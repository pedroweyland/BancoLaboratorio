package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

public class MostrarCliente extends BaseGestion {

    // Mostrar un cliente en especifico
    public void mostrarCliente(){
        boolean seguir = true;

        while (seguir) {
            long dni = pedirDni("Escriba el DNI del usuario que quiere ver: (0 para salir)");

            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = clienteDao.findCliente(dni);

            try {

                if (cliente == null) { //Si el cliente no existe lanzo una excepcion (Ya que no hay nada que mostrar)
                    throw new ClienteNoEncontradoException("No existe ningun cliente con el DNI ingresado");
                }

                System.out.println("------------ Muestra cliente -----------");
                System.out.println(toString(cliente)); //Muestro en pantalla el cliente encontrado
                seguir = false;

            } catch (ClienteNoEncontradoException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
            } finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }

    }

}
