package ar.edu.utn.frbb.tup.service.administracion.gestion;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.CuentasDeClientesDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static ar.edu.utn.frbb.tup.presentation.input.validator.Validaciones.esNumeroLong;

public class BaseGestion {
    protected Scanner scanner = new Scanner(System.in);
    protected ClienteDao clienteDao = new ClienteDao();
    protected CuentaDao cuentaDao = new CuentaDao();
    protected CuentasDeClientesDao cuentasDeClientes = new CuentasDeClientesDao();
    protected MovimientosDao movimientosDao = new MovimientosDao();

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Funciones extras para reducir codigo

    //Dependiendo que tipo de parametro se le pase usa una funcion o la otra
    public String toString(Cliente cliente) {
        return  "Nombre: " + cliente.getNombre() + "\n" +
                "Apellido: " + cliente.getApellido() + "\n" +
                "Direccion: " + cliente.getDireccion() + "\n" +
                "Dni: " + cliente.getDni() + "\n" +
                "Fecha de nacimiento: " + cliente.getFechaNacimiento() + "\n" +
                "Tipo de persona: " + cliente.getTipoPersona() + "\n" +
                "Banco: " + cliente.getBanco() + "\n" +
                "Mail: " + cliente.getMail() + "\n" +
                "Fecha de alta: " + cliente.getFechaAlta() + "\n" +
                "----------------------------------------";
    }

    public String toString(Cuenta cuenta){
        return "Nombre de la cuenta: " + cuenta.getNombre() + "\n" +
                "Estado: " + cuenta.getEstado() + "\n" +
                "Saldo: " + cuenta.getSaldo() + "\n" +
                "CVU: " + cuenta.getCVU() + "\n" +
                "DNI titular: " + cuenta.getDniTitular() + "\n" +
                "Fecha de Creacion: " + cuenta.getFechaCreacion() + "\n" +
                "Tipo de cuenta: " + cuenta.getTipoCuenta() + "\n" +
                "----------------------------------------";
    }

}