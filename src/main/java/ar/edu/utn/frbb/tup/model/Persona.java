package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.Period;

public class Persona {
    private String nombre;
    private String apellido;
    private String direccion;
    private long dni;
    private LocalDate fechaNacimiento;


    //Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public Persona setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }
    public Persona setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public String getDireccion() {
        return direccion;
    }
    public Persona setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public long getDni() {
        return dni;
    }
    public Persona setDni(long dni) {
        this.dni = dni;
        return this;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public Persona setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

}
