package ar.edu.utn.frbb.tup.service.handler;


import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import org.springframework.stereotype.Component;

@Component
public class ClienteService {
    ClienteDao clienteDao = new ClienteDao();

    public void findAllClientes() throws ClientesVaciosException {
        clienteDao.findAllClientes();
    }

    public void inicializarClientes() {
        clienteDao.inicializarClientes();
    }

}
