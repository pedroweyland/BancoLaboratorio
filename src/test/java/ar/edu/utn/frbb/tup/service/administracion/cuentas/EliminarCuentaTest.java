package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentasVaciasException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EliminarCuentaTest {

    @Mock
    CuentaDao cuentaDao;

    @Mock
    ClienteDao clienteDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    EliminarCuenta eliminarCuenta;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        eliminarCuenta = new EliminarCuenta(clienteDao, cuentaDao, movimientosDao);
    }

    @Test
    public void testEliminarCuentaSuccess() throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12341234L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        List<Long> cuentasCvu = new ArrayList<>();
        cuentasCvu.add(cuenta.getCVU());

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.getRelacionesDni(cuenta.getDniTitular())).thenReturn(cuentasCvu);
        when(cuentaDao.findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular())).thenReturn(cuenta);

        Cuenta cuentaEliminada = eliminarCuenta.eliminarCuenta(cuenta.getDniTitular(), cuenta.getCVU());

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).getRelacionesDni(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular());
        verify(cuentaDao, times(1)).deleteCuenta(cuenta.getCVU());
        verify(movimientosDao, times(1)).deleteMovimiento(cuenta.getCVU());

        assertEquals(cuenta, cuentaEliminada);
    }

    @Test
    public void testEliminarCuentaClienteNoEncontrado(){
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12341234L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> eliminarCuenta.eliminarCuenta(cuenta.getDniTitular(), cuenta.getCVU()));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
    }

    @Test
    public void testEliminarCuentaCuentasVacias(){
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12341234L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.getRelacionesDni(cuenta.getDniTitular())).thenReturn(new ArrayList<>());

        assertThrows(CuentasVaciasException.class, () -> eliminarCuenta.eliminarCuenta(cuenta.getDniTitular(), cuenta.getCVU()));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).getRelacionesDni(cuenta.getDniTitular());
    }

    @Test
    public void testEliminarCuentaCuentaNoEncontrada() {
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12341234L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        List<Long> cuentasCvu = new ArrayList<>();
        cuentasCvu.add(cuenta.getCVU());

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.getRelacionesDni(cuenta.getDniTitular())).thenReturn(cuentasCvu);
        when(cuentaDao.findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular())).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> eliminarCuenta.eliminarCuenta(cuenta.getDniTitular(), cuenta.getCVU()));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).getRelacionesDni(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findCuentaDelCliente(cuenta.getCVU(), cuenta.getDniTitular());

    }
}
