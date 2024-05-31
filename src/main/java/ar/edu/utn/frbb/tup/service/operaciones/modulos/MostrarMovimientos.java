package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MostrarMovimientos extends baseOperaciones {
    MovimientosDao movimientosDao;

    public MostrarMovimientos(MovimientosDao movimientosDao) {
        this.movimientosDao = movimientosDao;
    }

    public void mostrarMovimientos(Cuenta cuenta){
        List<Movimiento> movimientos = movimientosDao.findMovimientos(cuenta.getCVU());

        if (!movimientos.isEmpty()) { //Valido si la cuenta tiene movimientos o no

            //Recorro la lista de movivmientos para ir mostrando uno por uno
            System.out.println("---------- Movimientos de la cuenta " + cuenta.getNombre() + " ----------");
            for (Movimiento movimiento : movimientos) {
                System.out.println(mostrarMovimiento(movimiento));
                System.out.println("---------------------------------------------------------");
            }

        } else {
            System.out.println("La cuenta no tiene movimientos asociados");

        }

        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();

    }

    public String mostrarMovimiento(Movimiento movimiento){
        return "Fecha de operacion: " + movimiento.getFechaOperacion() + "\n" +
                "Hora de operacion: " + movimiento.getHoraOperacion() + "\n" +
                "Tipo de movimiento: " + movimiento.getTipoOperacion() + "\n" +
                "Monto del movimiento: " + movimiento.getMonto();
    }


}
