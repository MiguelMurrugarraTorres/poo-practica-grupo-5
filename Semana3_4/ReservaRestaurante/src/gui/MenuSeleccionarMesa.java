/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import modelo.EstadoMesa;
import modelo.Mesa;
import modelo.Ubicacion;
import servicio.MesaService;
import servicio.MesaService.MesaDisponibilidad;

public class MenuSeleccionarMesa extends JDialog  {
    
public static final int DIAMETRO = 48;
    private static final int PW = 849, PH = 353;
    private static final MesaService servicio = new MesaService();

    private final int personas;
    private final LocalDate fecha;
    private final LocalTime inicio, fin;
    private final List<Ubicacion> ubicaciones;
    private String ubicacion = "";
    private Mesa elegida = null;
    private final Plano plano = new Plano();

    public static Color colorDe(EstadoMesa e) {
        switch (e) {
            case DISPONIBLE: return new Color(125, 196, 120);
            case RESERVADA:  return new Color(125, 100, 155);
            default:         return new Color(150, 75, 95);
        }
    }

    public static Mesa elegir(Window padre, int personas, LocalDate fecha,
                              LocalTime inicio, LocalTime fin) {
        MenuSeleccionarMesa d = new MenuSeleccionarMesa(padre, personas, fecha, inicio, fin);
        d.setVisible(true);
        return d.elegida;
    }

    private MenuSeleccionarMesa(Window padre, int personas, LocalDate fecha,
                            LocalTime inicio, LocalTime fin) {
        super(padre, "Seleccionar Mesa", ModalityType.APPLICATION_MODAL);
        this.personas = personas;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.ubicaciones = servicio.listarUbicaciones();
        if (!ubicaciones.isEmpty()) ubicacion = ubicaciones.get(0).getNombreUbicacion();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(894, 570));

        JLabel barra = EstiloUI.crearBarra("Seleccionar Mesa", 894);
        barra.setBounds(0, 0, 894, 60);
        add(barra);

        int n = Math.max(1, ubicaciones.size());
        int paso = Math.min(177, 830 / n);
        int ancho = Math.min(118, paso - 10);
        int x = 20;
        for (Ubicacion u : ubicaciones) {
            Pestana p = new Pestana(u.getNombreUbicacion());
            p.setBounds(x, 82, ancho, 26);
            add(p);
            x += paso;
        }

        plano.setBounds(20, 132, PW, PH);
        add(plano);
        plano.cargar();

        add(leyenda("Disponible", colorDe(EstadoMesa.DISPONIBLE), 70, 505));
        add(leyenda("Reservado", colorDe(EstadoMesa.RESERVADA), 215, 505));
        add(leyenda("Inactiva", colorDe(EstadoMesa.INACTIVA), 360, 505));

        JLabel info = new JLabel("Mesas para " + personas + " personas o más");
        info.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        info.setBounds(500, 505, 210, 26);
        add(info);
        
       

        EstiloUI.BotonRedondo btnRetro = new EstiloUI.BotonRedondo("Retroceder", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnRetro.setBounds(724, 505, 125, 28);
        btnRetro.addActionListener(e -> dispose());
        add(btnRetro);

        pack();
        setLocationRelativeTo(padre);
    }

    private void cambiarUbicacion(String nueva) {
        ubicacion = nueva;
        plano.cargar();
        getContentPane().repaint();
    }

    private void abrirDatos(MesaDisponibilidad d) {
        if (MenuDatosMesa.mostrar(this, d)) {
            elegida = d.getMesa();
            dispose();
        }
    }

    private JLabel leyenda(String texto, Color color, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        l.setIconTextGap(8);
        l.setIcon(new Icon() {
            public int getIconWidth() { return 18; }
            public int getIconHeight() { return 18; }
            public void paintIcon(Component c, Graphics g, int px, int py) {
                g.setColor(color);
                g.fillRect(px, py, 18, 18);
                g.setColor(Color.DARK_GRAY);
                g.drawRect(px, py, 18, 18);
            }
        });
        l.setBounds(x, y, 140, 26);
        return l;
    }

    private class Pestana extends JButton {
        private final String nombre;

        Pestana(String nombre) {
            super(nombre.toUpperCase());
            this.nombre = nombre;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.BLACK);
            setFont(new Font("Segoe UI", Font.PLAIN, 15));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addActionListener(e -> cambiarUbicacion(nombre));
        }

        @Override
        protected void paintComponent(Graphics g) {
            boolean activa = nombre.equals(ubicacion);
            g.setColor(activa ? new Color(196, 214, 130) : new Color(232, 240, 190));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(activa ? Color.BLACK : new Color(90, 90, 90));
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            super.paintComponent(g);
        }
    }

    private static class Item {
        final MesaDisponibilidad datos;
        final Ellipse2D.Double forma;
        Item(MesaDisponibilidad datos, Ellipse2D.Double forma) {
            this.datos = datos;
            this.forma = forma;
        }
    }

    private class Plano extends JPanel {
        private final List<Item> items = new ArrayList<>();

        Plano() {
            setBackground(new Color(170, 170, 170));
            setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Item it = itemEn(e.getPoint());
                    if (it != null) abrirDatos(it.datos);
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    setCursor(itemEn(e.getPoint()) != null
                            ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                            : Cursor.getDefaultCursor());
                }
            });
        }

        void cargar() {
            items.clear();
            List<MesaDisponibilidad> lista =
                    servicio.mesasParaReserva(ubicacion, personas, fecha, inicio, fin);

            int columnas = 7;
            int filas = Math.max(1, (int) Math.ceil(lista.size() / (double) columnas));
            double cw = PW / (double) columnas;
            double ch = PH / (double) filas;

            for (int i = 0; i < lista.size(); i++) {
                double cx = cw * (i % columnas + 0.5);
                double cy = ch * (i / columnas + 0.5);
                items.add(new Item(lista.get(i),
                        new Ellipse2D.Double(cx - DIAMETRO / 2.0, cy - DIAMETRO / 2.0, DIAMETRO, DIAMETRO)));
            }
            repaint();
        }

        private Item itemEn(Point p) {
            for (Item it : items) {
                if (it.forma.contains(p)) return it;
            }
            return null;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (items.isEmpty()) {
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                g2.setColor(new Color(50, 50, 50));
                String t = "No hay mesas para " + personas + " personas en esta ubicación";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(t, (getWidth() - fm.stringWidth(t)) / 2, getHeight() / 2);
                return;
            }

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
            FontMetrics fm = g2.getFontMetrics();
            for (Item it : items) {
                g2.setColor(colorDe(it.datos.getEstado()));
                g2.fill(it.forma);
                g2.setColor(Color.BLACK);
                String t = String.valueOf(it.datos.getMesa().getNumeroMesa());
                g2.drawString(t, (float) (it.forma.getCenterX() - fm.stringWidth(t) / 2.0),
                        (float) (it.forma.getCenterY() + fm.getAscent() / 2.0 - 2));
            }
        }
    }
}