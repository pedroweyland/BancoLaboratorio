package ar.edu.utn.frbb.tup.operaciones.modulos;

import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.Movimiento;
import ar.edu.utn.frbb.tup.operaciones.baseOperaciones;

public class Consulta extends baseOperaciones {
    private final String tipoOperacion = "Consulta";

    public void consulta(Cuenta cuenta){

        System.out.println("----------------------------------------");
        System.out.println("Su saldo es de la cuenta " + cuenta.getNombre() + " es de $" + cuenta.getSaldo());
        System.out.println("----------------------------------------");

        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();

        //Tomo registro de la operacion que se hizo
        Movimiento movimiento = crearMovimiento(tipoOperacion, 0);
        cuenta.addMovimientos(movimiento);
    }
}