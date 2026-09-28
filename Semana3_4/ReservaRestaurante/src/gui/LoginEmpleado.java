/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginEmpleado extends JFrame  {
 private static final Color FONDO       = new Color(217, 217, 217);
    private static final Color BARRA       = new Color(122, 122, 122);
    private static final Color CAMPO       = new Color(117, 80, 80);
    private static final Color CAMPO_FOCO  = new Color(140, 96, 96);
    private static final Color BOTON       = new Color(160, 170, 205);
    private static final Color BOTON_HOVER = new Color(140, 152, 195);

    private CampoTexto txtCorreo;
    private CampoClave txtClave;
    private JLabel lblError;

    public LoginEmpleado() {
        initComponents();
        
    }

    private void initComponents() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(460, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(FONDO);

        // ---------- Barra gris superior ----------
        JPanel barra = new JPanel();
        barra.setBackground(BARRA);
        barra.setBounds(0, 0, 460, 28);
        add(barra);

        // ---------- Título ----------
        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 34));
        lblTitulo.setForeground(new Color(30, 30, 30));
        lblTitulo.setBounds(0, 45, 460, 50);
        add(lblTitulo);

        // ---------- Correo ----------
        JLabel lblCorreo = new JLabel("Correo :");
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCorreo.setBounds(45, 120, 200, 24);
        add(lblCorreo);

        txtCorreo = new CampoTexto();
        txtCorreo.setBounds(45, 148, 370, 38);
        add(txtCorreo);

        // ---------- Contraseña ----------
        JLabel lblClave = new JLabel("Contraseña :");
        lblClave.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblClave.setBounds(45, 210, 200, 24);
        add(lblClave);

        txtClave = new CampoClave();
        txtClave.setBounds(45, 238, 370, 38);
        add(txtClave);

        // ---------- Mensaje de error (oculto hasta que haga falta) ----------
        lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(new Color(190, 40, 40));
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblError.setBounds(45, 292, 370, 20);
        add(lblError);

        // ---------- Botón Iniciar ----------
        BotonRedondo btnIniciar = new BotonRedondo("Iniciar");
        btnIniciar.setBounds(115, 350, 230, 42);
        btnIniciar.addActionListener(e -> iniciarSesion());
        add(btnIniciar);

        // Enter en cualquier campo también inicia sesión
        getRootPane().setDefaultButton(btnIniciar);
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String clave = new String(txtClave.getPassword());

        if (correo.isEmpty() || clave.isEmpty()) {
            lblError.setText("Completa el correo y la contraseña");
            return;
        }

        // TODO: aquí valida con tu base de datos / clase Trabajador.iniciarSesion()
        boolean valido = true;

        if (valido) {
            new MenuEmpleado().setVisible(true);
            dispose();
        } else {
            lblError.setText("Correo o contraseña incorrectos");
        }
    }

    private static void pintarPildora(Graphics g, JComponent c, Color color) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, c.getHeight(), c.getHeight());
        g2.setColor(new Color(30, 30, 30));
        g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, c.getHeight(), c.getHeight());
        g2.dispose();
    }

    private class CampoTexto extends JTextField {
        private boolean foco = false;

        CampoTexto() {
            setOpaque(false);
            setForeground(Color.WHITE);
            setCaretColor(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { foco = true; repaint(); }
                public void focusLost(FocusEvent e)   { foco = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            pintarPildora(g, this, foco ? CAMPO_FOCO : CAMPO);
            super.paintComponent(g);
        }
    }

    private class CampoClave extends JPasswordField {
        private boolean foco = false;

        CampoClave() {
            setOpaque(false);
            setForeground(Color.WHITE);
            setCaretColor(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
            addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) { foco = true; repaint(); }
                public void focusLost(FocusEvent e)   { foco = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            pintarPildora(g, this, foco ? CAMPO_FOCO : CAMPO);
            super.paintComponent(g);
        }
    }

    private class BotonRedondo extends JButton {
        private boolean sobre = false;

        BotonRedondo(String texto) {
            super(texto);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 18));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { sobre = true; repaint(); }
                public void mouseExited(MouseEvent e)  { sobre = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            pintarPildora(g, this, sobre ? BOTON_HOVER : BOTON);
            super.paintComponent(g); // dibuja el texto
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new LoginEmpleado().setVisible(true));
    }
}