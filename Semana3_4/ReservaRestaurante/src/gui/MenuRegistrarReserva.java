/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Mesa;
import modelo.Reserva;
import gui.Selectores;
import servicio.ReservaServicio;
import servicio.Sesion;
        
public class MenuRegistrarReserva extends JFrame {

private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_HORA  = DateTimeFormatter.ofPattern("HH:mm");

    private final ReservaServicio reservaServicio = new ReservaServicio();

    private EstiloUI.CampoGris txtDni, txtCantidad, txtMesa;
    private CampoSelector txtFecha, txtHoraInicio, txtHoraFin;
    private JTextArea txtObservacion;

    private LocalDate fecha = LocalDate.now();
    private LocalTime horaInicio = LocalTime.now().withSecond(0).withNano(0);
    private LocalTime horaFin = null;
    private Mesa mesa = null;

    public MenuRegistrarReserva() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Reserva");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(450, 490));

        add(EstiloUI.crearBarra("Registrar Reserva", 450));

        txtDni = new EstiloUI.CampoGris();
        soloDigitos(txtDni, 8);
        fila("Cliente (DNI):", txtDni, 70);

        txtFecha = new CampoSelector(this::elegirFecha);
        txtFecha.setText(fecha.format(FMT_FECHA));
        fila("Fecha:", txtFecha, 113);

        txtHoraInicio = new CampoSelector(this::elegirHoraInicio);
        txtHoraInicio.setText(horaInicio.format(FMT_HORA));
        fila("Hora Inicio:", txtHoraInicio, 156);

        txtHoraFin = new CampoSelector(this::elegirHoraFin);
        fila("Hora fin:", txtHoraFin, 199);

        txtCantidad = new EstiloUI.CampoGris();
        soloDigitos(txtCantidad, 3);
        fila("Cantidad de Personas:", txtCantidad, 242);

        add(etiqueta("Mesa:", 290));
        txtMesa = new EstiloUI.CampoGris();
        txtMesa.setEditable(false);
        txtMesa.setFocusable(false);
        txtMesa.setBounds(235, 290, 70, 30);
        add(txtMesa);

        // Si cambian las personas, la mesa elegida ya podría no servir
        txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { limpiarMesa(); }
            public void removeUpdate(DocumentEvent e)  { limpiarMesa(); }
            public void changedUpdate(DocumentEvent e) { limpiarMesa(); }
        });

        EstiloUI.BotonRedondo btnSeleccionar =
                new EstiloUI.BotonRedondo("Seleccionar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnSeleccionar.setBounds(320, 291, 105, 28);
        btnSeleccionar.addActionListener(e -> seleccionarMesa());
        add(btnSeleccionar);

        add(etiqueta("Observación:", 335));
        txtObservacion = new JTextArea();
        txtObservacion.setLineWrap(true);
        txtObservacion.setWrapStyleWord(true);
        txtObservacion.setBackground(EstiloUI.GRIS_CAMPO);
        txtObservacion.setForeground(Color.WHITE);
        txtObservacion.setCaretColor(Color.WHITE);
        txtObservacion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtObservacion.setMargin(new Insets(4, 6, 4, 6));
        JScrollPane scroll = new JScrollPane(txtObservacion);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(235, 335, 190, 75);
        add(scroll);

        EstiloUI.BotonRedondo btnCancelar =
                new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCancelar.setBounds(55, 435, 150, 30);
        btnCancelar.addActionListener(e -> volverAlMenu());
        add(btnCancelar);

        EstiloUI.BotonRedondo btnRegistrar =
                new EstiloUI.BotonRedondo("Registrar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnRegistrar.setBounds(245, 435, 150, 30);
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar);

        pack();
        setLocationRelativeTo(null);
    }

    // ---------- Helpers de diseño ----------
    private JLabel etiqueta(String texto, int y) {
        JLabel l = EstiloUI.etiqueta(texto, 25, y);
        l.setSize(210, 30);
        return l;
    }

    private void fila(String texto, JComponent campo, int y) {
        add(etiqueta(texto, y));
        campo.setBounds(235, y, 190, 30);
        add(campo);
    }

    private void soloDigitos(JTextField campo, int max) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) return;
                if (!Character.isDigit(c) || campo.getText().length() >= max) e.consume();
            }
        });
    }

    // ---------- Selectores de fecha y hora ----------
    private void elegirFecha() {
        LocalDate f = Selectores.elegirFecha(this, fecha);
        if (f != null) {
            fecha = f;
            txtFecha.setText(f.format(FMT_FECHA));
            limpiarMesa();
        }
    }

    private void elegirHoraInicio() {
        LocalTime h = Selectores.elegirHora(this, "Hora de inicio", horaInicio);
        if (h != null) {
            horaInicio = h;
            txtHoraInicio.setText(h.format(FMT_HORA));
            limpiarMesa();
        }
    }

    private void elegirHoraFin() {
        // Si aún no hay hora fin, el selector se abre sugiriendo 3 horas después del inicio
        LocalTime sugerida = (horaFin != null) ? horaFin : horaInicio.plusHours(3);
        LocalTime h = Selectores.elegirHora(this, "Hora de fin", sugerida);
        if (h != null) {
            horaFin = h;
            txtHoraFin.setText(h.format(FMT_HORA));
            limpiarMesa();
        }
    }

    private void limpiarMesa() {
        mesa = null;
        if (txtMesa != null) txtMesa.setText("");
    }

    private int leerPersonas() {
        try {
            return Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    // ---------- Seleccionar mesa (filtrada por cantidad de personas) ----------
    private void seleccionarMesa() {
        int personas = leerPersonas();
        if (personas <= 0) {
            aviso("Primero ingresa la cantidad de personas.");
            txtCantidad.requestFocus();
            return;
        }
        if (horaFin == null) {
            aviso("Primero selecciona la hora de fin.");
            return;
        }
        if (!horaFin.isAfter(horaInicio)) {
            aviso("La hora de fin debe ser posterior a la hora de inicio.");
            return;
        }

        Mesa m = MenuSeleccionarMesa.elegir(this, personas, fecha, horaInicio, horaFin);
        if (m != null) {
            mesa = m;
            txtMesa.setText(String.valueOf(m.getNumeroMesa()));
        }
    }

    
    private void registrar() {
        String dni = txtDni.getText().trim();
        if (!dni.matches("\\d{8}")) {
            aviso("El DNI debe tener 8 dígitos.");
            return;
        }
        if (horaFin == null) {
            aviso("Selecciona la hora de fin.");
            return;
        }
        int personas = leerPersonas();
        if (personas <= 0) {
            aviso("Ingresa la cantidad de personas.");
            return;
        }

        try {
            Reserva r = reservaServicio.registrar(dni, fecha, horaInicio, horaFin,
                    personas, txtObservacion.getText().trim(), mesa, Sesion.getIdTrabajador());
            JOptionPane.showMessageDialog(this, "Reserva N° " + r.getIdReserva() + " registrada correctamente.");
            volverAlMenu();
        } catch (IllegalArgumentException ex) {
            aviso(ex.getMessage());
        }
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }

    private void volverAlMenu() {
        new MenuEmpleado().setVisible(true);
        dispose();
    }

    // ---------- Campo gris que abre un selector al hacer clic ----------
    private static class CampoSelector extends EstiloUI.CampoGris {
        CampoSelector(Runnable alClick) {
            setEditable(false);
            setFocusable(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { alClick.run(); }
            });
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuRegistrarReserva().setVisible(true));
    }
}