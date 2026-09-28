/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.JFrame;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
/**
 *
 * @author Diego
 */
public class MenuRegistrarMesa extends JFrame{
    
private EstiloUI.CampoGris txtNumero, txtCapacidad;
    private EstiloUI.ComboGris cboUbicacion, cboEstado;

    public MenuRegistrarMesa() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Mesa");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(440, 380));

        add(EstiloUI.crearBarra("Registrar Mesa", 440));

        add(EstiloUI.etiqueta("Numero de mesa :", 25, 80));
        txtNumero = new EstiloUI.CampoGris();
        txtNumero.setBounds(215, 80, 210, 30);
        add(txtNumero);

        add(EstiloUI.etiqueta("Capacidad:", 25, 125));
        txtCapacidad = new EstiloUI.CampoGris();
        txtCapacidad.setBounds(215, 125, 210, 30);
        add(txtCapacidad);

        add(EstiloUI.etiqueta("Ubicación:", 25, 170));
        cboUbicacion = new EstiloUI.ComboGris("Sala", "Terraza", "Campo", "Afuera");
        cboUbicacion.setBounds(215, 170, 210, 30);
        add(cboUbicacion);

        add(EstiloUI.etiqueta("Estado:", 25, 215));
        cboEstado = new EstiloUI.ComboGris("Activo", "Inactivo");
        cboEstado.setBounds(215, 215, 210, 30);
        add(cboEstado);

        EstiloUI.BotonRedondo btnCancelar =
                new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCancelar.setBounds(50, 300, 150, 30);
        btnCancelar.addActionListener(e -> volverAlMenu());
        add(btnCancelar);

        EstiloUI.BotonRedondo btnRegistrar =
                new EstiloUI.BotonRedondo("Registrar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnRegistrar.setBounds(235, 300, 150, 30);
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
            JOptionPane.showMessageDialog(this, "Número de mesa y capacidad deben ser números.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (numero <= 0 || capacidad <= 0) {
            JOptionPane.showMessageDialog(this, "Los valores deben ser mayores a 0.",
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ubicacion = (String) cboUbicacion.getSelectedItem();
        boolean activo = "Activo".equals(cboEstado.getSelectedItem());

        // TODO: aquí guardas en tu base de datos (Mesa: numero, capacidad, ubicacion, activo)

        JOptionPane.showMessageDialog(this, "Mesa " + numero + " registrada correctamente.");
        volverAlMenu();
    }

    private void volverAlMenu() {
        new MenuEmpleado().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuRegistrarMesa().setVisible(true));
    }
}