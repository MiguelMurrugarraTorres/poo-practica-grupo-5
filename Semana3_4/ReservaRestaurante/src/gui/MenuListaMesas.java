/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Mesa;
import servicio.MesaService;
/**
 *
 * @author Diego
 */
public class MenuListaMesas extends JDialog  {

    public static void mostrar(Window padre) {
        new MenuListaMesas(padre).setVisible(true);
    }

    private MenuListaMesas(Window padre) {
        super(padre, "Mesas registradas", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(EstiloUI.FONDO);

        JLabel barra = EstiloUI.crearBarra("Mesas registradas", 520);
        barra.setPreferredSize(new Dimension(520, 48));
        add(barra, BorderLayout.NORTH);

        String[] columnas = {"N° Mesa", "Capacidad", "Ubicación", "Estado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        MesaService mesaService = new MesaService();
        List<Mesa> mesas = mesaService.listarMesas();
        mesas.sort((a, b) -> Integer.compare(a.getNumeroMesa(), b.getNumeroMesa()));
        for (Mesa m : mesas) {
            modeloTabla.addRow(new Object[]{
                m.getNumeroMesa(),
                m.getCapacidad(),
                m.getUbicacion().getNombreUbicacion(),
                m.isEstado() ? "Activo" : "Inactivo"
            });
        }

        JTable tabla = new JTable(modeloTabla);
        tabla.setRowHeight(26);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.setEnabled(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(480, 320));
        add(scroll, BorderLayout.CENTER);

        EstiloUI.BotonRedondo btnCerrar =
                new EstiloUI.BotonRedondo("Cerrar", EstiloUI.ROJO, EstiloUI.ROJO_HOVER);
        btnCerrar.setPreferredSize(new Dimension(120, 30));
        btnCerrar.addActionListener(e -> dispose());
        JPanel pie = new JPanel();
        pie.setBackground(EstiloUI.FONDO);
        pie.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pie.add(btnCerrar);
        add(pie, BorderLayout.SOUTH);

        setSize(520, 440);
        setLocationRelativeTo(padre);
    }
}