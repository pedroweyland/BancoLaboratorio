package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.exception.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MostrarMovimientos extends baseOperaciones {
    private final MovimientosDao movimientosDao;

    public MostrarMovimientos(MovimientosDao movimientosDao) {
        this.movimientosDao = movimientosDao;
    }

    public List<Movimiento> mostrarMovimientos(Cuenta cuenta) throws MovimientosVaciosException {
        List<Movimiento> movimientos = movimientosDao.findMovimientos(cuenta.getCVU());

        if (!movimientos.isEmpty()) { //Valido si existen movimientos
            List<Movimiento> auxMovimiento = new ArrayList<>();

            //Recorro la lista de movivmientos para guardar los movimientos de la cuenta en otra lista separada
            for (Movimiento movimiento : movimientos) {
                if (cuenta.getCVU() == movimiento.getCVU()) {
                    auxMovimiento.add(movimiento);
                }
            }
            return auxMovimiento;
        } else{
            throw new MovimientosVaciosException("La cuenta no tiene movimientos");
        }
    }

}
