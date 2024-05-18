package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.CuentasDeClientesDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static ar.edu.utn.frbb.tup.presentation.input.validator.Validaciones.*;

public class baseOperaciones {
    protected Scanner scanner = new Scanner(System.in);
    protected ClienteDao clienteDao = new ClienteDao();
    protected CuentaDao cuentaDao = new CuentaDao();
    protected CuentasDeClientesDao cuentasDeClientes = new CuentasDeClientesDao();
    protected MovimientosDao movimientosDao = new MovimientosDao();

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public Cuenta encontrarCuenta(Set<Cuenta> cuentas, long cvu) {
        //Funcion que encuentra una cuenta y retorna la cuenta si la encontro o retorna null si no la encontro

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

    public Movimiento crearMovimiento(String tipoOperacion, double monto, long cvu){
        //Creo el movimiento para usar cuando se realizen las operaciones
        Movimiento movimiento = new Movimiento();
        movimiento.setCVU(cvu);
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
