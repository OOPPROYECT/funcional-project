package model;

public class Cliente extends Persona {

    private String correo;

    public Cliente(String nombre, int edad, String correo) {

        super(nombre, edad);
        this.correo = correo;

    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {

        this.correo = correo;

    }

    @Override
    public String toString() {

        return super.toString() +
               " | Correo: " + correo;

    }

}
