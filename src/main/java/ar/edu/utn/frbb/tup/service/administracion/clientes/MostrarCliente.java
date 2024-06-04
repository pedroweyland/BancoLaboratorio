package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import org.springframework.stereotype.Service;

@Service
public class MostrarCliente extends BaseAdministracion {
    ClienteDao clienteDao;

    public MostrarCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    // Mostrar un cliente en especifico
    public void mostrarCliente(long dni){

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente cliente = clienteDao.findCliente(dni);

        try {
            if (cliente == null){
                throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
            }

            System.out.println("------------ Muestra cliente -----------");
            System.out.println(toString(cliente)); //Muestro en pantalla el cliente encontrado


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
