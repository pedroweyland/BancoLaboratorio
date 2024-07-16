package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MostrarMovimientos {
    private final MovimientosDao movimientosDao;
    private final CuentaDao cuentaDao;

    public MostrarMovimientos(MovimientosDao movimientosDao, CuentaDao cuentaDao) {
        this.movimientosDao = movimientosDao;
        this.cuentaDao = cuentaDao;
    }

    public List<Movimiento> mostrarMovimientos(long cvu) throws MovimientosVaciosException, CuentaNoEncontradaException {

        Cuenta cuenta = cuentaDao.findCuenta(cvu);

        if (cuenta == null){
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cvu);
        }
        List<Movimiento> movimientos = movimientosDao.findMovimientos(cuenta.getCVU());

        if (movimientos.isEmpty()){
            throw new MovimientosVaciosException("La cuenta no tiene movimientos");
        }

        return movimientos;

    }

}
