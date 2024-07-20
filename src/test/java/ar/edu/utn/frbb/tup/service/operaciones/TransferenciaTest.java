package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaDistintaMonedaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.TransferenciaFailException;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaTest {
    @Mock
    CuentaDao cuentaDao;

    @Mock
    ClienteDao clienteDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    Transferencia transferencia;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        transferencia = new Transferencia(cuentaDao, clienteDao, movimientosDao);
    }

    @Test
    public void testTransferenciaSuccess() throws CuentaDistintaMonedaException, CuentaNoEncontradaException, CuentaEstaDeBajaException, CuentaSinDineroException, TransferenciaFailException {

        Cliente clienteOrigen = BaseAdministracionTest.getCliente("Juan", 12345678);
        Cliente clienteDestino = BaseAdministracionTest.getCliente("Pedro", 11223344);
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", clienteOrigen.getDni(), TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", clienteDestino.getDni(), TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(cuentaOrigen.getCVU(), cuentaDestino.getCVU(), 100, "P", "D");

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(cuentaDestino);
        when(clienteDao.findCliente(cuentaOrigen.getDniTitular())).thenReturn(clienteOrigen);
        when(clienteDao.findCliente(cuentaDestino.getDniTitular())).thenReturn(clienteDestino);

        Operaciones transferenciaRealizada = transferencia.transferencia(transferDto);

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));
        verify(clienteDao, times(2)).findCliente(any(Long.class));
        verify(cuentaDao, times(2)).deleteCuenta(any(Long.class));
        verify(movimientosDao, times(2)).saveMovimiento(any(String.class), any(Double.class), any(Long.class));
        verify(cuentaDao, times(2)).saveCuenta(any(Cuenta.class));

        assertNotNull(transferenciaRealizada);
        assertEquals(100, transferenciaRealizada.getMonto());
    }

    @Test
    public void testTransferenciaCuentaOrigenNoEncontrada(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D");

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> transferencia.transferencia(transferDto));

    }

    @Test
    public void testTransferenciaCuentaDestinoNoEncontrada(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D");

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(new Cuenta());
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(null);

        assertThrows(CuentaNoEncontradaException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));

    }

    @Test
    public void testTransferenciaCuentaOrigenEstaDeBaja(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D");
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", 12345678, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", 12341234, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        cuentaOrigen.setEstado(false);

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(cuentaDestino);

        assertThrows(CuentaEstaDeBajaException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));
    }

    @Test
    public void testTransferenciaCuentaDestinoestaDeBaja(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D");
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", 12345678, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", 12341234, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        cuentaDestino.setEstado(false);

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(cuentaDestino);

        assertThrows(CuentaEstaDeBajaException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));
    }

    @Test
    public void testTransferenciaCuentaOrigenDistintaMoneda(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D"); //Transferencia en Pesos
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", 12345678, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.DOLARES); //Cuenta Origen en Dolares
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", 12341234, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(cuentaDestino);

        assertThrows(CuentaDistintaMonedaException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));
    }

    @Test
    public void testTransferenciaCuentaDestinoDistintaMoneda(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D"); //Transferencia en Pesos
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", 12345678, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", 12341234, TipoCuenta.CAJA_AHORRO, TipoMoneda.DOLARES); //Cuenta Destino en dolares

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(cuentaDestino);

        assertThrows(CuentaDistintaMonedaException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));
    }

    @Test
    public void testTransferenciaCuentaOrigenSinDinero(){
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(123456, 112233, 100, "P", "D"); //Transferencia en Pesos
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", 12345678, TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", 12341234, TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS); //Cuenta Destino en dolares
        cuentaOrigen.setSaldo(0);

        when(cuentaDao.findCuenta(transferDto.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transferDto.getCuentaDestino())).thenReturn(cuentaDestino);

        assertThrows(CuentaSinDineroException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));

    }

    @Test
    public void testTransferenciaBancoExternoException(){
        Cliente clienteOrigen = BaseAdministracionTest.getCliente("Juan", 12341234L);
        Cliente clienteDestino = BaseAdministracionTest.getCliente("Pedro", 12341233L); //Numero impar para que salga la excepión
        Cuenta cuentaOrigen = BaseAdministracionTest.getCuenta("Cuenta origen", clienteOrigen.getDni(), TipoCuenta.CUENTA_CORRIENTE, TipoMoneda.PESOS);
        Cuenta cuentaDestino = BaseAdministracionTest.getCuenta("Cuenta destino", clienteDestino.getDni(), TipoCuenta.CAJA_AHORRO, TipoMoneda.PESOS);
        TransferDto transferDto = BaseOperacionesTest.getTransferDto(cuentaOrigen.getCVU(), cuentaDestino.getCVU(), 100, "P", "D");
        Transfer transfer = new Transfer(transferDto);

        clienteDestino.setBanco("Naranja");

        when(cuentaDao.findCuenta(transfer.getCuentaOrigen())).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(transfer.getCuentaDestino())).thenReturn(cuentaDestino);
        when(clienteDao.findCliente(clienteOrigen.getDni())).thenReturn(clienteOrigen);
        when(clienteDao.findCliente(clienteDestino.getDni())).thenReturn(clienteDestino);

        assertThrows(TransferenciaFailException.class, () -> transferencia.transferencia(transferDto));

        verify(cuentaDao, times(2)).findCuenta(any(Long.class));
        verify(clienteDao, times(2)).findCliente(any(Long.class));
    }
}
