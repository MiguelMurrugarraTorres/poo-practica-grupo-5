/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import excepciones.DatosInvalidosException;
import excepciones.MesaYaExistenteException;
import modelo.Ubicacion;
import servicio.MesaService;
/**
 *
 * @author Diego
 */
public class MenuRegistrarMesa extends JFrame{
    
private final MesaService mesaService = new MesaService();

    private EstiloUI.CampoGris txtNumero, txtCapacidad;
    private EstiloUI.ComboGris cboUbicacion, cboEstado;

    public MenuRegistrarMesa() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Mesa");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { volverAlMenu(); }
        });
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(440, 380));

        add(EstiloUI.crearBarra("Registrar Mesa", 440));

        EstiloUI.BotonRedondo btnVerMesas =
                new EstiloUI.BotonRedondo("Ver mesas", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnVerMesas.setBounds(270, 54, 145, 24);
        btnVerMesas.addActionListener(e -> MenuListaMesas.mostrar(this));
        add(btnVerMesas);

        add(EstiloUI.etiqueta("Numero de mesa :", 25, 90));
        txtNumero = new EstiloUI.CampoGris();
        txtNumero.setBounds(215, 90, 210, 30);
        add(txtNumero);

        add(EstiloUI.etiqueta("Capacidad:", 25, 135));
        txtCapacidad = new EstiloUI.CampoGris();
        txtCapacidad.setBounds(215, 135, 210, 30);
        add(txtCapacidad);

        add(EstiloUI.etiqueta("Ubicación:", 25, 180));
        List<Ubicacion> ubicaciones = mesaService.listarUbicaciones();
        String[] nombres = new String[ubicaciones.size()];
        for (int i = 0; i < ubicaciones.size(); i++) nombres[i] = ubicaciones.get(i).getNombreUbicacion();
        cboUbicacion = new EstiloUI.ComboGris(nombres);
        cboUbicacion.setBounds(215, 180, 210, 30);
        add(cboUbicacion);

        add(EstiloUI.etiqueta("Estado:", 25, 225));
        cboEstado = new EstiloUI.ComboGris("Activo", "Inactivo");
        cboEstado.setBounds(215, 225, 210, 30);
        add(cboEstado);

        EstiloUI.BotonRedondo btnCancelar =
                new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCancelar.setBounds(50, 310, 150, 30);
        btnCancelar.addActionListener(e -> volverAlMenu());
        add(btnCancelar);

        EstiloUI.BotonRedondo btnRegistrar =
                new EstiloUI.BotonRedondo("Registrar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnRegistrar.setBounds(235, 310, 150, 30);
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar);

        getRootPane().setDefaultButton(btnRegistrar);

        pack();
        setLocationRelativeTo(null);
    }

    private void registrar() {
        int numero, capacidad;
        try {
            numero = Integer.parseInt(txtNumero.getText().trim());
            capacidad = Integer.parseInt(txtCapacidad.getText().trim());
        } catch (NumberFormatException ex) {
            aviso("Número de mesa y capacidad deben ser números.");
            return;
        }

        String ubicacion = (String) cboUbicacion.getSelectedItem();
        boolean activo = "Activo".equals(cboEstado.getSelectedItem());

        try {
            mesaService.registrarMesa(numero, capacidad, ubicacion, activo);
        } catch (MesaYaExistenteException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage() + "\nIngresa otro número de mesa e inténtalo de nuevo.",
                    "Mesa ya registrada", JOptionPane.WARNING_MESSAGE);
            txtNumero.requestFocus();
            txtNumero.selectAll();
            return;
        } catch (DatosInvalidosException ex) {
            aviso(ex.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Mesa " + numero + " registrada correctamente.");
        volverAlMenu();
    }

    private void aviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
    }

    private void volverAlMenu() {
        new MenuEmpleado().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuRegistrarMesa().setVisible(true));
    }
}