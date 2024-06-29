package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.administracion.clientes.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteService {
    private final CrearCliente crearCliente;
    private final EliminarCliente eliminarCliente;
    private final ModificarCliente modificarCliente;
    private final MostrarCliente mostrarCliente;
    private final MostrarTodosClientes mostrarTodosClientes;
    private final ClienteDao clienteDao;

    public ClienteService(CrearCliente crearCliente, EliminarCliente eliminarCliente, ModificarCliente modificarCliente, MostrarCliente mostrarCliente, MostrarTodosClientes mostrarTodosClientes, ClienteDao clienteDao) {
        this.crearCliente = crearCliente;
        this.eliminarCliente = eliminarCliente;
        this.modificarCliente = modificarCliente;
        this.mostrarCliente = mostrarCliente;
        this.mostrarTodosClientes = mostrarTodosClientes;
        this.clienteDao = clienteDao;
    }

    public List<Cliente> findAllClientes() throws ClientesVaciosException {
        return clienteDao.findAllClientes();
    }

    public void inicializarClientes() {
        clienteDao.inicializarClientes();
    }

    public Cliente crearCliente(Cliente cliente) throws ClienteMenorDeEdadException, ClienteFechaDeAltaInvalidaException, ClienteExistenteException {
        return crearCliente.crearCliente(cliente);
    }

    public Cliente eliminarCliente(long dni) throws ClienteNoEncontradoException {
        return eliminarCliente.eliminarCliente(dni);
    }

    public Cliente modificarCliente(long dni, Cliente clienteModificar) throws ClienteNoEncontradoException {
        return modificarCliente.modificarCliente(dni, clienteModificar);
    }

    public Cliente mostrarCliente(long dni) throws ClienteNoEncontradoException {
        return mostrarCliente.mostrarCliente(dni);
    }

    public List<Cliente> mostrarTodosClientes() throws ClientesVaciosException {
        return mostrarTodosClientes.mostrarTodosClientes();
    }
}
