package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EliminarClienteTest {

    @Mock
    ClienteDao clienteDao;

    @Mock
    CuentaDao cuentaDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    EliminarCliente eliminarCliente;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eliminarCliente = new EliminarCliente(clienteDao, cuentaDao, movimientosDao);
    }

    @Test
    public void testEliminarClienteSuccess() throws ClienteNoEncontradoException {
        Cliente pepo = BaseAdministracionTest.getCliente("Pepo", 12345678L);
        Cuenta pepoCuenta = BaseAdministracionTest.getCuenta("pepoCuenta", pepo.getDni(), TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        List<Long> pepoCvu = new ArrayList<>();

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(pepo);
        when(cuentaDao.getRelacionesDni(pepo.getDni())).thenReturn(pepoCvu);

        pepoCvu.add(pepoCuenta.getCVU());

        Cliente eliminado = eliminarCliente.eliminarCliente(pepo.getDni());

        assertEquals(pepo, eliminado);

        verify(clienteDao, times(1)).deleteCliente(pepo.getDni());
        verify(cuentaDao, times(1)).getRelacionesDni(pepo.getDni());
        verify(cuentaDao, times(1)).deleteCuenta(pepoCuenta.getCVU());
        verify(movimientosDao, times(1)).deleteMovimiento(pepoCuenta.getCVU());
    }

    @Test
    public void testEliminarClienteNoEncontrado(){
        Cliente pepo = BaseAdministracionTest.getCliente("Pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> eliminarCliente.eliminarCliente(pepo.getDni()));
    }

}
