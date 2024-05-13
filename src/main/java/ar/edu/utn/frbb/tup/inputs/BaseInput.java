package ar.edu.utn.frbb.tup.inputs;

import java.util.Scanner;

public class BaseInput {


    protected Scanner scanner = new Scanner(System.in);

    //Metodo limpiar consola
    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
