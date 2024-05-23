package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

public class MostrarCuenta extends BaseGestion {

    public void mostrarCuenta() {
        boolean seguir = true;


        while (seguir) {
            long dni = pedirDni("Escriba el DNI del usuario para ver sus cuentas: (0 para salir)");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = clienteDao.findCliente(dni);


            if (cliente == null) {
                System.out.println("----------------------------------------");
                System.out.println("No existe ningun cliente con el DNI ingresado");
                System.out.println("----------------------------------------");
            } else {

                //Funcion que me devuelve una lista de los cvu que tiene el cliente para despues mostrarlo por pantalla
                List<Long> cvuMostrar = cuentaDao.getRelacionesDni(dni);
                List<Cuenta> cuentas = cuentaDao.findAllCuentas();

                if (cvuMostrar.isEmpty()) {
                    System.out.println("----------------------------------------");
                    System.out.println("No tiene cuentas asociadas");
                    System.out.println("----------------------------------------");
                } else {
                    int cantCuentas = 0;

                    for (Cuenta cuenta : cuentas) {
                        if (cuenta.getDniTitular() == dni) {
                            //Muestro en pantalla las cuentas que creo el cliente
                            System.out.println("------- Cuentas del cliente " + cliente.getNombre() + " -------");

                            System.out.println(toString(cuenta)); //Muestro en pantalla las cuentas del cliente
                            cantCuentas++;
                        }
                    }
                    /*
                    for (Long cvu : cvuMostrar) {


                        //Muestro en pantalla las cuentas que creo el cliente
                        System.out.println("------- Cuentas del cliente " + cliente.getNombre() + " -------");

                        System.out.println(toString(cuentaDao.findCuentaDelCliente(cvu, dni))); //Muestro en pantalla las cuentas del cliente
                        cantCuentas++;
                    }
                    */

                    System.out.println("Tiene " + cantCuentas + " cuentas asociadas");
                    System.out.println("----------------------------------------");
                }

                seguir = false;
            }
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }
    }
}
