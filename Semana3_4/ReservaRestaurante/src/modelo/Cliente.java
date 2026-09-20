package restaurante.modelo;

public class Cliente extends Persona {

    private int idSocio;

    public Cliente(
            int dni,
            String nombres,
            String apellidos,
            String telefono,
            String correo,
            int idSocio) {

        super(
                dni,
                nombres,
                apellidos,
                telefono,
                correo
        );

        this.idSocio = idSocio;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public Reserva solicitarReserva() {
        return null;
    }
}