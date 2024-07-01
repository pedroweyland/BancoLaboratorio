package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.exception.ClientesException.*;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.validator.Validaciones.clienteEsValido;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
        clienteService.inicializarClientes();
    }

    @GetMapping
    public List<Cliente> getAllClientes() throws ClientesVaciosException {
        return clienteService.mostrarTodosClientes();
    }

    @GetMapping("/{dni}")
    public Cliente getClienteById(@PathVariable long dni) throws ClienteNoEncontradoException {
        return clienteService.mostrarCliente(dni);
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) throws ClienteMenorDeEdadException, ClienteFechaDeAltaInvalidaException, ClienteExistenteException {
        return  clienteService.crearCliente(cliente);
    }

    @DeleteMapping("/{dni}")
    public Cliente deleteCliente(@PathVariable long dni) throws ClienteNoEncontradoException {
        return clienteService.eliminarCliente(dni);
    }

    @PutMapping("/{dni}")
    public Cliente updateCliente(@PathVariable long dni, @RequestBody Cliente cliente) throws ClienteNoEncontradoException {
        return clienteService.modificarCliente(dni, cliente);
    }
}
