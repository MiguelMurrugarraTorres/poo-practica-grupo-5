/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

public class Selectores {

    public static final LocalTime APERTURA = LocalTime.of(8, 0);
    public static final LocalTime CIERRE   = LocalTime.of(22, 0);

    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalTime redondearArriba(LocalTime t) {
        if (t.getHour() == 23 && t.getMinute() > 45) {
            return CIERRE.plusMinutes(15);
        }
        int resto = t.getMinute() % 15;
        if (resto == 0 && t.getSecond() == 0 && t.getNano() == 0) {
            return t.withSecond(0).withNano(0);
        }
        return t.withSecond(0).withNano(0).plusMinutes(15 - resto);
    }

    public static LocalTime limitarHorarioAtencion(LocalTime t) {
        if (t.isBefore(APERTURA)) return APERTURA;
        if (t.isAfter(CIERRE)) return CIERRE;
        return t;
    }

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

    public static LocalTime elegirHora(Window padre, String titulo, LocalTime sugerida, LocalTime minimo) {
        LocalTime limiteInferior = APERTURA;
        if (minimo != null) {
            LocalTime m = redondearArriba(minimo);
            if (m.isAfter(limiteInferior)) limiteInferior = m;
        }

        if (limiteInferior.isAfter(CIERRE)) {
            JOptionPane.showMessageDialog(padre,
                    "No hay horarios disponibles dentro del horario de atención ("
                            + APERTURA.format(FMT_HORA) + " a " + CIERRE.format(FMT_HORA) + ").",
                    "Fuera de horario", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        List<LocalTime> opciones = new ArrayList<>();
        for (LocalTime t = limiteInferior; !t.isAfter(CIERRE); t = t.plusMinutes(15)) {
            opciones.add(t);
        }

        LocalTime preseleccion = limiteInferior;
        if (sugerida != null) {
            LocalTime candidata = redondearArriba(sugerida);
            if (!candidata.isBefore(limiteInferior) && !candidata.isAfter(CIERRE)) {
                preseleccion = candidata;
            }
        }

        final LocalTime[] resultado = {null};

        JDialog d = new JDialog(padre, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        d.setLayout(new BorderLayout(0, 14));
        ((JComponent) d.getContentPane()).setBorder(BorderFactory.createEmptyBorder(18, 26, 18, 26));
        d.getContentPane().setBackground(EstiloUI.FONDO);

        JLabel lblInfo = new JLabel(
                "Horario de atención: " + APERTURA.format(FMT_HORA) + " a " + CIERRE.format(FMT_HORA),
                SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(90, 90, 90));

        JComboBox<LocalTime> combo = new JComboBox<>(opciones.toArray(new LocalTime[0]));
        combo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        combo.setSelectedItem(preseleccion);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(CENTER);
                if (value instanceof LocalTime) setText(((LocalTime) value).format(FMT_HORA));
                return this;
            }
        });

        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setOpaque(false);
        centro.add(lblInfo, BorderLayout.NORTH);
        centro.add(combo, BorderLayout.CENTER);

        EstiloUI.BotonRedondo cancelar = new EstiloUI.BotonRedondo("Cancelar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        EstiloUI.BotonRedondo aceptar = new EstiloUI.BotonRedondo("Seleccionar", EstiloUI.LILA, EstiloUI.LILA_HOVER);
        cancelar.setPreferredSize(new Dimension(110, 32));
        aceptar.setPreferredSize(new Dimension(130, 32));
        cancelar.addActionListener(e -> d.dispose());
        aceptar.addActionListener(e -> {
            resultado[0] = (LocalTime) combo.getSelectedItem();
            d.dispose();
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        botones.setOpaque(false);
        botones.add(cancelar);
        botones.add(aceptar);

        d.add(centro, BorderLayout.CENTER);
        d.add(botones, BorderLayout.SOUTH);
        d.getRootPane().setDefaultButton(aceptar);
        d.pack();                       // ← antes era d.setSize(300, 190), cortaba el botón Aceptar
        d.setMinimumSize(d.getSize());
        d.setResizable(false);
        d.setLocationRelativeTo(padre);
        d.setVisible(true);
        return resultado[0];
    }
}