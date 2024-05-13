package ar.edu.utn.frbb.tup.operaciones.modulos;

import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.Movimiento;
import ar.edu.utn.frbb.tup.operaciones.baseOperaciones;

public class Retiro extends baseOperaciones {
    private final String tipoOperacion = "Retiro";

    public void retiro(Cuenta cuenta) {

        //Pido el monto del retiro
        double monto = ingresarDinero("Ingrese el monto del retiro: ");
        System.out.println("----------------------------------------");

        if (monto > cuenta.getSaldo()) { //Si lo ingresado es mayor al saldo no va a poder retirar
            System.out.println("No puede retirar ese monto, su saldo es de $" + cuenta.getSaldo());

        } else {
            //Resto el monto al saldo que tenia la cuenta
            cuenta.setSaldo(cuenta.getSaldo() - monto);

            //Tomo registro de la operacion que se hizo
            Movimiento movimiento = crearMovimiento(tipoOperacion, monto);
            cuenta.addMovimientos(movimiento);

            System.out.println("Se ha realizado el retiro de $" + monto + " a la cuenta " + cuenta.getNombre());

        }
        System.out.println("----------------------------------------");
        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();

    }
}