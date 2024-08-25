package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClientesVaciosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteControllerTest {

    @Mock
    ClienteService clienteService;

    @Mock
    ClienteValidator clienteValidator;

    @InjectMocks
    ClienteController clienteController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteController = new ClienteController(clienteValidator, clienteService);
    }

    @Test
    public void testMostrarTodosLosClientesSuccess() throws ClientesVaciosException {
        List<Cliente> cliente = new ArrayList<>();

        when(clienteService.mostrarTodosClientes()).thenReturn(cliente);

        ResponseEntity<List<Cliente>> getClientes = clienteController.getAllClientes();

        verify(clienteService, times(1)).mostrarTodosClientes();

        assertEquals(getClientes.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testMostarTodosLosClientesVaciosException() throws ClientesVaciosException {

        doThrow(ClientesVaciosException.class).when(clienteService).mostrarTodosClientes();

        assertThrows(ClientesVaciosException.class, () -> clienteController.getAllClientes());

        verify(clienteService, times(1)).mostrarTodosClientes();
    }

    @Test
    public void testMostrarClientePorDniSuccess() throws ClienteNoEncontradoException {
        Cliente cliente = BaseAdministracionTest.getCliente("Peperino", 12345678);

        when(clienteService.mostrarCliente(cliente.getDni())).thenReturn(cliente);

        ResponseEntity<Cliente> getCliente = clienteController.getClienteById(cliente.getDni());

        verify(clienteService, times(1)).mostrarCliente(cliente.getDni());

        assertEquals(getCliente.getStatusCode(), HttpStatus.OK);
        assertEquals(getCliente.getBody(), cliente);

    }

    @Test
    public void testMostrarClientePorDniNoEncontrado() throws ClienteNoEncontradoException {

        doThrow(ClienteNoEncontradoException.class).when(clienteService).mostrarCliente(12345678L);

        assertThrows(ClienteNoEncontradoException.class, () -> clienteController.getClienteById(12345678L));

        verify(clienteService, times(1)).mostrarCliente(12345678L);
    }

    @Test
    public void testModificarClienteSuccess() throws ClienteMenorDeEdadException, ClienteNoEncontradoException {
        ClienteDto clienteDto = BaseAdministracionTest.getClienteDto("Peperino", 12341234L);

        when(clienteService.modificarCliente(clienteDto)).thenReturn(new Cliente());

        ResponseEntity<Cliente> clienteModifcado = clienteController.updateCliente(clienteDto);

        verify(clienteService, times(1)).modificarCliente(clienteDto);
        verify(clienteValidator, times(1)).validateClienteModificacion(clienteDto);

        assertEquals(clienteModifcado.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void testModificarClienteNoEncontrado() throws ClienteMenorDeEdadException, ClienteNoEncontradoException {
        ClienteDto clienteDto = BaseAdministracionTest.getClienteDto("Peperino", 12341234L);

        doThrow(ClienteNoEncontradoException.class).when(clienteService).modificarCliente(clienteDto);

        assertThrows(ClienteNoEncontradoException.class, () -> clienteController.updateCliente(clienteDto));

        verify(clienteService, times(1)).modificarCliente(clienteDto);
        verify(clienteValidator, times(1)).validateClienteModificacion(clienteDto);

    }

    @Test
    public void testModificarClienteMenorEdad() throws ClienteMenorDeEdadException, ClienteNoEncontradoException {
        ClienteDto clienteDto = BaseAdministracionTest.getClienteDto("Peperino", 12341234L);
        clienteDto.setFechaNacimiento("2000-02-02");

        doThrow(ClienteMenorDeEdadException.class).when(clienteService).modificarCliente(clienteDto);

        assertThrows(ClienteMenorDeEdadException.class, () -> clienteController.updateCliente(clienteDto));

        verify(clienteService, times(1)).modificarCliente(clienteDto);
        verify(clienteValidator, times(1)).validateClienteModificacion(clienteDto);
    }

    @Test
    public void testEliminarClienteSuccess() throws ClienteNoEncontradoException {

        when(clienteService.eliminarCliente(12341234L)).thenReturn(new Cliente());

        ResponseEntity<Cliente> deleteCliente = clienteController.deleteCliente(12341234L);

        verify(clienteService, times(1)).eliminarCliente(12341234L);
    }

    @Test
    public void testEliminarClienteNoEncontrado() throws ClienteNoEncontradoException {

        doThrow(ClienteNoEncontradoException.class).when(clienteService).eliminarCliente(12341234L);

        assertThrows(ClienteNoEncontradoException.class, () -> clienteController.deleteCliente(12341234L));

        verify(clienteService, times(1)).eliminarCliente(12341234L);
    }

    @Test
    public void testCrearClienteSuccess() throws ClienteMenorDeEdadException, ClienteExistenteException {
        ClienteDto clienteDto = BaseAdministracionTest.getClienteDto("Peperino", 12341234L);

        when(clienteService.crearCliente(clienteDto)).thenReturn(new Cliente());

        ResponseEntity<Cliente> postCliente = clienteController.createCliente(clienteDto);

        verify(clienteService, times(1)).crearCliente(clienteDto);
        verify(clienteValidator, times(1)).validateCliente(clienteDto);

        assertEquals(postCliente.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void testCrearClienteExistente() throws ClienteMenorDeEdadException, ClienteExistenteException {
        ClienteDto clienteDto = BaseAdministracionTest.getClienteDto("Peperino", 12341234L);

        doThrow(ClienteExistenteException.class).when(clienteService).crearCliente(clienteDto);

        assertThrows(ClienteExistenteException.class, () -> clienteController.createCliente(clienteDto));

        verify(clienteValidator, times(1)).validateCliente(clienteDto);
        verify(clienteService, times(1)). crearCliente(clienteDto);
    }

    @Test
    public void testCrearClienteMenorEdad() throws ClienteMenorDeEdadException, ClienteExistenteException {
        ClienteDto clienteDto = BaseAdministracionTest.getClienteDto("Peperino", 12341234L);

        doThrow(ClienteMenorDeEdadException.class).when(clienteService).crearCliente(clienteDto);

        assertThrows(ClienteMenorDeEdadException.class, () -> clienteController.createCliente(clienteDto));

        verify(clienteValidator, times(1)).validateCliente(clienteDto);
        verify(clienteService, times(1)). crearCliente(clienteDto);
    }

}
