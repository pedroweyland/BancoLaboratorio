package ar.edu.utn.frbb.tup.operaciones.modulos;

import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.Movimiento;
import ar.edu.utn.frbb.tup.operaciones.baseOperaciones;

import java.util.List;

public class MostrarMovimientos extends baseOperaciones {

    public void mostrarMovimientos(Cuenta cuenta){

        if (!cuenta.getMovimientos().isEmpty()) { //Valido si la cuenta tiene movimientos o no

            List<Movimiento> movimientos = cuenta.getMovimientos();

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