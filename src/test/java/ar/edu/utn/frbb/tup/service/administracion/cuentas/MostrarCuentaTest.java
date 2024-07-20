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
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MostrarCuentaTest {
    @Mock
    ClienteDao clienteDao;

    @Mock
    CuentaDao cuentaDao;

    @InjectMocks
    MostrarCuenta mostrarCuenta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mostrarCuenta = new MostrarCuenta(clienteDao, cuentaDao);
    }

    @Test
    public void testMostrarCuentaSuccess() throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12345678L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(cuenta);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findAllCuentasDelCliente(cuenta.getDniTitular())).thenReturn(cuentas);

        List<Cuenta> cuentasAsociadas = mostrarCuenta.mostrarCuenta(cuenta.getDniTitular());
        assertNotNull(cuentasAsociadas);

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(cuenta.getDniTitular());
    }

    @Test
    public void testMostrarCuentaClienteNoEncontrado(){
        Cuenta cuenta = BaseAdministracionTest.getCuenta("pepoCuenta", 12345678L, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> mostrarCuenta.mostrarCuenta(cuenta.getDniTitular()));
    }

    @Test
    public void testMostrarCuentaCuentasVacias(){

        when(clienteDao.findCliente(12341234L)).thenReturn(new Cliente());
        when(cuentaDao.findAllCuentasDelCliente(any(Long.class))).thenReturn(new ArrayList<>());

        assertThrows(CuentaNoEncontradaException.class, () -> mostrarCuenta.mostrarCuenta(12341234L));

        verify(clienteDao, times(1)).findCliente(12341234L);
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(any(Long.class));
    }

}
