package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MostrarClienteTest {
    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    MostrarCliente mostrarCliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mostrarCliente = new MostrarCliente(clienteDao);
    }
    @Test
    public void testMostrarClienteSuccess() throws ClienteNoEncontradoException {
        Cliente pepo = BaseAdministracionTest.getCliente("pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(pepo);

        Cliente clienteAMostrar = mostrarCliente.mostrarCliente(pepo.getDni());

        assertNotNull(clienteAMostrar);
        assertEquals(pepo, clienteAMostrar);
    }

    @Test
    public void testMostarClienteNoEncontrado(){
        Cliente pepo = BaseAdministracionTest.getCliente("pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> mostrarCliente.mostrarCliente(pepo.getDni()));
    }

}
