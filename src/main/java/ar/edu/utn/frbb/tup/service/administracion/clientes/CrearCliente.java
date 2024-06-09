package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrearCliente extends BaseAdministracion {
    private final ClienteDao clienteDao;


    public CrearCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente crearCliente(Cliente cliente) throws ClienteExistenteException {

        if (clienteDao.findCliente(cliente.getDni()) != null){
            throw new ClienteExistenteException("Ya existe un cliente con el DNI ingresado");
        }

        //Guardo el cliente ingresado, si ya existe se lanza la excepcion por ende no se guarda y el catch agarra la excepcion
        clienteDao.saveCliente(cliente);

        return cliente;


    }
}
