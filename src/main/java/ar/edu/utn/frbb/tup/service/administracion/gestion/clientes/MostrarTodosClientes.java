package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;

import java.util.List;

public class MostrarTodosClientes extends BaseGestion {

    // Mostrar todos los clientes
    public void mostrarTodosClientes(){

        List<Cliente> clientes = clienteDao.findAllClientes();

        int contador = 1;

        //Condicional para verificar si existen clientes o no
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                System.out.println("------------ Cliente Nro " + contador + " ------------");
                System.out.println(toString(cliente));
                contador++;
            }
        } else {
            System.out.println("----------------------------------------");
            System.out.println("No hay clientes registrados");
            System.out.println("----------------------------------------");
        }

        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();
    }

}
