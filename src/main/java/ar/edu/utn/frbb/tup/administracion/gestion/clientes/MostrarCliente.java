package ar.edu.utn.frbb.tup.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;

public class MostrarCliente extends BaseGestion {

    // Mostrar un cliente en especifico
    public void mostrarCliente(Banco banco){
        boolean seguir = true;
        List<Cliente> clientes = banco.getClientes(); //Creo una lista auxiliar 'cliente' con la copia de Clientes que esta en banco ++Legibilidad

        while (seguir) {
            long dni = pedirDni("Escriba el DNI del usuario que quiere ver: (0 para salir)");

            if (dni == 0) break;

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = encontrarCliente(clientes, dni);

            if (cliente == null) {
                System.out.println("No existe ningun cliente con el DNI ingresado");
            } else {
                System.out.println("------------ Muestra cliente -----------");
                System.out.println(toString(cliente)); //Muestro en pantalla el cliente encontrado
                seguir = false;
            }
        }

    }

}
