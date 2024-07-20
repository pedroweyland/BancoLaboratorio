package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Operaciones;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.stereotype.Service;

@Service
public class Consulta {
    private final MovimientosDao movimientosDao;
    private final CuentaDao cuentaDao;
    private final String tipoOperacion = "Consulta";

    public Consulta(MovimientosDao movimientosDao, CuentaDao cuentaDao) {
        this.movimientosDao = movimientosDao;
        this.cuentaDao = cuentaDao;
    }

    public Operaciones consulta(long cvu) throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        //Valido que la cuenta existe y que esta de alta
        Cuenta cuenta = cuentaDao.findCuenta(cvu);

        if (cuenta == null){
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cvu);
        }

        if (!cuenta.getEstado()){
            throw new CuentaEstaDeBajaException("Esta cuenta se encuentra de baja, consulta con la sucursal. CVU: " + cuenta.getCVU());
        }

        //Tomo registro de la operacion que se hizo
        movimientosDao.saveMovimiento(tipoOperacion, 0, cuenta.getCVU());

        //Devuelvo un Objeto operacion de la consulta que se hizo
        return new Operaciones().setCvu(cvu).setSaldoActual(cuenta.getSaldo()).setTipoOperacion(tipoOperacion);
    }
}
