package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MostrarTodosClientes extends BaseGestion {
    ClienteDao clienteDao;

    public MostrarTodosClientes(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    // Mostrar todos los clientes
    public void mostrarTodosClientes() {

        try {
            //Leo toda la lista de clientes, si no hay clientes lanza una excepcion
            List<Cliente> clientes = clienteDao.findAllClientes();

            int contador = 1;

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
