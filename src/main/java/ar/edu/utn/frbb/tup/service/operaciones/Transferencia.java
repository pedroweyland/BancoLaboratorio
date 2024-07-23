package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaDistintaMonedaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.TransferenciaFailException;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;
import org.springframework.stereotype.Service;

@Service
public class Transferencia {
    private final CuentaDao cuentaDao;
    private final ClienteDao clienteDao;
    private final MovimientosDao movimientosDao;

    private final String tipoOperacion = "Transferencia a la cuenta ";
    private final String tipoOperacionDestino = "Deposito recibido de la cuenta ";

    public Transferencia(CuentaDao cuentaDao, ClienteDao clienteDao, MovimientosDao movimientosDao) {
        this.cuentaDao = cuentaDao;
        this.clienteDao = clienteDao;
        this.movimientosDao = movimientosDao;
    }

    public Operaciones transferencia(TransferDto transferDto) throws CuentaEstaDeBajaException, CuentaSinDineroException, CuentaNoEncontradaException, CuentaDistintaMonedaException, TransferenciaFailException {
        Transfer datosTransfer = new Transfer(transferDto);

        //Calculo el monto total a transferir, si es PESOS un cargo de 2% si es DOLARES un cargo de 0.5%
        double montoTotal = calcularMontoTransferencia(datosTransfer.getMonto(), datosTransfer.getMoneda());

        //Busco las cuentas a transferir
        Cuenta cuentaOrigen = cuentaDao.findCuenta(datosTransfer.getCuentaOrigen());
        Cuenta cuentaDestino = cuentaDao.findCuenta(datosTransfer.getCuentaDestino());

        //Valido si las cuentas existen, valido si estan de alta/baja, valido si son de la misma moneda
        validateTransferencia(cuentaOrigen, cuentaDestino, datosTransfer, montoTotal);

        //Busco el cliente para identificar si son del mismo banco o no
        Cliente clienteOrigen = clienteDao.findCliente(cuentaOrigen.getDniTitular());
        Cliente clienteDestino = clienteDao.findCliente(cuentaDestino.getDniTitular());

        Operaciones transferenciaRealizada;
        //Si son del mismo banco se realiza la transferencia si no la transferencia seria de un banco externo (posiblidades de excepcion)
        if (clienteOrigen.getBanco().equalsIgnoreCase(clienteDestino.getBanco())) {
            transferenciaRealizada = realizarTransferencia(cuentaOrigen, cuentaDestino, datosTransfer.getTipoTransaccion(), montoTotal);
        } else {
            transferenciaRealizada = transferenciaBancoExterno(cuentaOrigen, cuentaDestino, datosTransfer.getTipoTransaccion(),montoTotal);
        }

        return transferenciaRealizada;
    }

    //Funcion que valida si la transferencia se puede ejecutar correctamente
    private void validateTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, Transfer datosTransfer, double montoTotal) throws CuentaEstaDeBajaException, CuentaDistintaMonedaException, CuentaNoEncontradaException, CuentaSinDineroException {

        //Excepciones de Cuenta No encontrada - Si las cuentas son nulas lanza excepcion
        if (cuentaOrigen == null ) throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + datosTransfer.getCuentaOrigen());
        if (cuentaDestino == null) throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + datosTransfer.getCuentaDestino());

        //Excepciones de Cuenta dada de baja - Si las cuentas estan dadas de baja lanza excepcion
        if (!cuentaOrigen.getEstado()) throw new CuentaEstaDeBajaException("La cuenta origen esta dada de baja");
        if (!cuentaDestino.getEstado()) throw new CuentaEstaDeBajaException("La cuenta destino esta dada de baja");

        //Excepcion cuando son diferentes MONEDAS - Si la cuenta tiene diferente moneda lanza excepcion
        if (cuentaOrigen.getTipoMoneda() != datosTransfer.getMoneda()) throw new CuentaDistintaMonedaException("La cuenta de Origen con el CVU " + datosTransfer.getCuentaOrigen() + " es de diferente tipo de moneda" );
        if (cuentaDestino.getTipoMoneda() != datosTransfer.getMoneda()) throw new CuentaDistintaMonedaException("La cuenta de Destino con el CVU " + datosTransfer.getCuentaDestino() + " es de diferente tipo de moneda" );

        //Si la cuenta no tiene dinero lanza excepcion
        if (montoTotal > cuentaOrigen.getSaldo()) throw new CuentaSinDineroException("No hay suficiente dinero en la cuenta " + cuentaOrigen.getNombre() + ", su saldo es de $" + cuentaOrigen.getSaldo());
    }

    //Funcion para calcular el monto final de la transferencia
    private double calcularMontoTransferencia(double monto, TipoMoneda moneda){
        double cargo = 0;

        if (moneda == TipoMoneda.PESOS && monto >= 1000000) {
            cargo = 0.02;
        } else if (moneda == TipoMoneda.DOLARES && monto >= 5000) {
            cargo = 0.005;
        }

        return monto + (monto * cargo);
    }
    //Funcion para realizar la transferencia - Cambiar los montos de las cuentas y reescribirlo en la base de datos
    private Operaciones realizarTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, TipoTransaccion tipoTransaccion, double monto){

        //Borro la cuentaOrigen ya que va ser modificada
        cuentaDao.deleteCuenta(cuentaOrigen.getCVU());
        cuentaDao.deleteCuenta(cuentaDestino.getCVU());

        //Resto el monto a la cuenta origen y sumo a la que se envia
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

        //Tomo registro de la transferencia en la cuentaOrginen y destino
        movimientosDao.saveMovimiento(tipoOperacion + cuentaDestino.getNombre() + " - " + tipoTransaccion, monto, cuentaOrigen.getCVU());
        movimientosDao.saveMovimiento(tipoOperacionDestino + cuentaOrigen.getNombre() + " - " + tipoTransaccion, monto, cuentaDestino.getCVU());

        //Guardo la cuentaOrigen y cuentaDestino modificada
        cuentaDao.saveCuenta(cuentaOrigen);
        cuentaDao.saveCuenta(cuentaDestino);

        return new Operaciones().setCvu(cuentaOrigen.getCVU()).setSaldoActual(cuentaOrigen.getSaldo()).setMonto(monto).setTipoOperacion(tipoOperacion + cuentaDestino.getNombre() + " - " + tipoTransaccion);
    }

    //Funcion para simular una transferencia de un banco externo
    private Operaciones transferenciaBancoExterno(Cuenta cuentaOrigen, Cuenta cuentaDestino, TipoTransaccion tipoTransaccion,double monto) throws TransferenciaFailException {
        //Simulacion de una transferencia de banco externo, si el dni de la cuenta destino es par se ejecuta, si no lanza excepcion
        if (cuentaDestino.getDniTitular() % 2 == 0){
            return realizarTransferencia(cuentaOrigen, cuentaDestino, tipoTransaccion, monto);
        } else{
            throw new TransferenciaFailException("El banco externo no puede realizar esta transferencia");
        }
    }
}
