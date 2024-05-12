package ar.edu.utn.frbb.tup.operaciones.modulos;

import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.Movimiento;
import ar.edu.utn.frbb.tup.operaciones.baseOperaciones;


public class Deposito extends baseOperaciones {
    private final String tipoOperacion = "Deposito";

    public void deposito(Cuenta cuenta){

        //Pido el monto del deposito
        double monto = ingresarDinero("Ingrese el monto del deposito: ");
        //Sumo el monto al saldo que tenia la cuenta
        cuenta.setSaldo(cuenta.getSaldo() + monto); 

        //Tomo registro de la operacion que se hizo
        Movimiento movimiento = crearMovimiento(tipoOperacion, monto);
        cuenta.addMovimientos(movimiento);

        System.out.println("Se ha realizado el deposito de $" + monto + " a la cuenta " + cuenta.getNombre());

    }
}
