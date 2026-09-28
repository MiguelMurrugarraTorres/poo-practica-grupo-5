/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.*;

public class Selectores {

    /** Muestra un calendario. Devuelve la fecha elegida o null si se cancela. */
    public static LocalDate elegirFecha(Window padre, LocalDate inicial) {
        final LocalDate[] resultado = {null};
        final YearMonth[] mes = {YearMonth.from(inicial)};

        JDialog d = new JDialog(padre, "Seleccionar fecha", Dialog.ModalityType.APPLICATION_MODAL);
        d.setLayout(new BorderLayout(6, 6));
        ((JComponent) d.getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        d.getContentPane().setBackground(EstiloUI.FONDO);

        JLabel lblMes = new JLabel("", SwingConstants.CENTER);
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton ant = new JButton("<");
        JButton sig = new JButton(">");
        JPanel cab = new JPanel(new BorderLayout());
        cab.setOpaque(false);
        cab.add(ant, BorderLayout.WEST);
        cab.add(lblMes, BorderLayout.CENTER);
        cab.add(sig, BorderLayout.EAST);

        JPanel grilla = new JPanel(new GridLayout(0, 7, 3, 3));
        grilla.setOpaque(false);
        grilla.setPreferredSize(new Dimension(300, 230));

        Runnable[] pintar = new Runnable[1];
        pintar[0] = () -> {
            grilla.removeAll();
            for (String s : new String[]{"L", "M", "M", "J", "V", "S", "D"}) {
                JLabel l = new JLabel(s, SwingConstants.CENTER);
                l.setFont(new Font("Segoe UI", Font.BOLD, 13));
                grilla.add(l);
            }
            YearMonth ym = mes[0];
            int vacios = ym.atDay(1).getDayOfWeek().getValue() - 1;
            for (int i = 0; i < vacios; i++) grilla.add(new JLabel());

            for (int dia = 1; dia <= ym.lengthOfMonth(); dia++) {
                LocalDate f = ym.atDay(dia);
                JButton b = new JButton(String.valueOf(dia));
                b.setMargin(new Insets(0, 0, 0, 0));
                b.setFocusPainted(false);
                b.setEnabled(!f.isBefore(LocalDate.now()));
                if (f.equals(inicial)) b.setBackground(EstiloUI.LILA);
                if (f.equals(LocalDate.now())) b.setFont(b.getFont().deriveFont(Font.BOLD));
                b.addActionListener(e -> { resultado[0] = f; d.dispose(); });
                grilla.add(b);
            }
            String nombre = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-PE"));
            lblMes.setText(Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1) + " " + ym.getYear());
            grilla.revalidate();
            grilla.repaint();
        };

        ant.addActionListener(e -> { mes[0] = mes[0].minusMonths(1); pintar[0].run(); });
        sig.addActionListener(e -> { mes[0] = mes[0].plusMonths(1); pintar[0].run(); });
        pintar[0].run();

        d.add(cab, BorderLayout.NORTH);
        d.add(grilla, BorderLayout.CENTER);
        d.pack();
        d.setResizable(false);
        d.setLocationRelativeTo(padre);
        d.setVisible(true);
        return resultado[0];
    }

    /** Muestra un selector de hora (HH:mm). Devuelve la hora o null si se cancela. */
    public static LocalTime elegirHora(Window padre, String titulo, LocalTime inicial) {
        final LocalTime[] resultado = {null};

        JDialog d = new JDialog(padre, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        d.setLayout(new BorderLayout(0, 12));
        ((JComponent) d.getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        d.getContentPane().setBackground(EstiloUI.FONDO);

        JSpinner sH = new JSpinner(new SpinnerNumberModel(inicial.getHour(), 0, 23, 1));
        JSpinner sM = new JSpinner(new SpinnerNumberModel(inicial.getMinute(), 0, 59, 1));
        estiloSpinner(sH);
        estiloSpinner(sM);

        JLabel dosPuntos = new JLabel(":");
        dosPuntos.setFont(new Font("Segoe UI", Font.BOLD, 34));

        JPanel reloj = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        reloj.setOpaque(false);
        reloj.add(sH);
        reloj.add(dosPuntos);
        reloj.add(sM);

        EstiloUI.BotonRedondo cancelar = new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        EstiloUI.BotonRedondo aceptar = new EstiloUI.BotonRedondo("Aceptar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        cancelar.setPreferredSize(new Dimension(110, 30));
        aceptar.setPreferredSize(new Dimension(110, 30));
        cancelar.addActionListener(e -> d.dispose());
        aceptar.addActionListener(e -> {
            resultado[0] = LocalTime.of((Integer) sH.getValue(), (Integer) sM.getValue());
            d.dispose();
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        botones.setOpaque(false);
        botones.add(cancelar);
        botones.add(aceptar);

        d.add(reloj, BorderLayout.CENTER);
        d.add(botones, BorderLayout.SOUTH);
        d.getRootPane().setDefaultButton(aceptar);
        d.pack();
        d.setResizable(false);
        d.setLocationRelativeTo(padre);
        d.setVisible(true);
        return resultado[0];
    }

    private static void estiloSpinner(JSpinner s) {
        s.setEditor(new JSpinner.NumberEditor(s, "00"));
        JFormattedTextField tf = ((JSpinner.DefaultEditor) s.getEditor()).getTextField();
        tf.setFont(new Font("Segoe UI", Font.BOLD, 34));
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        tf.setColumns(2);
    }
}