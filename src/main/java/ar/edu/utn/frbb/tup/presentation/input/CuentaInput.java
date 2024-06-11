package ar.edu.utn.frbb.tup.presentation.input;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.CrearCuenta;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;


@Component
public class CuentaInput extends BasePresentation {
    private Scanner scanner = new Scanner(System.in);

    private final CrearCuenta crearCuenta;

    public CuentaInput(CrearCuenta crearCuenta){
        this.crearCuenta = crearCuenta;
    }

    public void creacionCuenta(long dniTitular) throws ClienteNoEncontradoException {
        Random r = new Random();
        Cuenta cuenta = new Cuenta();
        //Creacion de cuenta para el cliente

        clearScreen();

        cuenta.setNombre(ingresarNombre());

        //Inicializo los valores de la cuenta NUEVA
        cuenta.setEstado(true);
        cuenta.setSaldo(0);

        cuenta.setCVU(generarCVU(r)); //Creo un CVU random
        cuenta.setDniTitular(dniTitular);

        cuenta.setFechaCreacion(LocalDate.now());
        cuenta.setTipoCuenta(ingresarTipoCuenta());
        cuenta.setTipoMoneda(ingresarTipoMoneda());
        //Muestro en pantalla si la cuenta fue creada exitosamente
        Cuenta c = crearCuenta.crearCuenta(cuenta);
        if (c != null){
            System.out.println(toString(cuenta, "------------ Cuenta Creada Con Exito -----------"));
        }

    }

    public String ingresarNombre() {
        System.out.println("Ingrese el nombre de la cuenta: ");
        return scanner.nextLine();
    }

    public TipoCuenta ingresarTipoCuenta(){

        while (true) {
            try {
                System.out.println("Ingrese el tipo de cuenta Corriente(C) o Ahorro(A): ");
                String tipoCuentaStr = scanner.nextLine().toUpperCase();

                return TipoCuenta.fromString(tipoCuentaStr); //Retorno el tipo Cuenta
            } catch (IllegalArgumentException e) {
                System.out.println("---------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        }

    }

    public TipoMoneda ingresarTipoMoneda(){
        while(true){
            try {
                System.out.println("Ingrese el tipo de moneda de la cuenta, Pesos(P) o Dolares(D): ");
                String tipoMonedaStr = scanner.nextLine().toUpperCase();

                return TipoMoneda.fromString(tipoMonedaStr);
            } catch (IllegalArgumentException e) {
                System.out.println("---------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("---------------------------------------");
            }
        }
    }

    public long generarCVU(Random r) {
        return r.nextInt(900000) + 100000; //Genera numero aleatorio entre 100000 y 999999
    }
}
