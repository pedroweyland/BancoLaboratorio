package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
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
public class RetiroTest {
    @Mock
    CuentaDao cuentaDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    Retiro retiro;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        retiro = new Retiro(cuentaDao, movimientosDao);
    }

    @Test
    public void testRetiroSuccess() throws CuentaNoEncontradaException, CuentaSinDineroException, CuentaEstaDeBajaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        cuenta.setSaldo(1000);
        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(cuenta);

        Operaciones operacion = retiro.retiro(cuenta.getCVU(), 1000);

        assertNotNull(operacion);
        assertEquals(1000, operacion.getMonto());
        assertEquals(0, cuenta.getSaldo());

        verify(cuentaDao).deleteCuenta(cuenta.getCVU());
        verify(movimientosDao).saveMovimiento("Retiro", 1000, cuenta.getCVU());
        verify(cuentaDao).saveCuenta(cuenta);
    }

    @Test
    public void testRetiroCuentaNoEncontrada(){

        when(cuentaDao.findAllCuentas()).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> retiro.retiro(123456, 1000));
    }
}
