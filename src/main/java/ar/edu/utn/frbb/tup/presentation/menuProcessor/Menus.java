package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import org.springframework.stereotype.Component;

import java.util.Scanner;



@Component
public class Menus extends BasePresentation {
    private static Scanner scanner = new Scanner(System.in);

    public static int menuPrincipal() {

        System.out.println("---------------------------------------");
        System.out.println("| Bienveido a la aplicacion de Banco! |");
        System.out.println("| 1. Ir al menu Cliente               |");
        System.out.println("| 2. Ir al menu Cuenta                |");
        System.out.println("| 3. Operaciones                      |");
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su opcion (0-3): ");

        int opcion = obtenerOpcionValida(3); //Pido una opcion valida, que este entre 0 y 3

        clearScreen();

        return opcion;
    }

    public static int menuCliente() {

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

        int opcion = obtenerOpcionValida(5); //Pido una opcion valida, que este entre 0 y 5

        clearScreen();

        return opcion;
    }

    public static int menuCuenta() {
        System.out.println("---------------------------------------");
        System.out.println("|    Bienveido al menu de Cuenta!     |");
        System.out.println("| 1. Crear una nueva Cuenta           |");
        System.out.println("| 2. Eliminar un Cuenta               |");
        System.out.println("| 3. Mostrar un Cuentas del cliente   |");
        System.out.println("| 4. Dar Alta/Baja Cuenta             |");
        System.out.println("| 0. Salir                            |");
        System.out.println("---------------------------------------");
        System.out.print(" Ingrese su Opcion (0-4): ");

        int opcion = obtenerOpcionValida(4); //Pido una opcion valida, que este entre 0 y 4

        clearScreen();

        return opcion;
    }

    public static int menuOperaciones() {

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

        int opcion = obtenerOpcionValida(5); //Pido una opcion valida, que este entre 0 y 5

        clearScreen();

        return opcion;
    }

    public static int menuModificacion(){

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

        int opcion = obtenerOpcionValida(6); //Pido una opcion valida, que este entre 0 y 6

        clearScreen();

        return opcion;
    }

    private static int obtenerOpcionValida(int max) {
        int opcion;
        while (true) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 0 && opcion <= max) {
                    break;
                } else {
                    System.out.println("Opcion invalida. Por favor seleccione un numero entre " + 0 + "-" + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Opcion invalida. Por favor seleccione un numero entre " + 0 + "-" + max);
            }
        }
        return opcion;
    }
}
