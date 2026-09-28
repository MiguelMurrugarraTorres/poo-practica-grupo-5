/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
/**
 *
 * @author Diego
 */
public class MenuEliminarMesa extends JFrame  {
 
    private EstiloUI.CampoGris txtNumero, txtCapacidad;
    private EstiloUI.ComboGris cboUbicacion, cboEstado;
    private boolean mesaEncontrada = false;

    public MenuEliminarMesa() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Eliminar Mesa");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(440, 380));

        add(EstiloUI.crearBarra("Eliminar Mesa", 440));

        add(EstiloUI.etiqueta("Numero de mesa :", 25, 80));
        txtNumero = new EstiloUI.CampoGris();
        txtNumero.setBounds(215, 80, 210, 30);
        txtNumero.addActionListener(e -> buscarMesa());          // Enter
        txtNumero.addFocusListener(new FocusAdapter() {          // al salir del campo
            public void focusLost(FocusEvent e) { buscarMesa(); }
        });
        add(txtNumero);

        add(EstiloUI.etiqueta("Capacidad:", 25, 125));
        txtCapacidad = new EstiloUI.CampoGris();
        txtCapacidad.setBounds(215, 125, 210, 30);
        txtCapacidad.bloquear(true);
        add(txtCapacidad);

        add(EstiloUI.etiqueta("Ubicación:", 25, 170));
        cboUbicacion = new EstiloUI.ComboGris("Sala", "Terraza", "Campo", "Afuera");
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

    /** Busca la mesa por número y muestra sus datos en los campos bloqueados. */
    private void buscarMesa() {
        String texto = txtNumero.getText().trim();
        if (texto.isEmpty()) return;

        int numero;
        try {
            numero = Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            limpiarDatos();
            return;
        }

        // TODO: consulta tu base de datos con "numero".
        // Si existe → llena los campos con sus datos. Si no → mesaEncontrada = false.
        // (Datos de ejemplo para probar la pantalla:)
        mesaEncontrada = true;
        txtCapacidad.setText("4");
        cboUbicacion.setSelectedItem("Sala");
        cboEstado.setSelectedItem("Activo");
    }

    private void limpiarDatos() {
        mesaEncontrada = false;
        txtCapacidad.setText("");
    }

    private void eliminar() {
        buscarMesa();
        if (!mesaEncontrada) {
            JOptionPane.showMessageDialog(this, "Ingresa el número de una mesa existente.",
                    "Mesa no encontrada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int r = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar la mesa " + txtNumero.getText().trim() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (r != JOptionPane.YES_OPTION) return;

        // TODO: aquí eliminas la mesa en tu base de datos

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