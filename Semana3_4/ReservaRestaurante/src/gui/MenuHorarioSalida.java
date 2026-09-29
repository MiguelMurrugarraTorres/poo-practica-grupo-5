/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.*;
import modelo.Trabajador;
import servicio.Sesion;
import servicio.TrabajadorServicio;

/**
 *
 * @author Diego
 */
public class MenuHorarioSalida  extends JFrame{
private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("hh:mm:ss a");

    private final TrabajadorServicio trabajadorServicio = new TrabajadorServicio();
    private EstiloUI.BotonRedondo btnRegistrarSalida;
    private JLabel lblHorarioSalida;

    public MenuHorarioSalida() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Mi Asistencia");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { volverAlMenu(); }
        });
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(400, 470));

        add(EstiloUI.crearBarra("Mi Asistencia", 400));

        EstiloUI.BotonRedondo btnCerrar =
                new EstiloUI.BotonRedondo("X", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCerrar.setBounds(350, 10, 30, 28);
        btnCerrar.addActionListener(e -> volverAlMenu());
        add(btnCerrar);

        Trabajador t = Sesion.getTrabajador();
        String nombre = (t != null) ? t.getNombres() + " " + t.getApellidos() : "—";
        String cargo = (t != null) ? t.getCargo() : "—";

        JLabel lblHola = new JLabel("Hola: " + nombre);
        lblHola.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblHola.setBounds(25, 65, 350, 26);
        add(lblHola);

        JLabel lblCargo = new JLabel("Cargo : " + cargo);
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblCargo.setBounds(25, 93, 350, 26);
        add(lblCargo);

        JLabel lblAsistencia = new JLabel("MI ASISTENCIA", SwingConstants.CENTER);
        lblAsistencia.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAsistencia.setBounds(0, 150, 400, 26);
        add(lblAsistencia);

        JLabel lblFecha = new JLabel(formatearFecha(LocalDate.now()), SwingConstants.CENTER);
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblFecha.setBounds(0, 185, 400, 26);
        add(lblFecha);

        JPanel caja = new JPanel(new GridLayout(2, 1, 0, 6));
        caja.setBackground(EstiloUI.FONDO);
        caja.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        caja.setBounds(35, 240, 330, 70);

        String horaEntrada = (t != null && t.getHoraEntrada() != null)
                ? t.getHoraEntrada().toLocalTime().format(FMT_HORA) : "--";
        JLabel lblEntrada = new JLabel("  Horario de entrada :  " + horaEntrada);
        lblEntrada.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel lblTurno = new JLabel("  turno : " + ((t != null) ? t.getTurno() : "--"));
        lblTurno.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        caja.add(lblEntrada);
        caja.add(lblTurno);
        add(caja);

        lblHorarioSalida = new JLabel("", SwingConstants.CENTER);
        lblHorarioSalida.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblHorarioSalida.setForeground(new Color(30, 110, 60));
        lblHorarioSalida.setBounds(0, 320, 400, 24);
        add(lblHorarioSalida);
        if (t != null && t.tieneSalidaRegistrada()) {
            lblHorarioSalida.setText("Salida registrada a las " + t.getHoraSalida().toLocalTime().format(FMT_HORA));
        }

        // Botón Volver, por si el empleado entró aquí por error
        EstiloUI.BotonRedondo btnVolver =
                new EstiloUI.BotonRedondo("Volver", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnVolver.setBounds(50, 375, 140, 36);
        btnVolver.addActionListener(e -> volverAlMenu());
        add(btnVolver);

        btnRegistrarSalida = new EstiloUI.BotonRedondo("Registrar Salida", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnRegistrarSalida.setBounds(210, 375, 150, 36);
        btnRegistrarSalida.addActionListener(e -> registrarSalida());
        btnRegistrarSalida.setEnabled(t != null && !t.tieneSalidaRegistrada());
        add(btnRegistrarSalida);

        pack();
        setLocationRelativeTo(null);
    }

    private String formatearFecha(LocalDate f) {
        String dia = f.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-PE"));
        dia = Character.toUpperCase(dia.charAt(0)) + dia.substring(1);
        return dia + " " + f.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private void registrarSalida() {
        Trabajador t = Sesion.getTrabajador();
        if (t == null) return;

        int r = JOptionPane.showConfirmDialog(this,
                "¿Registrar tu salida y cerrar sesión?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (r != JOptionPane.YES_OPTION) return;

        trabajadorServicio.registrarSalida(t);
        lblHorarioSalida.setText("Salida registrada a las " + t.getHoraSalida().toLocalTime().format(FMT_HORA));
        btnRegistrarSalida.setEnabled(false);

        JOptionPane.showMessageDialog(this, "Salida registrada correctamente. Sesión cerrada.");
        Sesion.cerrar();
        new LoginEmpleado().setVisible(true);
        dispose();
    }

    private void volverAlMenu() {
        new MenuEmpleado().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuHorarioSalida().setVisible(true));
    }
}