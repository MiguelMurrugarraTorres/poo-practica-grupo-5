/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.*;
import javax.swing.*;
import modelo.EstadoMesa;
import modelo.Mesa;
import servicio.MesaService.MesaDisponibilidad;
public class MenuDatosMesa extends JDialog{
 private boolean seleccionada = false;

    public static boolean mostrar(Window padre, MesaDisponibilidad datos) {
        MenuDatosMesa d = new MenuDatosMesa(padre, datos);
        d.setVisible(true);
        return d.seleccionada;
    }

    private MenuDatosMesa(Window padre, MesaDisponibilidad datos) {
        super(padre, "Datos Mesa", ModalityType.APPLICATION_MODAL);
        Mesa mesa = datos.getMesa();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(EstiloUI.FONDO);
        getContentPane().setPreferredSize(new Dimension(340, 400));

        add(EstiloUI.crearBarra("Datos Mesa", 340));

        Dibujo dibujo = new Dibujo(datos);
        dibujo.setBounds(0, 55, 340, 230);
        add(dibujo);

        JLabel lblCap = new JLabel("Mesa: " + mesa.getCapacidad() + " personas", SwingConstants.CENTER);
        lblCap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblCap.setBounds(0, 292, 340, 26);
        add(lblCap);

        JLabel lblEstado = new JLabel("Estado: " + datos.getEstado().getTexto()
                + "  ·  " + mesa.getUbicacion().getNombreUbicacion(), SwingConstants.CENTER);
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEstado.setForeground(new Color(80, 80, 80));
        lblEstado.setBounds(0, 318, 340, 22);
        add(lblEstado);

        EstiloUI.BotonRedondo btnRetro = new EstiloUI.BotonRedondo("Retroceder", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnRetro.setBounds(45, 355, 118, 28);
        btnRetro.addActionListener(e -> dispose());
        add(btnRetro);

        EstiloUI.BotonRedondo btnSel = new EstiloUI.BotonRedondo("Seleccionar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        btnSel.setBounds(180, 355, 118, 28);
        btnSel.setEnabled(datos.getEstado() == EstadoMesa.DISPONIBLE);
        btnSel.addActionListener(e -> { seleccionada = true; dispose(); });
        add(btnSel);

        pack();
        setLocationRelativeTo(padre);
    }

    private static class Dibujo extends JPanel {
        private final MesaDisponibilidad datos;

        Dibujo(MesaDisponibilidad datos) {
            this.datos = datos;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Mesa mesa = datos.getMesa();
            int cx = getWidth() / 2, cy = getHeight() / 2;
            int n = mesa.getCapacidad();
            int radio = Math.min(88, Math.max(58, 15 + n * 7));
            int w = 34, h = 30;

            for (int i = 0; i < n; i++) {
                double ang = 2 * Math.PI * i / n - Math.PI / 2;
                int sx = (int) (cx + radio * Math.cos(ang)) - w / 2;
                int sy = (int) (cy + radio * Math.sin(ang)) - h / 2;
                g2.setColor(new Color(176, 170, 170));
                g2.fillRect(sx, sy, w, h);
                g2.setColor(new Color(90, 90, 90));
                g2.drawRect(sx, sy, w, h);
            }

            int d = MenuSeleccionarMesa.DIAMETRO;
            g2.setColor(MenuSeleccionarMesa.colorDe(datos.getEstado()));
            g2.fillOval(cx - d / 2, cy - d / 2, d, d);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 22));
            String t = String.valueOf(mesa.getNumeroMesa());
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(t, cx - fm.stringWidth(t) / 2, cy + fm.getAscent() / 2 - 2);
        }
    }
}