package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaTest {
    @Mock
    CuentaDao cuentaDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    Consulta consulta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        consulta = new Consulta(movimientosDao, cuentaDao);
    }

    @Test
    public void testConsultaSuccess() throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(cuenta);

        Operaciones operacion = consulta.consulta(cuenta.getCVU());

        assertNotNull(operacion);
        assertEquals(cuenta.getCVU(), operacion.getCvu());
        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());
        verify(movimientosDao, times(1)).saveMovimiento("Consulta", 0.0, cuenta.getCVU());
    }

    @Test
    public void testConsultaCuentaNoEncontrada() throws CuentaNoEncontradaException {

        when(cuentaDao.findCuenta(123456L)).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> consulta.consulta(123456L));

        verify(cuentaDao, times(1)).findCuenta(123456L);
    }

    @Test
    public void testConsultaCuentaEstaDeBaja() throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        cuenta.setEstado(false);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(cuenta);

        assertThrows(CuentaEstaDeBajaException.class, () -> consulta.consulta(cuenta.getCVU()));

        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());
    }
}
