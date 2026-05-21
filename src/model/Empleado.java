package model;

public class Empleado extends Persona {

    private double salario;

    public Empleado(String nombre, int edad, double salario) {

        super(nombre, edad);
        this.salario = salario;

    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {

        if (salario > 0) {

            this.salario = salario;

        }

    }

}
