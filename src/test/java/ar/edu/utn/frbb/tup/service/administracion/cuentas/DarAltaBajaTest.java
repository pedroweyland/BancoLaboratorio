package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
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
public class DarAltaBajaTest {
    @Mock
    ClienteDao clienteDao;

    @Mock
    CuentaDao cuentaDao;

    @InjectMocks
    DarAltaBaja darAltaBaja;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        darAltaBaja = new DarAltaBaja(clienteDao, cuentaDao);
    }

    @Test
    public void testDarAltaBajaSuccess() throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12345678L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular())).thenReturn(cuenta);

        Cuenta cuentaModificada = darAltaBaja.gestionarEstado(cuenta.getDniTitular(), cuenta.getCVU(), true);
        assertTrue(cuentaModificada.getEstado());

        Cuenta cuentaModificada2 = darAltaBaja.gestionarEstado(cuenta.getDniTitular(), cuenta.getCVU(), false);
        assertFalse(cuentaModificada2.getEstado());

        verify(clienteDao, times(2)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(2)).findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular());
        verify(cuentaDao, times(2)).saveCuenta(cuenta);
        verify(cuentaDao, times(2)).deleteCuenta(cuenta.getCVU());
    }

    @Test
    public void testDarAltaBajaClienteNoEncontrado() {
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12345678L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> darAltaBaja.gestionarEstado(cuenta.getDniTitular(), cuenta.getCVU(), true));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
    }

    @Test
    public void testDarAltaBajaCuentaNoEncontrada() {
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12345678L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular())).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> darAltaBaja.gestionarEstado(cuenta.getDniTitular(), cuenta.getCVU(), true));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular());
    }
}
