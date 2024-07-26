package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaValidatorTest {

    CuentaValidator cuentaValidator;

    @BeforeEach
    public void setUp() {
        cuentaValidator = new CuentaValidator();
    }

    @Test
    public void testCuentaValidatorSuccess(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre("Peperino");
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setDniTitular(12341234);

        //Verifico que no haga ningun Throw
        assertDoesNotThrow(() -> cuentaValidator.validate(cuentaDto));
    }

    @Test
    public void testCuentaSinNombreException(){
        CuentaDto cuentaDto = new CuentaDto();
        //cuentaDto.setNombre("Peperino");
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setDniTitular(12341234);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto));
    }

    @Test
    public void testCuentaSinTipoCuentaException(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre("Peperino");
        //cuentaDto.setTipoCuenta("C");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setDniTitular(12341234);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto));
    }

    @Test
    public void testCuentaSinTipoMonedaException(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre("Peperino");
        cuentaDto.setTipoCuenta("C");
        //cuentaDto.setTipoMoneda("P");
        cuentaDto.setDniTitular(12341234);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto));
    }

    @Test
    public void testCuentaSinDniTitularException() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre("Peperino");
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setTipoMoneda("P");
        //cuentaDto.setDniTitular(12341234);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto));
    }

    @Test
    public void testCuentaDniNegativoException(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre("Peperino");
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setTipoMoneda("P");
        cuentaDto.setDniTitular(-12341234);

        assertThrows(IllegalArgumentException.class, () -> cuentaValidator.validate(cuentaDto));
    }
}
