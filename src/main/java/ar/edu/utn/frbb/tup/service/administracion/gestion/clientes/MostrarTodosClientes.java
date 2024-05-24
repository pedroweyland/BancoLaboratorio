package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;

import java.util.List;

public class MostrarTodosClientes extends BaseGestion {

    // Mostrar todos los clientes
    public void mostrarTodosClientes() {

        int contador = 1;
        List<Cliente> clientes = clienteDao.findAllClientes();

        try {

            if (clientes.isEmpty()) { //Si la lista clientes esta vacia lanzo una exepcion (Ya que no hay nada que mostrar)
                throw new ClientesVaciosException("No hay clientes registrados");
            }

            for (Cliente cliente : clientes) {
                System.out.println("------------ Cliente Nro " + contador + " ------------");
                System.out.println(toString(cliente));
                contador++;
            }

        } catch (ClientesVaciosException ex){
            System.out.println("----------------------------------------");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------");
        } finally {
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }


    }

}
