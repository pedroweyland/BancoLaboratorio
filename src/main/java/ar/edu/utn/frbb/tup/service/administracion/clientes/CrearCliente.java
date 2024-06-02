package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import org.springframework.stereotype.Service;

@Service
public class CrearCliente extends BaseAdministracion {
    ClienteDao clienteDao;
    ClienteInput clienteInput;

    public CrearCliente(ClienteDao clienteDao, ClienteInput clienteInput) {
        this.clienteDao = clienteDao;
        this.clienteInput = clienteInput;
    }

    public void crearCliente(Cliente cliente) {

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
