package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.stereotype.Service;


@Service
public class Deposito {
    private final CuentaDao cuentaDao;
    private final MovimientosDao movimientosDao;
    private final String tipoOperacion = "Deposito";

    public Deposito(CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    public double deposito(long cvu , double monto) throws CuentaNoEncontradaException {

        Cuenta cuenta = cuentaDao.findCuenta(cvu);

        if (cuenta == null){
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cvu);
        }

        cuentaDao.deleteCuenta(cuenta.getCVU()); //Borro la cuenta ya que va ser modificada

        //Sumo el monto al saldo que tenia la cuenta
        cuenta.setSaldo(cuenta.getSaldo() + monto); 

        //Tomo registro de la operacion que se hizo
        movimientosDao.saveMovimiento(tipoOperacion, monto, cuenta.getCVU());

        cuentaDao.saveCuenta(cuenta); //Guardo la cuenta modificada

        return cuenta.getSaldo();

    }
}
