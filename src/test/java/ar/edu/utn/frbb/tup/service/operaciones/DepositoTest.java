package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Operaciones;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DepositoTest {
    @Mock
    CuentaDao cuentaDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    Deposito desposito;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        desposito = new Deposito(cuentaDao, movimientosDao);
    }

    @Test
    public void testDepositoSuccess() throws CuentaNoEncontradaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        cuenta.setSaldo(0);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(cuenta);

        Operaciones deposito = desposito.deposito(cuenta.getCVU(), 1000);

        assertNotNull(deposito);
        assertEquals(1000, deposito.getMonto());
        assertEquals(1000, cuenta.getSaldo());

        verify(cuentaDao).deleteCuenta(cuenta.getCVU());
        verify(movimientosDao).saveMovimiento("Deposito", 1000, cuenta.getCVU());
        verify(cuentaDao).saveCuenta(cuenta);

    }

    @Test
    public void testDepositoCuentaNoEncontrada() throws CuentaNoEncontradaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> desposito.deposito(cuenta.getCVU(), 1000));
    }
}
