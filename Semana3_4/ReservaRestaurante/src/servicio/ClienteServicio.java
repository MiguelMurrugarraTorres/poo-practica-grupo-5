/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import excepciones.ClienteYaExistenteException;
import excepciones.DatosInvalidosException;
import modelo.Cliente;
import repositorio.ClienteRepositorio;
/**
 *
 * @author Diego
 */
public class ClienteServicio {
 public Cliente registrarCliente(String dni, String nombres, String apellidos,
                                     String telefono, String correo)
            throws DatosInvalidosException, ClienteYaExistenteException {

        if (dni == null || !dni.matches("\\d{8}")) {
            throw new DatosInvalidosException("El DNI debe tener 8 dígitos.");
        }
        if (nombres == null || nombres.isBlank() || apellidos == null || apellidos.isBlank()) {
            throw new DatosInvalidosException("Completa nombres y apellidos.");
        }
        if (ClienteRepositorio.buscarPorDni(dni) != null) {
            throw new ClienteYaExistenteException("Ya existe un cliente con el DNI " + dni + ".");
        }

        Cliente c = new Cliente(ClienteRepositorio.siguienteId(), dni, nombres.trim(),
                apellidos.trim(), telefono == null ? "" : telefono.trim(),
                correo == null ? "" : correo.trim());
        ClienteRepositorio.guardar(c);
        return c;
    }
}