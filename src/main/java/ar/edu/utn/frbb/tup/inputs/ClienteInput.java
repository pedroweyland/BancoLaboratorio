package ar.edu.utn.frbb.tup.inputs;

import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.TipoPersona;

import java.time.LocalDate;

import static ar.edu.utn.frbb.tup.validator.Validaciones.*;

public class ClienteInput extends BaseInput {

    public Cliente ingresoCliente() {

        Cliente cliente = new Cliente();
        clearScreen();

        //Usuario ingresa datos del cliente
        cliente.setNombre(ingresarNombre()).setApellido(ingresarApellido()).setDireccion(ingresarDireccion());
        cliente.setDni(ingresarDni());

        LocalDate fechaNacimiento = ingresarFechaNacimiento();
        cliente.setFechaNacimiento(fechaNacimiento);

        cliente.setTipoPersona(ingresarTipoPersona());
        cliente.setBanco(ingresarBanco());
        cliente.setMail(ingresarMail());

        cliente.setFechaAlta(ingresarFechaAlta(fechaNacimiento));

        clearScreen();
        return cliente;
    }

    public String ingresarNombre() {
        System.out.println("Ingrese el nombre del cliente: ");
        return scanner.nextLine();
    }

    public String ingresarApellido() {
        System.out.println("Ingrese el apellido del cliente: ");
        return scanner.nextLine();
    }

    public String ingresarDireccion() {
        System.out.println("Ingrese la direccion del cliente: ");
        return scanner.nextLine();
    }

    public long ingresarDni() {
        System.out.println("Ingrese el dni del cliente: ");
        String dni = scanner.nextLine();
        while (!esNumeroLong(dni)) {
            System.out.println("Dni invalido. Ingrese el dni del cliente: ");
            dni = scanner.nextLine();
        }
        return Long.parseLong(dni);
    }

    public LocalDate ingresarFechaNacimiento() {
        System.out.println("Ingrese la fecha de nacimiento del cliente (Formato: YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();

        while (!esFechaValida(fechaNacimiento)) {
            System.out.println("Fecha de nacimiento invalida. Ingrese la fecha de nacimiento del cliente (Formato: YYYY-MM-DD): ");
            fechaNacimiento = scanner.nextLine();
        }

        while (!esMayor(LocalDate.parse(fechaNacimiento))) {
            System.out.println("Tiene que ser mayor de edad para ser cliente. (Formato: YYYY-MM-DD): ");
            fechaNacimiento = scanner.nextLine();
        }

        return LocalDate.parse(fechaNacimiento);
    }

    public TipoPersona ingresarTipoPersona() {
        String tipoPersonaStr = null;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("Ingrese el tipo de persona Fisica(F) o Juridica(J): ");
                tipoPersonaStr = scanner.nextLine().toUpperCase();
                return TipoPersona.fromString(tipoPersonaStr); //Retorno el tipo persona
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Por favor, ingrese un valor valido (F o J).");
            }
        }
        // Esto nunca debería ocurrir si el bucle funciona correctamente
        return null;
    }

    public String ingresarBanco() {
        System.out.println("Ingrese el banco del cliente: ");
        return scanner.nextLine();
    }

    public String ingresarMail() {
        System.out.println("Ingrese el mail del cliente: ");
        return scanner.nextLine();
    }

    public LocalDate ingresarFechaAlta(LocalDate fechaNacimiento) {
        System.out.println("Ingrese la fecha de alta del cliente (Formato: YYYY-MM-DD): ");
        String fechaAlta = scanner.nextLine();


        while (!esFechaAltaValida(fechaAlta, fechaNacimiento)) {
            fechaAlta = scanner.nextLine();
        }

        return LocalDate.parse(fechaAlta);
    }
}
