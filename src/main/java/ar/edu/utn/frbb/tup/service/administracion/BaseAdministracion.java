package ar.edu.utn.frbb.tup.service.administracion;


import java.util.Scanner;


public class BaseAdministracion {
    protected Scanner scanner = new Scanner(System.in);

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
