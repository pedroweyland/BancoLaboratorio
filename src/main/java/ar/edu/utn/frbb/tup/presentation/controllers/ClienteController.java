package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.administracion.clientes.*;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.validator.Validaciones.clienteEsValido;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final CrearCliente crearCliente;
    private final EliminarCliente eliminarCliente;
    private final ModificarCliente modificarCliente;
    private final MostrarCliente mostrarCliente;
    private final MostrarTodosClientes mostrarTodosClientes;
    private final ClienteService clienteService;

    public ClienteController(CrearCliente crearCliente, EliminarCliente eliminarCliente, ModificarCliente modificarCliente, MostrarCliente mostrarCliente, MostrarTodosClientes mostrarTodosClientes, ClienteService clienteService) {
        this.crearCliente = crearCliente;
        this.eliminarCliente = eliminarCliente;
        this.modificarCliente = modificarCliente;
        this.mostrarCliente = mostrarCliente;
        this.mostrarTodosClientes = mostrarTodosClientes;
        this.clienteService = clienteService;
        clienteService.inicializarClientes();
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
    public Cliente createCliente(@RequestBody Cliente cliente){
        try {
            Cliente clienteCrear = clienteEsValido(cliente);
            return  crearCliente.crearCliente(clienteCrear);//Válido que los datos fueron ingresados
        } catch (ClienteExistenteException | ClienteMenorDeEdadException | ClienteFechaDeAltaInvalidaException | FaltaDeDatosException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @DeleteMapping("/{dni}")
    public Cliente deleteCliente(@PathVariable long dni) throws ClienteNoEncontradoException {
        return eliminarCliente.eliminarCliente(dni);
    }

    @PutMapping("/{dni}")
    public Cliente updateCliente(@PathVariable long dni, @RequestBody Cliente cliente) throws ClienteNoEncontradoException {
        return modificarCliente.modificarCliente(dni, cliente);
    }
}
