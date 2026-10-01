/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import excepciones.ClienteYaExistenteException;
import excepciones.DatosInvalidosException;
import modelo.Cliente;
import repositorio.ClienteRepositorio;

public class ClienteServicio {

    private static final String PATRON_CORREO = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    public Cliente registrarCliente(String dni, String nombres, String apellidos,
                                     String telefono, String correo)
            throws DatosInvalidosException, ClienteYaExistenteException {

        if (dni == null || !dni.matches("\\d{8}")) {
            throw new DatosInvalidosException("El DNI debe tener 8 dígitos.");
        }
        if (nombres == null || nombres.isBlank() || apellidos == null || apellidos.isBlank()) {
            throw new DatosInvalidosException("Completa nombres y apellidos.");
        }
        if (correo == null || !correo.trim().matches(PATRON_CORREO)) {
            throw new DatosInvalidosException("El correo debe tener un formato válido, con @ y dominio (ejemplo: nombre@gmail.com).");
        }
        if (ClienteRepositorio.buscarPorDni(dni) != null) {
            throw new ClienteYaExistenteException("Ya existe un cliente con el DNI " + dni + ".");
        }

        Cliente c = new Cliente(0, dni, nombres.trim(), apellidos.trim(),
                telefono == null ? "" : telefono.trim(), correo.trim());
        ClienteRepositorio.guardar(c);   // el id real lo asigna MySQL
        return c;
    }
}