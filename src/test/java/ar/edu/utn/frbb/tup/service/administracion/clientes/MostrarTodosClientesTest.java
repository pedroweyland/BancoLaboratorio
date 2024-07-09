package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClientesVaciosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MostrarTodosClientesTest {
    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    MostrarTodosClientes mostrarTodosClientes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mostrarTodosClientes = new MostrarTodosClientes(clienteDao);
    }

    @Test
    public void testMostrarTodosClientesSuccess() throws ClientesVaciosException {
        List<Cliente> clientes = BaseAdministracionTest.getListaDeClientes();

        when(clienteDao.findAllClientes()).thenReturn(clientes);

        List<Cliente> clientesAMostrar = mostrarTodosClientes.mostrarTodosClientes();

        assertNotNull(clientesAMostrar);
        assertEquals(clientes, clientesAMostrar);
    }

    @Test
    public void testMostrarTodosClientesVacio(){
        List<Cliente> clientes = new ArrayList<>();

        when(clienteDao.findAllClientes()).thenReturn(clientes);

        assertThrows(ClientesVaciosException.class, () -> mostrarTodosClientes.mostrarTodosClientes());
    }
}
