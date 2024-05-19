package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.ingresarDinero;


public class Deposito extends baseOperaciones {
    private final String tipoOperacion = "Deposito";

    public void deposito(Cuenta cuenta){
        cuentaDao.deleteCuenta(cuenta.getCVU()); //Borro la cuenta ya que va ser modificada
        //Pido el monto del deposito
        double monto = ingresarDinero("Ingrese el monto del deposito: ");

        //Sumo el monto al saldo que tenia la cuenta
        cuenta.setSaldo(cuenta.getSaldo() + monto); 

        //Tomo registro de la operacion que se hizo
        Movimiento movimiento = crearMovimiento(tipoOperacion, monto, cuenta.getCVU());
        movimientosDao.saveMovimiento(movimiento);

        System.out.println("----------------------------------------");
        System.out.println("Se ha realizado el deposito de $" + monto + " a la cuenta " + cuenta.getNombre());
        System.out.println("----------------------------------------");

        cuentaDao.saveCuenta(cuenta); //Guardo la cuenta modificada
        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();

    }
}
