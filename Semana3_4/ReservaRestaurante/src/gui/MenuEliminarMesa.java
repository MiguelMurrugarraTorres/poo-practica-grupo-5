/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import excepciones.MesaConReservaException;
import excepciones.MesaNoEncontradaException;
import modelo.Mesa;
import modelo.Ubicacion;
import servicio.MesaService;

public class MenuEliminarMesa extends JFrame {

    private final MesaService mesaService = new MesaService();

    private EstiloUI.ComboGris cboNumero;
    private EstiloUI.CampoGris txtCapacidad;
    private EstiloUI.ComboGris cboUbicacion, cboEstado;
    private Mesa mesaEncontrada = null;

    public MenuEliminarMesa() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Eliminar Mesa");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { volverAlMenu(); }
        });
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(440, 380));

        add(EstiloUI.crearBarra("Eliminar Mesa", 440));

        add(EstiloUI.etiqueta("Numero de mesa :", 25, 80));

        List<Mesa> mesas = mesaService.listarMesas();
        mesas.sort((a, b) -> Integer.compare(a.getNumeroMesa(), b.getNumeroMesa()));
        String[] numeros = new String[mesas.size()];
        for (int i = 0; i < mesas.size(); i++) numeros[i] = String.valueOf(mesas.get(i).getNumeroMesa());

        cboNumero = new EstiloUI.ComboGris(numeros);
        cboNumero.setBounds(215, 80, 210, 30);
        cboNumero.setSelectedItem(null);   // que arranque sin ninguna mesa elegida
        cboNumero.addActionListener(e -> buscarMesa());
        add(cboNumero);

        add(EstiloUI.etiqueta("Capacidad:", 25, 125));
        txtCapacidad = new EstiloUI.CampoGris();
        txtCapacidad.setBounds(215, 125, 210, 30);
        txtCapacidad.bloquear(true);
        add(txtCapacidad);

        add(EstiloUI.etiqueta("Ubicación:", 25, 170));
        List<Ubicacion> ubicaciones = mesaService.listarUbicaciones();
        String[] nombresUbicacion = new String[ubicaciones.size()];
        for (int i = 0; i < ubicaciones.size(); i++) nombresUbicacion[i] = ubicaciones.get(i).getNombreUbicacion();
        cboUbicacion = new EstiloUI.ComboGris(nombresUbicacion);
        cboUbicacion.setBounds(215, 170, 210, 30);
        cboUbicacion.bloquear(true);
        add(cboUbicacion);

        add(EstiloUI.etiqueta("Estado:", 25, 215));
        cboEstado = new EstiloUI.ComboGris("Activo", "Inactivo");
        cboEstado.setBounds(215, 215, 210, 30);
        cboEstado.bloquear(true);
        add(cboEstado);

        EstiloUI.BotonRedondo btnCancelar =
                new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCancelar.setBounds(50, 300, 150, 30);
        btnCancelar.addActionListener(e -> volverAlMenu());
        add(btnCancelar);

        EstiloUI.BotonRedondo btnEliminar =
                new EstiloUI.BotonRedondo("Eliminar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnEliminar.setBounds(235, 300, 150, 30);
        btnEliminar.addActionListener(e -> eliminar());
        add(btnEliminar);

        pack();
        setLocationRelativeTo(null);
    }

    /** Busca la mesa según el número elegido en la lista y llena los campos bloqueados. */
    private void buscarMesa() {
        mesaEncontrada = null;
        txtCapacidad.setText("");

        Object seleccion = cboNumero.getSelectedItem();
        if (seleccion == null) return;

        mesaEncontrada = mesaService.buscarMesa(Integer.parseInt((String) seleccion));
        if (mesaEncontrada != null) {
            txtCapacidad.setText(String.valueOf(mesaEncontrada.getCapacidad()));
            cboUbicacion.setSelectedItem(mesaEncontrada.getUbicacion().getNombreUbicacion());
            cboEstado.setSelectedItem(mesaEncontrada.isEstado() ? "Activo" : "Inactivo");
        }
    }

    private void eliminar() {
        if (mesaEncontrada == null) {
            JOptionPane.showMessageDialog(this,
                    "Elige una mesa de la lista.",
                    "Ninguna mesa seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int r = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar la mesa " + mesaEncontrada.getNumeroMesa() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r != JOptionPane.YES_OPTION) return;

        try {
            mesaService.eliminarMesa(mesaEncontrada.getNumeroMesa());
        } catch (MesaNoEncontradaException | MesaConReservaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se puede eliminar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Mesa eliminada correctamente.");
        volverAlMenu();
    }

    private void volverAlMenu() {
        new MenuEmpleado().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuEliminarMesa().setVisible(true));
    }
}