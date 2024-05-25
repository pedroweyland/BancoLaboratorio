package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.service.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;

public class CrearCliente extends BaseGestion {
    ClienteInput clienteInput = new ClienteInput();

    // Creacion Cliente
    public void crearCliente() {
        boolean existe = false;

        //Usuario ingresa los datos y se guarda en la variable cliente
        Cliente cliente = clienteInput.ingresoCliente();

        try {
            //Guardo el cliente ingresado, si ya existe se lanza la excepcion por ende no se guarda y el catch agarra la excepcion
            clienteDao.saveCliente(cliente);
            System.out.println("------- Cliente creado con exito -------");
            System.out.println(toString(cliente));

        } catch (ClienteExistenteException ex) {
            System.out.println("----------------------------------------");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------");
        } finally {
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }

    }
}
