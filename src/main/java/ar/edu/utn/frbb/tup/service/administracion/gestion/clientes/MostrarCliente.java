package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;


@Service
public class MostrarCliente extends BaseGestion {

    // Mostrar un cliente en especifico
    public void mostrarCliente(){
        boolean seguir = true;

        while (seguir) {
            long dni = pedirDni("Escriba el DNI del usuario que quiere ver: (0 para salir)");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = clienteDao.findCliente(dni);

            try {
                if (cliente == null){
                    throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
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
