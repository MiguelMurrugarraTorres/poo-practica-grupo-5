/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import excepciones.ClienteYaExistenteException;
import excepciones.DatosInvalidosException;
import servicio.ClienteServicio;
/**
 *
 * @author Diego
 */
public class MenuRegistrarCliente extends JFrame   {
 private final ClienteServicio clienteServicio = new ClienteServicio();

    private EstiloUI.CampoGris txtDni, txtNombres, txtApellidos, txtTelefono, txtCorreo;

    public MenuRegistrarCliente() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Registrar Cliente");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(440, 420));

        add(EstiloUI.crearBarra("Registrar Cliente", 440));

        add(EstiloUI.etiqueta("DNI:", 25, 80));
        txtDni = new EstiloUI.CampoGris();
        soloDigitos(txtDni, 8);
        txtDni.setBounds(215, 80, 200, 30);
        add(txtDni);

        add(EstiloUI.etiqueta("Nombres:", 25, 125));
        txtNombres = new EstiloUI.CampoGris();
        txtNombres.setBounds(215, 125, 200, 30);
        add(txtNombres);

        add(EstiloUI.etiqueta("Apellidos:", 25, 170));
        txtApellidos = new EstiloUI.CampoGris();
        txtApellidos.setBounds(215, 170, 200, 30);
        add(txtApellidos);

        add(EstiloUI.etiqueta("Teléfono:", 25, 215));
        txtTelefono = new EstiloUI.CampoGris();
        soloDigitos(txtTelefono, 9);
        txtTelefono.setBounds(215, 215, 200, 30);
        add(txtTelefono);

        add(EstiloUI.etiqueta("Correo:", 25, 260));
        txtCorreo = new EstiloUI.CampoGris();
        txtCorreo.setBounds(215, 260, 200, 30);
        add(txtCorreo);

        EstiloUI.BotonRedondo btnCancelar =
                new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCancelar.setBounds(50, 340, 150, 30);
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        EstiloUI.BotonRedondo btnRegistrar =
                new EstiloUI.BotonRedondo("Registrar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnRegistrar.setBounds(235, 340, 150, 30);
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar);

        getRootPane().setDefaultButton(btnRegistrar);

        pack();
        setLocationRelativeTo(null);
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

    private void registrar() {
        try {
            clienteServicio.registrarCliente(
                    txtDni.getText().trim(),
                    txtNombres.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim());
        } catch (DatosInvalidosException | ClienteYaExistenteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.");
        dispose();   // vuelve a la ventana que lo abrió (Registrar Reserva)
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MenuRegistrarCliente().setVisible(true));
    }
}
