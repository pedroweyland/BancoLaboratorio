package ar.edu.utn.frbb.tup.service.operaciones.modulos;

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

    public List<Movimiento> mostrarMovimientos(Cuenta cuenta){
        List<Movimiento> movimientos = movimientosDao.findMovimientos(cuenta.getCVU());

        if (!movimientos.isEmpty()) { //Valido si la cuenta tiene movimientos o no
            List<Movimiento> auxMovimiento = new ArrayList<>();

            //Recorro la lista de movivmientos para ir mostrando uno por uno
            for (Movimiento movimiento : movimientos) {
                if (cuenta.getCVU() == movimiento.getCVU()) {
                    auxMovimiento.add(movimiento);
                }
            }
            return auxMovimiento;
        } else {
            System.out.println("La cuenta no tiene movimientos asociados");

        }

        return null;

    }


}
