package ar.edu.utn.frbb.tup.menuBuilder;

import ar.edu.utn.frbb.tup.inputs.BaseInput;

import java.util.Scanner;

import static ar.edu.utn.frbb.tup.validator.Validaciones.esNumero;

public class Menus extends BaseInput {

    public static int menuPrincipal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---------------------------------------");
        System.out.println("| Bienveido a la aplicación de Banco! |");
        System.out.println("| 1. Ir al menu Cliente               |");
        System.out.println("| 2. Ir al menu Cuenta                |");
        System.out.println("| 3. Operaciones                      |");
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su opción (0-3): ");

        String opcion = scanner.nextLine();
        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 4) {
            System.out.println("Opción inválida. Por favor seleccione un número entre 0 y 3.");
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
        System.out.print(" Ingrese su opción (0-5): ");

        String opcion = scanner.nextLine();
        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 6) {
            System.out.println("Opción inválida. Por favor seleccione un número entre 0 y 5.");
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
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su opción (0-3): ");
        String opcion = scanner.nextLine();

        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 4) { // Valido si es numero y si esta entre 0 y 5
            System.out.println("Opción inválida. Por favor seleccione un número entre 0-3.");
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
        System.out.print(" Ingrese su opción (0-5): ");
        String opcion = scanner.nextLine();

        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 6) { // Valido si es numero y si esta entre 0 y 4
            System.out.println("Opción inválida. Por favor seleccione un número entre 0-5.");
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
        System.out.println("| 4. Fecha de nacimiento                  |");
        System.out.println("| 5. Tipo de persona                      |");
        System.out.println("| 6. Banco                                |");
        System.out.println("| 7. Mail                                 |");
        System.out.println("| 0. Salir                                |");
        System.out.println("-------------------------------------------");
        System.out.print(" Ingrese su opción (0-7): ");

        String opcion = scanner.nextLine();
        while (!esNumero(opcion) || Integer.parseInt(opcion) < 0 || Integer.parseInt(opcion) >= 8) {
            System.out.println("Opción inválida. Por favor seleccione un número entre 0 y 7.");
            opcion = scanner.nextLine();
        }

        return Integer.parseInt(opcion);

    }

}
