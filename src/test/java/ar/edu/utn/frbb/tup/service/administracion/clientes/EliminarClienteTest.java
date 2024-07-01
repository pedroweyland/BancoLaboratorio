package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EliminarClienteTest {

    @Mock
    ClienteDao clienteDao;

    @Mock
    CuentaDao cuentaDao;

    @InjectMocks
    EliminarCliente eliminarCliente;

    /*@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }*/

    @Test
    public void testEliminarClienteSuccess() throws ClienteNoEncontradoException {
        Cliente pepo = BaseAdministracionTest.getCliente("Pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(pepo);
        when(cuentaDao.getRelacionesDni(pepo.getDni())).thenReturn(new ArrayList<>());

        Cliente eliminado = eliminarCliente.eliminarCliente(pepo.getDni());

        assertEquals(pepo, eliminado);
        verify(clienteDao, times(1)).deleteCliente(pepo.getDni());
        verify(cuentaDao, times(1)).getRelacionesDni(pepo.getDni());
    }

    @Test
    public void testEliminarClienteNoEncontrado(){
        Cliente pepo = BaseAdministracionTest.getCliente("Pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> eliminarCliente.eliminarCliente(pepo.getDni()));
    }



}
