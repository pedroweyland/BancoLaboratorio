package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.*;
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
    public Cliente createCliente(@RequestBody Cliente cliente){
        try {
            Cliente clienteCrear = clienteEsValido(cliente);
            return  clienteService.crearCliente(clienteCrear);//Válido que los datos fueron ingresados
        } catch (ClienteExistenteException | ClienteMenorDeEdadException | ClienteFechaDeAltaInvalidaException | FaltaDeDatosException e) {
            System.out.println(e.getMessage());
        }
        return null;
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
