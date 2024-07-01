package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClientesVaciosException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MostrarTodosClientes {
    private final ClienteDao clienteDao;

    public MostrarTodosClientes(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    // Mostrar todos los clientes
    public List<Cliente> mostrarTodosClientes() throws ClientesVaciosException {

        List<Cliente> clientes = clienteDao.findAllClientes();

        if (clientes.isEmpty()){//Si la lista esta vacia significa que no hay clientes registrados
            throw new ClientesVaciosException("No hay clientes registrados");
        }

        //Leo toda la lista de clientes, si no hay clientes lanza una excepcion
        return clientes;

    }
}
