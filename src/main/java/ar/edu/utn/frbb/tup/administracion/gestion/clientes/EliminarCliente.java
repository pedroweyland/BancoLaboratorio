package ar.edu.utn.frbb.tup.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;


public class EliminarCliente extends BaseGestion {

    //Eliminar cliente
    public void eliminarCliente(List<Cliente> clientes){
        boolean seguir = true;

        while (seguir) {
            long dni = pedirDni("Escriba el DNI al cliente que quiere eliminar: (0 para salir) ");

            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = encontrarCliente(clientes, dni);

            if (cliente == null) {
                System.out.println("No existe ningun cliente con el DNI ingresado");
            } else {

                System.out.println("------------ Cliente eliminado -----------");
                System.out.println(toString(cliente)); //Muestro en pantalla el cliente eliminado

                //Elimino el cliente de la lista
                clientes.remove(cliente);
                seguir = false;
            }

            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }
    }

}
