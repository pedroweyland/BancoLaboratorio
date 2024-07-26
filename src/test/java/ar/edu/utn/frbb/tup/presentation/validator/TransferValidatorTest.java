package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferValidatorTest {

    TransferValidator transferValidator;

    @BeforeEach
    public void setUp() {
        transferValidator = new TransferValidator();
    }

    @Test
    public void testTransferValidatorSuccess(){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(123123);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        //Verifico que no haga ningun Throw
        assertDoesNotThrow(() -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferSinCvuOrigenException(){
        TransferDto transferDto = new TransferDto();
        //transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(123123);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferSinCvuDestinoException(){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        //transferDto.setCuentaDestino(123123);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferSinMonedaException(){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(123123);
        //transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferSinMontoException() {
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(123123);
        transferDto.setMoneda("P");
        //transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");
    }

    @Test
    public void testTransferSinTipoTransaccionException() {
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(123123);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        //transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferMismoCvuException(){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(123456);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferCvuOrigenNegativo(){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(-123456);
        transferDto.setCuentaDestino(123456);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

    @Test
    public void testTransferCvuDestinoNegativo(){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(123456);
        transferDto.setCuentaDestino(-123456);
        transferDto.setMoneda("P");
        transferDto.setMonto(1000);
        transferDto.setTipoTransaccion("D");

        assertThrows(IllegalArgumentException.class, () -> transferValidator.validate(transferDto));
    }

}
