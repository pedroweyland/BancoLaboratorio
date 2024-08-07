package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;

public class BaseOperacionesTest {

    public static Cuenta getCuenta(String nombre, long dniTitular, TipoCuenta tipoCuenta, TipoMoneda tipoMoneda){
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(nombre);
        cuenta.setDniTitular(dniTitular);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setEstado(true);
        cuenta.setCVU(123456);
        cuenta.setSaldo(10000);
        cuenta.setTipoMoneda(tipoMoneda);
        return cuenta;
    }

    public static TransferDto getTransferDto(long CVUOrigen, long CVUDestino, double monto, String moneda, String tipoTransaccion){
        TransferDto transferDto = new TransferDto();
        transferDto.setCuentaOrigen(CVUOrigen);
        transferDto.setCuentaDestino(CVUDestino);
        transferDto.setMonto(monto);
        transferDto.setMoneda(moneda);
        transferDto.setTipoTransaccion(tipoTransaccion);
        return transferDto;
    }
}
