/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import excepciones.MesaConReservaException;
import excepciones.MesaNoEncontradaException;
import modelo.Mesa;
import modelo.Ubicacion;
import servicio.MesaService;
/**
 *
 * @author Diego
 */
public class MenuEliminarMesa extends JFrame  {
 
    private final MesaService mesaService = new MesaService();

    private EstiloUI.CampoGris txtNumero, txtCapacidad;
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

        EstiloUI.BotonRedondo btnVerMesas =
                new EstiloUI.BotonRedondo("Ver mesas", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnVerMesas.setBounds(270, 54, 145, 24);
        btnVerMesas.addActionListener(e -> MenuListaMesas.mostrar(this));
        add(btnVerMesas);

        add(EstiloUI.etiqueta("Numero de mesa :", 25, 90));
        txtNumero = new EstiloUI.CampoGris();
        txtNumero.setBounds(215, 90, 210, 30);
        txtNumero.addActionListener(e -> buscarMesa());
        txtNumero.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) { buscarMesa(); }
        });
        add(txtNumero);

        add(EstiloUI.etiqueta("Capacidad:", 25, 135));
        txtCapacidad = new EstiloUI.CampoGris();
        txtCapacidad.setBounds(215, 135, 210, 30);
        txtCapacidad.bloquear(true);
        add(txtCapacidad);

        add(EstiloUI.etiqueta("Ubicación:", 25, 180));
        List<Ubicacion> ubicaciones = mesaService.listarUbicaciones();
        String[] nombres = new String[ubicaciones.size()];
        for (int i = 0; i < ubicaciones.size(); i++) nombres[i] = ubicaciones.get(i).getNombreUbicacion();
        cboUbicacion = new EstiloUI.ComboGris(nombres);
        cboUbicacion.setBounds(215, 180, 210, 30);
        cboUbicacion.bloquear(true);
        add(cboUbicacion);

        add(EstiloUI.etiqueta("Estado:", 25, 225));
        cboEstado = new EstiloUI.ComboGris("Activo", "Inactivo");
        cboEstado.setBounds(215, 225, 210, 30);
        cboEstado.bloquear(true);
        add(cboEstado);

        EstiloUI.BotonRedondo btnCancelar =
                new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCancelar.setBounds(50, 310, 150, 30);
        btnCancelar.addActionListener(e -> volverAlMenu());
        add(btnCancelar);

        EstiloUI.BotonRedondo btnEliminar =
                new EstiloUI.BotonRedondo("Eliminar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnEliminar.setBounds(235, 310, 150, 30);
        btnEliminar.addActionListener(e -> eliminar());
        add(btnEliminar);

        pack();
        setLocationRelativeTo(null);
    }

    private void buscarMesa() {
        mesaEncontrada = null;
        txtCapacidad.setText("");

        String texto = txtNumero.getText().trim();
        if (texto.isEmpty()) return;

        try {
            mesaEncontrada = mesaService.buscarMesa(Integer.parseInt(texto));
        } catch (NumberFormatException ex) {
            return;
        }

        if (mesaEncontrada != null) {
            txtCapacidad.setText(String.valueOf(mesaEncontrada.getCapacidad()));
            cboUbicacion.setSelectedItem(mesaEncontrada.getUbicacion().getNombreUbicacion());
            cboEstado.setSelectedItem(mesaEncontrada.isEstado() ? "Activo" : "Inactivo");
        }
    }

    private void eliminar() {
        buscarMesa();
        if (mesaEncontrada == null) {
            JOptionPane.showMessageDialog(this,
                    "No existe una mesa con ese número. Revisa la lista de mesas e inténtalo de nuevo.",
                    "Mesa no encontrada", JOptionPane.WARNING_MESSAGE);
            txtNumero.requestFocus();
            txtNumero.selectAll();
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