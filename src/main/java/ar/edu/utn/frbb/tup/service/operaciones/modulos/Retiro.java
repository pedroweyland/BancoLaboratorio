package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.ingresarDinero;

public class Retiro extends baseOperaciones {
    private final String tipoOperacion = "Retiro";

    public void retiro(Cuenta cuenta) {

        if (cuenta.getSaldo() != 0) {
            //Pido el monto del retiro
            double monto = ingresarDinero("Ingrese el monto del retiro: ");
            System.out.println("----------------------------------------");

            if (monto > cuenta.getSaldo()) { //Si lo ingresado es mayor al saldo no va a poder retirar
                System.out.println("No puede retirar ese monto, su saldo es de $" + cuenta.getSaldo());

            } else {
                cuentaDao.deleteCuenta(cuenta.getCVU()); //Borro la cuenta ya que va ser modificada
                //Resto el monto al saldo que tenia la cuenta
                cuenta.setSaldo(cuenta.getSaldo() - monto);

                //Tomo registro de la operacion que se hizo
                Movimiento movimiento = crearMovimiento(tipoOperacion, monto, cuenta.getCVU());
                movimientosDao.saveMovimiento(movimiento);

                System.out.println("Se ha realizado el retiro de $" + monto + " a la cuenta " + cuenta.getNombre());

                cuentaDao.saveCuenta(cuenta); //Guardo la cuenta modificada
            }
            System.out.println("----------------------------------------");


            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        } else {
            System.out.println("----------------------------------------");
            System.out.println("No tienes dinero para retirar");
            System.out.println("----------------------------------------");

            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }
    }
}
