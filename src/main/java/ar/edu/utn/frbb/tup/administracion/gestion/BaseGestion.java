package ar.edu.utn.frbb.tup.administracion.gestion;

import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;

import java.util.List;
import java.util.Scanner;

import static ar.edu.utn.frbb.tup.validator.Validaciones.esNumeroLong;

public class BaseGestion {
    protected Scanner scanner = new Scanner(System.in);

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Funciones extras para reducir codigo

    public Cliente encontrarCliente(List<Cliente> clientes, long dni){
        //Funcion que encuentra cliente y retorna el cliente o Null si no lo encuentra

        for (Cliente cliente : clientes) { //Busco en la lista clientes si existe el dni que puso el usuario
            if (cliente.getDni() == dni){
                return cliente;
            }
        }
        return null;
    }

    public long pedirDni(String texto){
        //Funcion para que el usuario ingrese dni, valido y devuelve en tipo 'long'

        System.out.println(texto);
        String dni = scanner.nextLine();

        while (!esNumeroLong(dni)){
            System.out.println("Error, ingrese un numero: ");
            dni = scanner.nextLine();
        }

        return Long.parseLong(dni);
    }

    public long pedirCvu(String texto){
        //Funcion para que el usuario ingrese Cvu de cuenta, valido y devuelve en tipo 'long'

        System.out.println(texto);
        String cvu = scanner.nextLine();

        while (!esNumeroLong(cvu)){
            System.out.println("Error, ingrese un numero: ");
            cvu = scanner.nextLine();
        }

        return Long.parseLong(cvu);
    }

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
                "Cuentas asociadas: " + cliente.getNombreCuentas() + "\n" +
                "----------------------------------------";
    }

    public String toString(Cuenta cuenta){
        return "Nombre de la cuenta: " + cuenta.getNombre() + "\n" +
                "Estado: " + cuenta.getEstado() + "\n" +
                "Saldo: " + cuenta.getSaldo() + "\n" +
                "CVU: " + cuenta.getCVU() + "\n" +
                "Fecha de Creacion: " + cuenta.getFechaCreacion() + "\n" +
                "Tipo de cuenta: " + cuenta.getTipoCuenta() + "\n" +
                "Id: " + cuenta.getId() + "\n" +
                "----------------------------------------";
    }

}
