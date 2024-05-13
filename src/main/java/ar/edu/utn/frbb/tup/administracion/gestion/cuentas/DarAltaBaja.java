package ar.edu.utn.frbb.tup.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import com.sun.source.tree.UsesTree;

import java.util.List;
import java.util.Set;

public class DarAltaBaja extends BaseGestion {


    public void gestionarEstado (List<Cliente> clientes){
        boolean seguir = true;


        while (seguir){
            long dni = pedirDni("Escriba el DNI del usuario para ver sus cuentas: (0 para salir)");

            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = encontrarCliente(clientes, dni);
            clearScreen();

            if (cliente == null){
                System.out.println("No existe ningun cliente con el DNI ingresado ");

            } else if (cliente.getCuentas().isEmpty()) { //Valido si el cliente tiene cuentas o no
                System.out.println("Este cliente no tiene cuentas asociadas");
                break;

            } else {
                Set<Cuenta> cuentas = cliente.getCuentas();

                //Pido CVU de la cuenta que quiere eliminar
                long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                clearScreen();
                if (cvu == 0) break; //Si escribe 0 termina con el bucle

                //Devuelve la cuenta encontrada o Null si no la encontro
                Cuenta cuenta = encontrarCuenta(cuentas, cvu);

                if (cuenta == null) {
                    System.out.println("No existe la cuenta con el CVU: " + cvu);
                } else {
                    //Si quiere dar de alta retorna True. si quiere dar de baja retorna False
                    boolean opcion = pedirOpcion("Escriba (B) si quiere dar de Baja o (A) si quere dar de Alta");

                    System.out.println("----------------------------------------");
                    darAltaBaja(cuenta, opcion);
                    System.out.println("----------------------------------------");
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
