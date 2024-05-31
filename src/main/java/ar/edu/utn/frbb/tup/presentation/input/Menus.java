package ar.edu.utn.frbb.tup.presentation.input;

import org.springframework.stereotype.Component;

import java.util.Scanner;

import static ar.edu.utn.frbb.tup.presentation.input.validator.Validaciones.esNumero;

@Component
public class Menus extends BaseInput {

    public static int menuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        clearScreen();

        System.out.println("---------------------------------------");
        System.out.println("| Bienveido a la aplicacion de Banco! |");
        System.out.println("| 1. Ir al menu Cliente               |");
        System.out.println("| 2. Ir al menu Cuenta                |");
        System.out.println("| 3. Operaciones                      |");
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su opcion (0-3): ");

        String opcion = scanner.nextLine();
        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 4) {
            System.out.println("Opcion invalida. Por favor seleccione un numero entre 0 y 3.");
            opcion = scanner.nextLine();
        }

        clearScreen();

        return Integer.parseInt(opcion);
    }
    public static int menuCliente() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------------------------");
        System.out.println("|    Bienveido al menu de Cliente!    |");
        System.out.println("| 1. Crear un nuevo Cliente           |");
        System.out.println("| 2. Modificar un Cliente             |");
        System.out.println("| 3. Eliminar un Cliente              |");
        System.out.println("| 4. Mostrar un Cliente               |");
        System.out.println("| 5. Mostrar todos los Cliente        |");
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su Opcion (0-5): ");

        String opcion = scanner.nextLine();
        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 6) {
            System.out.println("Opcion invalida. Por favor seleccione un numero entre 0 y 5.");
            opcion = scanner.nextLine();
        }

        clearScreen();

        return Integer.parseInt(opcion);
    }

    public static int menuCuenta() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------");
        System.out.println("|    Bienveido al menu de Cuenta!     |");
        System.out.println("| 1. Crear una nueva Cuenta           |");
        System.out.println("| 2. Eliminar un Cuenta               |");
        System.out.println("| 3. Mostrar un Cuentas del cliente   |");
        System.out.println("| 4. Dar Alta/Baja Cuenta             |");
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su Opcion (0-4): ");
        String opcion = scanner.nextLine();

        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 5) { // Valido si es numero y si esta entre 0 y 4
            System.out.println("Opcion invalida. Por favor seleccione un numero entre 0-4.");
            opcion = scanner.nextLine();
        }

        clearScreen();

        return Integer.parseInt(opcion);
    }

    public static int menuOperaciones() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------");
        System.out.println("|    Bienveido al menu de Operaciones! |");
        System.out.println("| 1. Depositar                         |");
        System.out.println("| 2. Retirar                           |");
        System.out.println("| 3. Transferencia                     |");
        System.out.println("| 4. Consultar saldo                   |");
        System.out.println("| 5. Ver movimientos                   |");
        System.out.println("| 0. Salir                             |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su Opcion (0-5): ");
        String opcion = scanner.nextLine();

        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 6) { // Valido si es numero y si esta entre 0 y 5
            System.out.println("Opcion invalida. Por favor seleccione un numero entre 0-5.");
            opcion = scanner.nextLine();
        }
        clearScreen();
        return Integer.parseInt(opcion);
    }

    public static int menuModificacion(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------- Modifique su cliente ----------");
        System.out.println("| 1. Nombre                               |");
        System.out.println("| 2. Apellido                             |");
        System.out.println("| 3. Direccion                            |");
        System.out.println("| 4. Tipo de persona                      |");
        System.out.println("| 5. Banco                                |");
        System.out.println("| 6. Mail                                 |");
        System.out.println("| 0. Salir                                |");
        System.out.println("-------------------------------------------");
        System.out.print(" Ingrese su Opcion (0-6): ");

        String opcion = scanner.nextLine();
        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 7) {
            System.out.println("Opcion invalida. Por favor seleccione un numero entre 0 y 6.");
            opcion = scanner.nextLine();
        }

        clearScreen();

        return Integer.parseInt(opcion);

    }

}
