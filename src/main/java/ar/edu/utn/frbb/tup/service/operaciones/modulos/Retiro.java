package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.service.exception.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.ingresarDinero;

public class Retiro extends baseOperaciones {
    private final String tipoOperacion = "Retiro";

    public void retiro(Cuenta cuenta) {

        try {
            if (cuenta.getSaldo() == 0){ //Si no tiene dinero para retirar lanzo una excepcion
                throw new CuentaSinDineroException("No tiene dinero en esta cuenta para retirar");
            }

            //Pido el monto del retiro
            double monto = ingresarDinero("Ingrese el monto del retiro: ");


            if (cuenta.getSaldo() < monto){ //Si no le alcanza el dinero para retirar lanza una excepcion
                throw new CuentaSinDineroException("No puede retirar ese monto, su saldo es de $" + cuenta.getSaldo());
            }

            System.out.println("----------------------------------------");
            cuentaDao.deleteCuenta(cuenta.getCVU()); //Borro la cuenta ya que va ser modificada
            //Resto el monto al saldo que tenia la cuenta
            cuenta.setSaldo(cuenta.getSaldo() - monto);

            //Tomo registro de la operacion que se hizo
            Movimiento movimiento = crearMovimiento(tipoOperacion, monto, cuenta.getCVU());
            movimientosDao.saveMovimiento(movimiento);

            System.out.println("Se ha realizado el retiro de $" + monto + " a la cuenta " + cuenta.getNombre());

            cuentaDao.saveCuenta(cuenta); //Guardo la cuenta modificada

            System.out.println("----------------------------------------");

        } catch (CuentaSinDineroException e) {
            System.out.println("----------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("----------------------------------------");
        } finally {
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }

    }
}
