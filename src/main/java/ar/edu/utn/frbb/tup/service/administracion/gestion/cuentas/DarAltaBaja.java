package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.*;

public class DarAltaBaja extends BaseGestion {


    public void gestionarEstado () {
        boolean seguir = true;

        while (seguir) {
            long dni = pedirDni("Escriba el DNI del usuario para ver sus cuentas: (0 para salir)");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = clienteDao.findCliente(dni);


            if (cliente == null) {
                System.out.println("No existe ningun cliente con el DNI ingresado ");

            } else {
                //Pido CVU de la cuenta que quiere eliminar
                long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere dar de alta/baja: (0 para salir) ");
                clearScreen();

                if (cvu == 0) break; //Si escribe 0 termina con el bucle

                //Funcion que devuelve la cuenta encontrada o vuelve Null si no lo encontro, solo devuelve las cuentas que tiene asocida el cliente
                Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

                if (cuenta == null) {
                    System.out.println("----------------------------------------");
                    System.out.println("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
                    System.out.println("----------------------------------------");
                } else {

                    cuentaDao.deleteCuenta(cvu); //Borro la cuenta ya que va ser actualizada

                    //Si quiere dar de alta retorna True. si quiere dar de baja retorna False
                    boolean opcion = pedirOpcion("Escriba (B) si quiere dar de Baja o (A) si quere dar de Alta");

                    System.out.println("----------------------------------------");
                    darAltaBaja(cuenta, opcion);
                    System.out.println("----------------------------------------");

                    cuentaDao.saveCuenta(cuenta); //Guardo la cuenta y la relacion actualizada

                }

                seguir = false;

                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }

    public void darAltaBaja (Cuenta cuenta, boolean darDeAlta) {
        //Funcion que da de alta o baja a una cuenta

        if (darDeAlta) { //Si darDeAlta es True significa que el usuario quiere dar de alta, si es False significa que lo quiere dar de baja
            if (cuenta.getEstado()) { //Condicional para saber si ya estaba dado de alta o no
                System.out.println("La cuenta " + cuenta.getNombre() + " ya estaba dada de alta");
            } else {
                cuenta.setEstado(true);
                System.out.println("La cuenta " + cuenta.getNombre() + " fue dada de alta correctamente");
            }
        } else {
            if (cuenta.getEstado()) { //Condicional para saber si ya estaba dado de baja o no
                cuenta.setEstado(false);
                System.out.println("La cuenta " + cuenta.getNombre() + " fue dada de baja correctamente");
            } else {
                System.out.println("La cuenta " + cuenta.getNombre() + " ya estaba dada de baja");
            }
        }
    }

}
