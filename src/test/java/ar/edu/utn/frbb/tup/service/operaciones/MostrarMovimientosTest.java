package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MostrarMovimientosTest {

    @Mock
    MovimientosDao movimientosDao;

    @Mock
    CuentaDao cuentaDao;

    @InjectMocks
    MostrarMovimientos mostrarMovimientos;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mostrarMovimientos = new MostrarMovimientos(movimientosDao, cuentaDao);
    }

    @Test
    public void testMostrarMovimientosSuccess() throws MovimientosVaciosException, CuentaNoEncontradaException, CuentaEstaDeBajaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        List<Movimiento> movimientos = new ArrayList<>();
        Movimiento movimiento = new Movimiento();

        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setCVU(cuenta.getCVU());
        movimiento.setHoraOperacion(LocalTime.now());
        movimiento.setMonto(0);
        movimiento.setTipoOperacion("Deposito");

        movimientos.add(movimiento);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(cuenta);
        when(movimientosDao.findMovimientos(cuenta.getCVU())).thenReturn(movimientos);

        List<Movimiento> resultado = mostrarMovimientos.mostrarMovimientos(cuenta.getCVU());

        assertEquals(movimientos, resultado);
    }

    @Test
    public void testMostrarMovimientosCuentaNoEncontrada() throws CuentaNoEncontradaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> mostrarMovimientos.mostrarMovimientos(cuenta.getCVU()));
    }

    @Test
    public void testMostrarMovimientosMovimientosVacios() throws CuentaNoEncontradaException {
        Cuenta cuenta = BaseOperacionesTest.getCuenta("Cuenta de prueba", 123456, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(cuenta);
        when(movimientosDao.findMovimientos(cuenta.getCVU())).thenReturn(new ArrayList<>());

        assertThrows(MovimientosVaciosException.class, () -> mostrarMovimientos.mostrarMovimientos(cuenta.getCVU()));
    }
}
