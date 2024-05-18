package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;

public class Transferencia extends baseOperaciones {
    private final String tipoOperacion = "Transferencia a la cuenta ";
    private final String tipoOperacionDestino = "Deposito recibido de la cuenta ";

    public void transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino){

        if (cuentaOrigen.getSaldo() != 0){
            if (cuentaDestino == null) {
                System.out.println("No existe la cuenta a transferir");
            } else if (cuentaOrigen.getCVU() == cuentaDestino.getCVU()) {
                System.out.println("No se puede transferir a la misma cuenta");
            } else {
                double monto = ingresarDinero("Ingrese el monto a transferir a la cuenta " + cuentaDestino.getNombre() + ": ");

                System.out.println("----------------------------------------");
                if (monto > cuentaOrigen.getSaldo()){
                    System.out.println("No hay suficiente dinero en la cuenta " + cuentaOrigen.getNombre());
                } else {
                    cuentaDao.deleteCuenta(cuentaOrigen.getCVU()); //Borro la cuentaOrigen ya que va ser modificada
                    cuentaDao.deleteCuenta(cuentaDestino.getCVU()); //Borro la cuentaDestino ya que va ser modificada

                    //Resto el monto a la cuenta origen y sumo a la que se envia
                    cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
                    cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

                    //Tomo registro de la transferencia
                    Movimiento movimiento = crearMovimiento(tipoOperacion + cuentaDestino.getNombre(), monto);
                    cuentaOrigen.addMovimientos(movimiento);

                    Movimiento movimientoDestino = crearMovimiento(tipoOperacionDestino + cuentaOrigen.getNombre(), monto);
                    cuentaDestino.addMovimientos(movimientoDestino);


                    System.out.println("Se ha transferido " + monto + " a la cuenta " + cuentaDestino.getNombre());

                    cuentaDao.saveCuenta(cuentaOrigen); //Guardo la cuentaOrigen modificada
                    cuentaDao.saveCuenta(cuentaDestino); //Guardo la cuentaDestino modificada
                }
                System.out.println("----------------------------------------");
            }
        } else {
            System.out.println("----------------------------------------");
            System.out.println("No tienes dinero para transferir");
            System.out.println("----------------------------------------");
        }

        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();
    }
}
