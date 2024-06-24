package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.exception.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.MismaCuentaException;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.BasePresentation.ingresarDinero;

@Service
public class Transferencia extends baseOperaciones {
    private final CuentaDao cuentaDao;
    private final MovimientosDao movimientosDao;

    private final String tipoOperacion = "Transferencia a la cuenta ";
    private final String tipoOperacionDestino = "Deposito recibido de la cuenta ";

    public Transferencia(CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    public double transferencia(long cuentaO, long cuentaD, double monto) throws MismaCuentaException, CuentaEstaDeBajaException, CuentaSinDineroException, CuentaNoEncontradaException {

        if (cuentaO == cuentaD) { ////Lanzo excepcion cuando la cuenta destino es igual a la origen
            throw new MismaCuentaException("No se puede transferir a la misma cuenta");
        }

        Cuenta cuentaOrigen = cuentaDao.findCuenta(cuentaO);
        Cuenta cuentaDestino = cuentaDao.findCuenta(cuentaD);

        if (cuentaOrigen == null ) {
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cuentaO);
        }

        if (cuentaDestino == null) {
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cuentaD);
        }

        if (!cuentaDestino.getEstado()) { //Lanzo excepcion cuando la cuenta destino esta dada de baja
            throw new CuentaEstaDeBajaException("La cuenta a transferir esta dada de baja");
        }

        if (monto > cuentaOrigen.getSaldo()) { //Lanzo excepcion cuando no tiene dinero para trnsferir
            throw new CuentaSinDineroException("No hay suficiente dinero en la cuenta " + cuentaOrigen.getNombre() + ", su saldo es de $" + cuentaOrigen.getSaldo());
        }

        //Borro la cuentaOrigen ya que va ser modificada
        cuentaDao.deleteCuenta(cuentaOrigen.getCVU());
        cuentaDao.deleteCuenta(cuentaDestino.getCVU());

        //Resto el monto a la cuenta origen y sumo a la que se envia
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

        //Tomo registro de la transferencia en la cuentaOrginen y destino
        Movimiento movimiento = crearMovimiento(tipoOperacion + cuentaDestino.getNombre(), monto, cuentaOrigen.getCVU());
        movimientosDao.saveMovimiento(movimiento);

        Movimiento movimientoDestino = crearMovimiento(tipoOperacionDestino + cuentaOrigen.getNombre(), monto, cuentaDestino.getCVU());
        movimientosDao.saveMovimiento(movimientoDestino);

        //Guardo la cuentaOrigen y cuentaDestino modificada
        cuentaDao.saveCuenta(cuentaOrigen);
        cuentaDao.saveCuenta(cuentaDestino);

        return cuentaOrigen.getSaldo();
    }
}
