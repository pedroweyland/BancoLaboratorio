package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;
import org.springframework.stereotype.Component;

@Component
public class TransferValidator {

    public static void validate(TransferDto transferDto ) {

        validateDatosCompletos(transferDto);
        validateCVUentregados(transferDto.getCuentaOrigen(), transferDto.getCuentaDestino());

    }

    private static void validateCVUentregados(long cuentaOrigen, long cuentaDestino) {
        if (cuentaOrigen < 100000 || cuentaOrigen > 999999) throw new IllegalArgumentException("Error: El CVU Origen tiene que ser de 6 digitos");
        if (cuentaDestino < 100000 || cuentaDestino > 999999) throw new IllegalArgumentException("Error: El CVU Destino tiene que ser de 6 digitos");

        if (cuentaOrigen == cuentaDestino) throw new IllegalArgumentException("Error: No puede ser la misma cuenta");

    }

    private static void validateDatosCompletos(TransferDto transferDto){
        //Cvu Origen
        if (transferDto.getCuentaOrigen() == 0) throw new IllegalArgumentException("Error: Ingrese un CVU de Origen");

        //Cvu Destino
        if (transferDto.getCuentaDestino() == 0) throw new IllegalArgumentException("Error: Ingrese un CVU de Destino");

        //Monto
        if (transferDto.getMonto() == 0) throw new IllegalArgumentException("Error: Ingrese un monto a transferir");

        //Moneda
        if (transferDto.getMoneda() == null) throw new IllegalArgumentException("Error: Ingrese el tipo de moneda");

        //Tipo transaccion
        if (transferDto.getTipoTransaccion() == null) throw new IllegalArgumentException("Error: Ingrese el tipo de transaccion");
    }

}
