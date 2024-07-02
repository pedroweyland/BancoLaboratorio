package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrearCuentaTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private CrearCuenta crearCuenta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crearCuenta = new CrearCuenta(clienteDao, cuentaDao);
    }

    @Test
    public void testCrearCuentaSuccess() throws TipoCuentaExistenteException, ClienteNoEncontradoException, CuentaExistenteException {
        Cliente pepo = BaseAdministracionTest.getCliente("Pepo", 11223344);
        Cuenta cuenta = BaseAdministracionTest.getCuenta("Peperino", 11223344, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(pepo);
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
        Cuenta cuenta = BaseAdministracionTest.getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> crearCuenta.crearCuenta(cuenta));
        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
    }

    @Test
    public void testCrearCuentaCuentaExistenteException(){
        Cuenta cuenta = BaseAdministracionTest.getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());

        //Preparo el mock para que devuelva una cuenta nueva asi agarro la excepcion buscada
        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(new Cuenta());

        assertThrows(CuentaExistenteException.class, () -> crearCuenta.crearCuenta(cuenta));

        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());
        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
    }

    @Test
    public void testCrearCuentaTipoCuentaExistenteException(){
        Cuenta cuenta = BaseAdministracionTest.getCuenta("Peperino", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaMismoTipo = BaseAdministracionTest.getCuenta("Pomoro", 12341234, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);

        when(clienteDao.findCliente(cuenta.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuenta(cuenta.getCVU())).thenReturn(null);
        when(cuentaDao.findAllCuentasDelCliente(cuenta.getDniTitular())).thenReturn(BaseAdministracionTest.getCuentasList(cuentaMismoTipo));

        assertThrows(TipoCuentaExistenteException.class, () -> crearCuenta.crearCuenta(cuenta));

        verify(clienteDao, times(1)).findCliente(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).findCuenta(cuenta.getCVU());
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(cuenta.getDniTitular());
    }


}
