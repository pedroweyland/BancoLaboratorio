package ar.edu.utn.frbb.tup.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;
import java.util.Set;

public class EliminarCuenta extends BaseGestion {

    public void eliminarCuenta(List<Cliente> clientes){

        boolean seguir = true;

        while (seguir){

            long dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");

            if (dni == 0) break; //Si escribe 0 termina con el bucle

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

                if (cuenta == null){
                    System.out.println("No existe la cuenta con el CVU: " + cvu);
                } else {
                    //Borro la cuenta en Cuentas
                    cuentas.remove(cuenta);

                    //Borro El nombre de la cuenta que esta asociada en clientes
                    String nombreCuenta = cuenta.getNombre();
                    cliente.getNombreCuentas().remove(nombreCuenta);

                    System.out.println("----------------------------------------");
                    System.out.println("La cuenta se borro exitosamente");
                    System.out.println("----------------------------------------");
                }
                seguir = false;

                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }

}
