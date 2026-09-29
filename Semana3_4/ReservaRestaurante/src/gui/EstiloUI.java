package gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EstiloUI {

   public static final Color FONDO       = new Color(217, 217, 217);
    public static final Color BARRA       = new Color(122, 122, 122);
    public static final Color GRIS_CAMPO  = new Color(147, 143, 141);
    public static final Color NEGRO       = Color.BLACK;
    public static final Color ROJO        = new Color(250, 65, 65);
    public static final Color ROJO_HOVER  = new Color(220, 45, 45);
    public static final Color LILA        = new Color(160, 170, 205);
    public static final Color LILA_HOVER  = new Color(140, 152, 195);

    public static JLabel crearBarra(String titulo, int ancho) {
        JLabel barra = new JLabel(titulo, SwingConstants.CENTER);
        barra.setOpaque(true);
        barra.setBackground(BARRA);
        barra.setForeground(Color.WHITE);
        barra.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        barra.setBounds(0, 0, ancho, 48);
        return barra;
    }

    public static JLabel etiqueta(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        l.setForeground(new Color(20, 20, 20));
        l.setBounds(x, y, 180, 30);
        return l;
    }

    /** Texto azul subrayado, con cursor de mano, para usar como link dentro de un JFrame/JDialog. */
    public static JLabel crearEnlace(String texto) {
        JLabel l = new JLabel("<html><u>" + texto + "</u></html>", SwingConstants.CENTER);
        l.setForeground(new Color(40, 70, 160));
        l.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return l;
    }

    public static class CampoGris extends JTextField {
        public CampoGris() {
            setOpaque(true);
            setBackground(GRIS_CAMPO);
            setForeground(Color.WHITE);
            setCaretColor(Color.WHITE);
            setHorizontalAlignment(CENTER);
            setFont(new Font("Segoe UI", Font.PLAIN, 18));
            setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        }

        public void bloquear(boolean bloqueado) {
            setEditable(!bloqueado);
            setFocusable(!bloqueado);
            setBackground(bloqueado ? NEGRO : GRIS_CAMPO);
        }
    }

    public static class ComboGris extends JComboBox<String> {
        public ComboGris(String... items) {
            super(items);
            setOpaque(true);
            setBackground(GRIS_CAMPO);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 18));
            setBorder(BorderFactory.createEmptyBorder());

            setUI(new BasicComboBoxUI() {
                @Override
                protected JButton createArrowButton() {
                    BasicArrowButton b = new BasicArrowButton(
                            BasicArrowButton.SOUTH, ComboGris.this.getBackground(),
                            ComboGris.this.getBackground(), new Color(230, 230, 230),
                            ComboGris.this.getBackground());
                    b.setBorder(BorderFactory.createEmptyBorder());
                    return b;
                }
            });

            setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    setHorizontalAlignment(CENTER);
                    setForeground(Color.WHITE);
                    setBackground(isSelected && index >= 0 ? BARRA : ComboGris.this.getBackground());
                    return this;
                }
            });
        }

        public void bloquear(boolean bloqueado) {
            setEnabled(!bloqueado);
            setBackground(bloqueado ? NEGRO : GRIS_CAMPO);
            setUI(getUI());
            repaint();
        }
    }

    public static class BotonRedondo extends JButton {
        private final Color normal, hover;
        private boolean sobre = false;

        public BotonRedondo(String texto, Color normal, Color hover) {
            super(texto);
            this.normal = normal;
            this.hover = hover;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 16));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { sobre = true; repaint(); }
                public void mouseExited(MouseEvent e)  { sobre = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(sobre ? hover : normal);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
            g2.setColor(new Color(30, 30, 30));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }
}