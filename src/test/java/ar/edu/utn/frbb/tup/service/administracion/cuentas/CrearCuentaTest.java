package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.utn.frbb.tup.service.administracion.baseAdministracionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrearCuentaTest extends baseAdministracionTest {

    @Mock
    CuentaDao cuentaDao;

    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    CrearCuenta crearCuenta;

    /*@BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);

    }
    */

    @Test
    public void testCrearCuentaSuccess() throws TipoCuentaExistenteException, ClienteNoEncontradoException, CuentaExistenteException {
        Cuenta cuenta = getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findAllCuentasDelCliente(cuenta.getDniTitular())).thenReturn(new ArrayList<>());
        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(null);

        Cuenta creacion = crearCuenta.crearCuenta(cuenta);

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).saveCuenta(cuenta);
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());

        assertEquals(cuenta, creacion);
        assertNotNull(creacion);
    }

    @Test
    public void testCrearCuentaClienteExistenteException(){
        Cuenta cuenta = getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> crearCuenta.crearCuenta(cuenta));
        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
    }

    @Test
    public void testCrearCuentaCuentaExistenteException(){
        Cuenta cuenta = getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());

        //Preparo el mock para que devuelva una cuenta nueva asi agarro la excepcion buscada
        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(new Cuenta());

        assertThrows(CuentaExistenteException.class, () -> crearCuenta.crearCuenta(cuenta));

        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());
        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
    }

    @Test
    public void testCrearCuentaTipoCuentaExistenteException(){
        Cuenta cuenta = getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaMismoTipo = getCuenta("Pomoro", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(null);
        when(cuentaDao.findAllCuentasDelCliente(cuenta.getDniTitular())).thenReturn(getCuentasList(cuentaMismoTipo));

        assertThrows(TipoCuentaExistenteException.class, () -> crearCuenta.crearCuenta(cuenta));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(cuenta.getDniTitular());
    }
}
