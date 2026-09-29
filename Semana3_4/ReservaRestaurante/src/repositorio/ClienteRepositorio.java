/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorio;


import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;

public class ClienteRepositorio {

    private static final List<Cliente> CLIENTES = new ArrayList<>();

    static {
        CLIENTES.add(new Cliente(1, "12345678", "Ana", "Torres", "999111222", "ana@mail.com"));
        CLIENTES.add(new Cliente(2, "87654321", "Luis", "Ramos", "999333444", "luis@mail.com"));
        CLIENTES.add(new Cliente(3, "45678912", "Rosa", "Quispe", "999555666", "rosa@mail.com"));
    }

    public static Cliente buscarPorDni(String dni) {
        for (Cliente c : CLIENTES) {
            if (c.getDni().equals(dni)) return c;
        }
        return null;
    }

    public static int siguienteId() {
        int max = 0;
        for (Cliente c : CLIENTES) max = Math.max(max, c.getIdSocio());
        return max + 1;
    }

    public static void guardar(Cliente cliente) { CLIENTES.add(cliente); }
}
