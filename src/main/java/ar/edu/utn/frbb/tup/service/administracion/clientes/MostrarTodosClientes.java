package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MostrarTodosClientes extends BaseAdministracion {
    ClienteDao clienteDao;

    public MostrarTodosClientes(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    // Mostrar todos los clientes
    public List<Cliente> mostrarTodosClientes() {

        try {

            //Leo toda la lista de clientes, si no hay clientes lanza una excepcion
            return clienteDao.findAllClientes();

        } catch (ClientesVaciosException ex){
            System.out.println("----------------------------------------");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------");
        }
        return null;
    }
}
