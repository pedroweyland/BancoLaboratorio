package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Operaciones;
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

    public Operaciones deposito(long cvu , double monto) throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        //Valido que la cuenta existe y que esta de alta
        Cuenta cuenta = cuentaDao.findCuenta(cvu);

        if (cuenta == null){
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cvu);
        }

        if (!cuenta.getEstado()){
            throw new CuentaEstaDeBajaException("Esta cuenta se encuentra de baja, consulta con la sucursal. CVU: " + cuenta.getCVU());
        }

        cuentaDao.deleteCuenta(cuenta.getCVU()); //Borro la cuenta ya que va ser modificada

        //Sumo el monto al saldo que tenia la cuenta
        cuenta.setSaldo(cuenta.getSaldo() + monto); 

        //Tomo registro de la operacion que se hizo
        movimientosDao.saveMovimiento(tipoOperacion, monto, cuenta.getCVU());

        cuentaDao.saveCuenta(cuenta); //Guardo la cuenta modificada

        return new Operaciones().setCvu(cvu).setSaldoActual(cuenta.getSaldo()).setMonto(monto).setTipoOperacion(tipoOperacion);

    }
}
