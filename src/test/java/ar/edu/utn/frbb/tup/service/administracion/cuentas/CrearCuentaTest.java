package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrearCuentaTest {
    @Mock
    ClienteDao clienteDao;

    @Mock
    CuentaDao cuentaDao;

    @InjectMocks
    CrearCuenta crearCuenta;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crearCuenta = new CrearCuenta(clienteDao, cuentaDao);
    }

    @Test
    public void testCrearCuentaSuccess() throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        when(clienteDao.findCliente(cuentaDto.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuenta(any(Long.class))).thenReturn(null); //Como parametro acepta cualquier tipo de Long ya que la creacion es aleatoria de CVU
        when(cuentaDao.findAllCuentasDelCliente(cuentaDto.getDniTitular())).thenReturn(new HashSet<>());

        Cuenta nuevaCuenta = crearCuenta.crearCuenta(cuentaDto);

        verify(clienteDao, times(1)).findCliente(cuentaDto.getDniTitular());
        verify(cuentaDao, times(1)).findCuenta(nuevaCuenta.getCVU());
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(cuentaDto.getDniTitular());
        verify(cuentaDao, times(1)).saveCuenta(nuevaCuenta);

        assertNotNull(nuevaCuenta);
    }

    @Test
    public void testCrearCuentaClienteNoEncontrado() {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        when(clienteDao.findCliente(cuentaDto.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> crearCuenta.crearCuenta(cuentaDto));

        verify(clienteDao, times(1)).findCliente(cuentaDto.getDniTitular());
    }

    @Test
    public void testCrearCuentaCuentaExistente() {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        when(clienteDao.findCliente(cuentaDto.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuenta(any(Long.class))).thenReturn(new Cuenta());

        assertThrows(CuentaExistenteException.class, () -> crearCuenta.crearCuenta(cuentaDto));

        verify(clienteDao, times(1)).findCliente(cuentaDto.getDniTitular());
        verify(cuentaDao, times(1)).findCuenta(any(Long.class));

    }

    @Test
    public void testCrearCuentaTipoCuentaExistente() {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");
        Cuenta cuenta = new Cuenta(cuentaDto);

        Set<Cuenta> cuentas = new HashSet<>();
        cuentas.add(cuenta);

        when(clienteDao.findCliente(cuentaDto.getDniTitular())).thenReturn(new Cliente());
        when(cuentaDao.findCuenta(any(Long.class))).thenReturn(null);
        when(cuentaDao.findAllCuentasDelCliente(cuentaDto.getDniTitular())).thenReturn(cuentas);

        assertThrows(TipoCuentaExistenteException.class, () -> crearCuenta.crearCuenta(cuentaDto));

        verify(clienteDao, times(1)).findCliente(cuentaDto.getDniTitular());
        verify(cuentaDao, times(1)).findCuenta(any(Long.class));
        verify(cuentaDao, times(1)).findAllCuentasDelCliente(cuentaDto.getDniTitular());
    }

}
