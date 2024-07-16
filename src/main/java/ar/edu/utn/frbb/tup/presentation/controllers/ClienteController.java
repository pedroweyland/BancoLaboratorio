package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.ClientesException.*;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private ClienteValidator clienteValidator;
    private ClienteService clienteService;

    public ClienteController(ClienteValidator clienteValidator, ClienteService clienteService) {
        this.clienteValidator = clienteValidator;
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
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteDto clienteDto) throws ClienteMenorDeEdadException, ClienteExistenteException {
        clienteValidator.validateCliente(clienteDto);
        Cliente cliente = clienteService.crearCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @DeleteMapping("/{dni}")
    public Cliente deleteCliente(@PathVariable long dni) throws ClienteNoEncontradoException {
        return clienteService.eliminarCliente(dni);
    }

    @PutMapping("/")
    public Cliente updateCliente(@RequestBody ClienteDto clienteDto) throws ClienteNoEncontradoException, ClienteMenorDeEdadException {
        clienteValidator.validateClienteModificacion(clienteDto);
        return clienteService.modificarCliente(clienteDto);
    }
}
