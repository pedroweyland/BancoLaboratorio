package ar.edu.utn.frbb.tup.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;

public class MostrarTodosClientes extends BaseGestion {

    // Mostrar todos los clientes
    public void mostrarTodosClientes(Banco banco){
        List<Cliente> clientes = banco.getClientes(); //Creo una lista auxiliar 'cliente' con la copia de Clientes que esta en banco ++Legibilidad
        int contador = 1;


        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                System.out.println("------------ Cliente Nro " + contador + " ------------");
                System.out.println(toString(cliente));
                contador++;
            }
        } else {
            System.out.println("No hay clientes registrados");
        }
        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();
    }

}
