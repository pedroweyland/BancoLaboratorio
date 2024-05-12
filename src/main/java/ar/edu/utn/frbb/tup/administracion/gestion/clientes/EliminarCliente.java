package ar.edu.utn.frbb.tup.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;


public class EliminarCliente extends BaseGestion {

    //Eliminar cliente
    public void eliminarCliente(Banco banco){
        List<Cliente> clientes = banco.getClientes(); //Creo una lista auxiliar 'cliente' con la copia de Clientes que esta en banco ++Legibilidad
        boolean seguir = true;

        while (seguir) {
            long dni = pedirDni("Escriba el DNI al cliente que quiere eliminar: (0 para salir) ");

            if (dni == 0) break;

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = encontrarCliente(clientes, dni);

            if (cliente == null) {
                System.out.println("No existe ningun cliente con el DNI ingresado");
            } else {

                System.out.println("------------ Cliente eliminado -----------");
                System.out.println(toString(cliente)); //Muestro en pantalla el cliente encontrado

                //Elimino el cliente de la lista
                clientes.remove(cliente);
                seguir = false;
            }
        }
    }

}
