package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import org.springframework.stereotype.Component;

@Component
public class CuentaValidator {

    public void validate(CuentaDto cuentaDto ){

        //Nombre de la cuenta
        if (cuentaDto.getNombre() == null || cuentaDto.getNombre().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un nombre");
        //Tipo de cuenta
        if (cuentaDto.getTipoCuenta() == null || cuentaDto.getTipoCuenta().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un tipo de cuenta");
        //Tipo de moneda
        if (cuentaDto.getTipoMoneda() == null || cuentaDto.getTipoMoneda().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un tipo de moneda");
        //DNI Titular
        if (cuentaDto.getDniTitular() == 0) throw new IllegalArgumentException("Error: Ingrese un dni");
        if (cuentaDto.getDniTitular() < 10000000 || cuentaDto.getDniTitular() > 99999999) throw new IllegalArgumentException("Error: El dni debe tener 8 digitos");

    }
}
