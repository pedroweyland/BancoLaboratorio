package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.clientes.MostrarCliente;
import ar.utn.frbb.tup.service.administracion.baseAdministracionTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MostrarClienteTest extends baseAdministracionTest {
    @Mock
    ClienteDao clienteDao;

    @Mock
    CuentaDao cuentaDao;

    @InjectMocks
    MostrarCliente mostrarCliente;

    @Test
    public void testMostrarClienteSuccess() throws ClienteNoEncontradoException {
        Cliente pepo = getCliente("pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(pepo);

        Cliente clienteAMostrar = mostrarCliente.mostrarCliente(pepo.getDni());

        assertNotNull(clienteAMostrar);
        assertEquals(pepo, clienteAMostrar);
    }

    @Test
    public void testMostarClienteNoEncontrado(){
        Cliente pepo = getCliente("pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> mostrarCliente.mostrarCliente(pepo.getDni()));
    }


}
