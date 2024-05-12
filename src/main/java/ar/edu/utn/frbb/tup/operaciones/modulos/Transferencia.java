package ar.edu.utn.frbb.tup.operaciones.modulos;

import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.Movimiento;
import ar.edu.utn.frbb.tup.operaciones.baseOperaciones;

public class Transferencia extends baseOperaciones {
    private final String tipoOperacion = "Transferencia";
    private final String tipoOperacionDestino = "Deposito recibido de la cuenta ";

    public void transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino){

        if (cuentaDestino == null) {
            System.out.println("No existe la cuenta a transferir");
        } else {

            double monto = ingresarDinero("Ingrese el monto a transferir a la cuenta " + cuentaDestino.getNombre() + ": ");

            if (monto > cuentaOrigen.getSaldo()){
                System.out.println("No hay suficiente dinero en la cuenta " + cuentaOrigen.getNombre());
            } else {
                //Restp el monto a la cuenta origen y sumo a la que se envia
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

                //Tomo registro de la transferencia
                Movimiento movimiento = crearMovimiento(tipoOperacion, monto);
                cuentaOrigen.addMovimientos(movimiento);

                Movimiento movimientoDestino = crearMovimiento(tipoOperacionDestino + cuentaOrigen.getNombre(), monto);
                cuentaDestino.addMovimientos(movimientoDestino);

                System.out.println("Se ha transferido " + monto + " a la cuenta " + cuentaDestino.getNombre());
            }
        }
    }
}
