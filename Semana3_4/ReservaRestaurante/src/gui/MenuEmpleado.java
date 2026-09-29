/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import modelo.Trabajador;
import servicio.Sesion;
/**
 *
 * @author Diego
 */
public class MenuEmpleado extends JFrame{
 private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(MenuEmpleado.class.getName());

    public MenuEmpleado() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Seleccionar Opción");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { intentarCerrarPrograma(); }
        });
        setSize(650, 650);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(45, 47, 51));

        JLabel lblTitulo = new JLabel("Seleccionar Opción", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(220, 220, 220));
        lblTitulo.setBounds(0, 20, 650, 45);
        add(lblTitulo);

        add(crearTarjeta(60, 100, 240, 220, "Registrar Horario de Salida", "/img/cerrar-sesion.png", () -> {
            new MenuHorarioSalida().setVisible(true);
            dispose();
        }));

        add(crearTarjeta(340, 100, 240, 220, "Registrar Reserva", "/img/viajar.png", () -> {
            new MenuRegistrarReserva().setVisible(true);
            dispose();
        }));

        add(crearTarjeta(60, 350, 240, 220, "Registrar Mesa", "/img/mesa.png", () -> {
            new MenuRegistrarMesa().setVisible(true);
            dispose();
        }));

        add(crearTarjeta(340, 350, 240, 220, "Eliminar Mesa", "/img/claro.png", () -> {
            new MenuEliminarMesa().setVisible(true);
            dispose();
        }));
    }

    /** Al cerrar el programa desde el menú principal, avisa si falta registrar la salida. */
    private void intentarCerrarPrograma() {
        Trabajador t = Sesion.getTrabajador();
        if (t != null && !t.tieneSalidaRegistrada()) {
            int r = JOptionPane.showConfirmDialog(this,
                    "Todavía no has registrado tu horario de salida.\n¿Deseas cerrar el programa de todas formas?",
                    "Salida no registrada", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (r != JOptionPane.YES_OPTION) return;
        }
        System.exit(0);
    }

    private JLayeredPane crearTarjeta(int x, int y, int ancho, int alto,
                                       String titulo, String rutaImagen, Runnable accion) {

        JLayeredPane capa = new JLayeredPane();
        capa.setBounds(x, y, ancho, alto);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0, ancho, alto);
        panel.setBackground(new Color(120, 80, 80));
        panel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30)));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(180, 180, 220));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 6, 8, 6));

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);

        ImageIcon icono = cargarIconoEscalado(rutaImagen, 90, 90);
        if (icono != null) {
            lblImagen.setIcon(icono);
        } else {
            logger.warning("No se encontró la imagen: " + rutaImagen);
        }

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblImagen, BorderLayout.CENTER);

        JButton boton = new JButton();
        boton.setBounds(0, 0, ancho, alto);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.addActionListener(e -> accion.run());

        capa.add(panel, JLayeredPane.DEFAULT_LAYER);
        capa.add(boton, JLayeredPane.PALETTE_LAYER);

        return capa;
    }

    private ImageIcon cargarIconoEscalado(String ruta, int ancho, int alto) {
        java.net.URL url = getClass().getResource(ruta);
        if (url == null) return null;
        ImageIcon original = new ImageIcon(url);
        Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new MenuEmpleado().setVisible(true));
    }
}