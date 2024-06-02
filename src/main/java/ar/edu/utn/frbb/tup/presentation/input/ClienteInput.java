package ar.edu.utn.frbb.tup.presentation.input;

import ar.edu.utn.frbb.tup.exception.ClienteFechaDeAltaInvalidaException;
import ar.edu.utn.frbb.tup.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.administracion.clientes.CrearCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static ar.edu.utn.frbb.tup.presentation.validator.Validaciones.*;

@Component
public class ClienteInput extends BasePresentation {
    private Scanner scanner = new Scanner(System.in);
    @Autowired
    CrearCliente crearCliente;

    public void ingresoCliente() {

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

        crearCliente.crearCliente(cliente);

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
        long dni;
        while (true) {
            try {
                System.out.println("Ingrese el dni del cliente: ");
                String dniStr = scanner.nextLine();

                dni = esNumeroLong(dniStr);

                if (dni < 10000000 || dni > 99999999) {
                    throw new NumberFormatException("El dni debe tener 8 digitos");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("---------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        }
        return dni;
    }

    public LocalDate ingresarFechaNacimiento() {
        LocalDate fechaNacimiento = null;
        while (true) {
            try {
                System.out.println("Ingrese la fecha de nacimiento del cliente (Formato: YYYY-MM-DD): ");
                String fechaNacimientoStr = scanner.nextLine();

                //Valido si la fecha ingresada es correcta
                fechaNacimiento = esFechaValida(fechaNacimientoStr);
                //Valido si el cliente es mayor de edad
                esMayor(fechaNacimiento);

                break;

            } catch (DateTimeParseException | ClienteMenorDeEdadException e) {
                System.out.println("---------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        }
        return fechaNacimiento;
    }

    public TipoPersona ingresarTipoPersona() {

        while (true) {
            try {
                System.out.println("Ingrese el tipo de persona Fisica(F) o Juridica(J): ");
                String tipoPersonaStr = scanner.nextLine().toUpperCase();

                return TipoPersona.fromString(tipoPersonaStr); //Retorno el tipo persona
            } catch (IllegalArgumentException e) {
                System.out.println("---------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        }

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
        LocalDate fechaAlta = null;

        while (true) {
            try {
                System.out.println("Ingrese la fecha de alta del cliente (Formato: YYYY-MM-DD): ");
                String fechaAltaStr = scanner.nextLine();
                //Valido si la fecha ingresada es correcta
                fechaAlta = esFechaValida(fechaAltaStr);

                //Valido si la fecha de alta es mayor a la fecha de nacimiento (diferencia de 18 años)
                esFechaAltaValida(fechaAlta, fechaNacimiento);

                break;
            } catch (DateTimeParseException | ClienteFechaDeAltaInvalidaException e) {
                System.out.println("---------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        }

        return fechaAlta;
    }
}
