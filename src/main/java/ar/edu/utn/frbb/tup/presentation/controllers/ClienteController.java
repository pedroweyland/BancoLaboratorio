package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.administracion.clientes.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final CrearCliente crearCliente;
    private final EliminarCliente eliminarCliente;
    private final ModificarCliente modificarCliente;
    private final MostrarCliente mostrarCliente;
    private final MostrarTodosClientes mostrarTodosClientes;

    public ClienteController(CrearCliente crearCliente, EliminarCliente eliminarCliente, ModificarCliente modificarCliente, MostrarCliente mostrarCliente, MostrarTodosClientes mostrarTodosClientes) {
        this.crearCliente = crearCliente;
        this.eliminarCliente = eliminarCliente;
        this.modificarCliente = modificarCliente;
        this.mostrarCliente = mostrarCliente;
        this.mostrarTodosClientes = mostrarTodosClientes;
    }

    @GetMapping
    public List<Cliente> getAllClientes() throws ClientesVaciosException {
        return mostrarTodosClientes.mostrarTodosClientes();
    }

    @GetMapping("/{dni}")
    public Cliente getClienteById(@PathVariable long dni) throws ClienteNoEncontradoException {
        return mostrarCliente.mostrarCliente(dni);
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) throws ClienteExistenteException {
        return crearCliente.crearCliente(cliente);
    }
}
