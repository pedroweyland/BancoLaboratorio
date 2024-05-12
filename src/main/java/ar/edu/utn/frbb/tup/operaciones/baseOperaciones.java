package ar.edu.utn.frbb.tup.operaciones;

import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.entidades.Movimiento;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static ar.edu.utn.frbb.tup.validator.Validaciones.*;

public class baseOperaciones {

    protected Scanner scanner = new Scanner(System.in);

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public Cuenta encontrarCuenta(Set<Cuenta> cuentas, long cvu) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCVU() == cvu) {
                return cuenta;
            }
        }
        return null;
    }

    public Cliente encontrarCliente(List<Cliente> clientes, long dni){
        //Funcion que encuentra cliente y retorna el cliente o Null si no lo encuentra

        for (Cliente cliente : clientes) { //Busco en la lista clientes si existe el dni que puso el usuario
            if (cliente.getDni() == dni){
                return cliente;
            }
        }
        return null;
    }


    public long pedirDni(String texto){
        //Funcion para que el usuario ingrese dni, valido y devuelve en tipo 'long'

        System.out.println(texto);
        String dni = scanner.nextLine();

        while (!esNumeroLong(dni)){
            System.out.println("Error, ingrese un numero: ");
            dni = scanner.nextLine();
        }

        return Long.parseLong(dni);
    }

    public long pedirCvu(String texto){
        //Funcion para que el usuario ingrese Cvu de cuenta, valido y devuelve en tipo 'long'

        System.out.println(texto);
        String cvu = scanner.nextLine();

        while (!esNumeroLong(cvu)){
            System.out.println("Error, ingrese un numero: ");
            cvu = scanner.nextLine();
        }

        return Long.parseLong(cvu);
    }

    public Movimiento crearMovimiento(String tipoOperacion, double monto){
        //Creo el movimiento para usar cuando se realizen las operaciones
        Movimiento movimiento = new Movimiento();

        movimiento.setFecha(LocalDate.now());
        movimiento.setHora(LocalTime.now());
        movimiento.setTipoOperacion(tipoOperacion);
        movimiento.setMonto(monto);
        return movimiento;
    }

    public double ingresarDinero(String mensaje) {
        boolean seguir = true;

        while (seguir) {

            System.out.println(mensaje); //Usuario ingresado la cantidad de dinero
            String dineroStr = scanner.nextLine();

            double dinero = esDouble(dineroStr); //Valido que sea un numero

            if (dinero == -1){ //Si no es un numero devuelve -1, por ende marca error
                System.out.println("El valor ingresado no es valido, ingrese un valor numerico");
            } else if (dinero <= 0){ //El valor tiene que ser mayor a 0
                System.out.println("El valor ingresado debe ser mayor a 0");
            } else {
                return dinero;
            }

        }

        return 0;
    }
}
