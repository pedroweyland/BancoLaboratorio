package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.exception.ClientesException.*;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
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

    public void inicializarClientes() {
        clienteDao.inicializarClientes();
    }

    public Cliente crearCliente(ClienteDto clienteDto) throws ClienteMenorDeEdadException, ClienteExistenteException {
        return crearCliente.crearCliente(clienteDto);
    }

    public Cliente eliminarCliente(long dni) throws ClienteNoEncontradoException {
        return eliminarCliente.eliminarCliente(dni);
    }

    public Cliente modificarCliente(ClienteDto clienteDto) throws ClienteNoEncontradoException, ClienteMenorDeEdadException {
        return modificarCliente.modificarCliente(clienteDto);
    }

    public Cliente mostrarCliente(long dni) throws ClienteNoEncontradoException {
        return mostrarCliente.mostrarCliente(dni);
    }

    public List<Cliente> mostrarTodosClientes() throws ClientesVaciosException {
        return mostrarTodosClientes.mostrarTodosClientes();
    }
}
