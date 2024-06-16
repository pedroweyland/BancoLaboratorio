package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClienteFechaDeAltaInvalidaException;
import ar.edu.utn.frbb.tup.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
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
public class CrearClienteTest extends baseAdministracionTest {

    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    CrearCliente crearCliente;

    /*@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }*/



    @Test
    public void testCrearClienteSuccess() throws ClienteExistenteException, ClienteMenorDeEdadException, ClienteFechaDeAltaInvalidaException {
        Cliente pepo = getCliente("Pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(null);

        Cliente creacion = crearCliente.crearCliente(pepo);
        //Verifico que clienteDao se haya ejecutado una ves
        verify(clienteDao, times(1)).saveCliente(pepo);

        assertEquals(pepo, creacion);
        assertNotNull(creacion);

    }

    @Test
    public void testCrearClienteExistente(){
        Cliente pepo = getCliente("Pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(new Cliente());

        assertThrows(ClienteExistenteException.class, () -> crearCliente.crearCliente(pepo));
    }

}
