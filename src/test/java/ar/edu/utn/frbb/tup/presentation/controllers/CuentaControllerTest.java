package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentasVaciasException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import ar.edu.utn.frbb.tup.presentation.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaControllerTest {
    @Mock
    CuentaService cuentaService;

    @Mock
    CuentaValidator cuentaValidator;

    @InjectMocks
    CuentaController cuentaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cuentaController = new CuentaController(cuentaValidator, cuentaService);
    }

    @Test
    public void testCrearCuentaSuccess() throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("Peperino", 12341234L, "C", "P");

        when(cuentaService.crearCuenta(cuentaDto)).thenReturn(new Cuenta());

        ResponseEntity<Cuenta> response = cuentaController.createCuenta(cuentaDto);

        verify(cuentaService, times(1)).crearCuenta(cuentaDto);
        verify(cuentaValidator, times(1)).validate(cuentaDto);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void testCrearCuentaExistente() throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("Peperino", 12341234L, "C", "P");

        doThrow(CuentaExistenteException.class).when(cuentaService).crearCuenta(cuentaDto);

        assertThrows(CuentaExistenteException.class, () -> cuentaController.createCuenta(cuentaDto));

        verify(cuentaService, times(1)).crearCuenta(cuentaDto);
        verify(cuentaValidator, times(1)).validate(cuentaDto);
    }

    @Test
    public void testCrearCuentaClienteNoEncontrado() throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        doThrow(ClienteNoEncontradoException.class).when(cuentaService).crearCuenta(cuentaDto);

        assertThrows(ClienteNoEncontradoException.class, () -> cuentaController.createCuenta(cuentaDto));

        verify(cuentaService, times(1)).crearCuenta(cuentaDto);
        verify(cuentaValidator, times(1)).validate(cuentaDto);
    }

    @Test
    public void testCrearCuentaTipoCuentaExistente() throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        doThrow(TipoCuentaExistenteException.class).when(cuentaService).crearCuenta(cuentaDto);

        assertThrows(TipoCuentaExistenteException.class, () -> cuentaController.createCuenta(cuentaDto));

        verify(cuentaService, times(1)).crearCuenta(cuentaDto);
        verify(cuentaValidator, times(1)).validate(cuentaDto);
    }

    @Test
    public void testDeleteCuentaSuccess() throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        when(cuentaService.eliminarCuenta(12341234L, 123123L)).thenReturn(new Cuenta());

        ResponseEntity<Cuenta> response = cuentaController.deleteCuenta(12341234L, 123123L);

        verify(cuentaService, times(1)).eliminarCuenta(12341234L, 123123L);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testDeleteCuentaNoEncontrada() throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        doThrow(CuentaNoEncontradaException.class).when(cuentaService).eliminarCuenta(12341234L, 123123L);

        assertThrows(CuentaNoEncontradaException.class, () -> cuentaController.deleteCuenta(12341234L, 123123L));

        verify(cuentaService, times(1)).eliminarCuenta(12341234L, 123123L);
    }

    @Test
    public void testDeleteCuentaClienteNoEncontrado() throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        doThrow(ClienteNoEncontradoException.class).when(cuentaService).eliminarCuenta(12341234L, 123123L);

        assertThrows(ClienteNoEncontradoException.class, () -> cuentaController.deleteCuenta(12341234L, 123123L));

        verify(cuentaService, times(1)).eliminarCuenta(12341234L, 123123L);
    }

    @Test
    public void testDeleteCuentasVacias() throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        CuentaDto cuentaDto = BaseAdministracionTest.getCuentaDto("pepoCuenta", 12345678L, "C", "P");

        doThrow(CuentasVaciasException.class).when(cuentaService).eliminarCuenta(12341234L, 123123L);

        assertThrows(CuentasVaciasException.class, () -> cuentaController.deleteCuenta(12341234L, 123123L));

        verify(cuentaService, times(1)).eliminarCuenta(12341234L, 123123L);
    }

    @Test
    public void testMostrarCuenta() throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        Set<Cuenta> cuentas = new HashSet<>();

        when(cuentaService.mostrarCuenta(12341234L)).thenReturn(cuentas);

        ResponseEntity<Set<Cuenta>> response = cuentaController.getAllCuentas(12341234L);

        verify(cuentaService, times(1)).mostrarCuenta(12341234L);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testMostrarCuentaNoEncontrada() throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        Set<Cuenta> cuentas = new HashSet<>();

        doThrow(CuentaNoEncontradaException.class).when(cuentaService).mostrarCuenta(12341234L);

        assertThrows(CuentaNoEncontradaException.class, () -> cuentaController.getAllCuentas(12341234L));

        verify(cuentaService, times(1)).mostrarCuenta(12341234L);
    }

    @Test
    public void testMostrarCuentaClienteNoEncontrado() throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        Set<Cuenta> cuentas = new HashSet<>();

        doThrow(ClienteNoEncontradoException.class).when(cuentaService).mostrarCuenta(12341234L);

        assertThrows(ClienteNoEncontradoException.class, () -> cuentaController.getAllCuentas(12341234L));

        verify(cuentaService, times(1)).mostrarCuenta(12341234L);
    }


}
