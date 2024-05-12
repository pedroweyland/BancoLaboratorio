package ar.edu.utn.frbb.tup.inputs;

import java.util.Scanner;

public class BaseInput {
    //Clase para limpiar la pantalla

    protected Scanner scanner = new Scanner(System.in);

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
