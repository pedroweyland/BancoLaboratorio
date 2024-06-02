package ar.edu.utn.frbb.tup.presentation;

import org.springframework.stereotype.Component;

import java.util.Scanner;

import static ar.edu.utn.frbb.tup.presentation.input.validator.Validaciones.esDouble;
import static ar.edu.utn.frbb.tup.presentation.input.validator.Validaciones.esNumeroLong;

@Component
public class BasePresentation {

    protected Scanner scanner = new Scanner(System.in);

    //Metodo limpiar consola
    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static long pedirDni(String texto){
        //Funcion para que el usuario ingrese dni, valido y devuelve en tipo 'long'
        Scanner input = new Scanner(System.in);

        System.out.println(texto);
        String dni = input.nextLine();

        while (!esNumeroLong(dni)){
            System.out.println("Error, ingrese un numero: ");
            dni = input.nextLine();
        }

        return Long.parseLong(dni);
    }

    public static long pedirCvu(String texto){
        //Funcion para que el usuario ingrese Cvu de cuenta, valido y devuelve en tipo 'long'
        Scanner input = new Scanner(System.in);

        System.out.println(texto);
        String cvu = input.nextLine();

        while (!esNumeroLong(cvu)){
            System.out.println("Error, ingrese un numero: ");
            cvu = input.nextLine();
        }

        return Long.parseLong(cvu);
    }

    public static boolean pedirOpcion(String texto){
        //Pido opcion para ver si quiere dar de baja o de alta
        Scanner input = new Scanner(System.in);

        System.out.println(texto);
        String opcion = input.nextLine();

        //Si ingresa otro tipo de letra vuelve error
        while (!opcion.equalsIgnoreCase("a") && !opcion.equalsIgnoreCase("b")){
            System.out.println("Error, ingrese A (Alta) o B (Baja)");
            opcion = input.nextLine();
        }

        return opcion.equalsIgnoreCase("a");
    }

    public static double ingresarDinero(String mensaje) {
        Scanner input = new Scanner(System.in);
        boolean seguir = true;

        while (seguir) {

            System.out.println(mensaje); //Usuario ingresado la cantidad de dinero
            String dineroStr = input.nextLine();

            double dinero = esDouble(dineroStr); //Valido que sea un numero

            if (dinero == -1){ //Si no es un numero devuelve -1, por ende marca error
                System.out.println("El valor ingresado no es valido, ingrese un valor numerico");
            } else if (dinero <= 0){ //El valor tiene que ser mayor a 0
                System.out.println("El valor ingresado debe ser mayor a 0");
            } else {
                return dinero;
            }

        }
        return 0;
    }
}
