package ar.edu.utn.frbb.tup.inputs;

import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.TipoCuenta;

import java.time.LocalDate;
import java.util.Random;

public class CuentaInput extends BaseInput {

    public Cuenta creacionCuenta(){
        Random r = new Random();
        Cuenta cuenta = new Cuenta();
        //Creacion de cuenta para el cliente

        clearScreen();


        cuenta.setNombre(ingresarNombre());

        //Inicializo los valores de la cuenta NUEVA
        cuenta.setEstado(true);
        cuenta.setSaldo(0);
        cuenta.setCVU(generarCVU(r)); //Creo un CVU random
        cuenta.setFechaCreacion(LocalDate.now());
        cuenta.setTipoCuenta(ingresarTipoCuenta());
        return cuenta;

    }

    public String ingresarNombre() {
        System.out.println("Ingrese el nombre de la cuenta: ");
        return scanner.nextLine();
    }

    public TipoCuenta ingresarTipoCuenta(){
        String tipoCuentaStr = null;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("Ingrese el tipo de cuenta Corriente(C) o Ahorro(A): ");
                tipoCuentaStr = scanner.nextLine().toUpperCase();
                return TipoCuenta.fromString(tipoCuentaStr); //Retorno el tipo persona
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Por favor, ingrese un valor valido (C o A).");
            }
        }
        return null;
    }
    public long generarCVU(Random r) {
        return r.nextInt(900000) + 100000; //Genera numero aleatorio entre 100000 y 999999
    }
}
