package ar.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EliminarClienteTest {

    private static ClienteDao clienteDao;

    @BeforeAll
    public static void setUp(){
        clienteDao = new ClienteDao();

    }

    @Test
    public void testEliminarClienteSuccess() throws ClienteNoEncontradoException {
        Cliente cliente = new Cliente();
        cliente.setNombre("Pepo");
        cliente.setApellido("Weyland");
        cliente.setDireccion("Berutti");
        cliente.setDni(45501926);
        cliente.setFechaNacimiento(LocalDate.of(2004, 1, 21));

        cliente.setMail("weylandpedro@gmail.com");
        cliente.setBanco("Macro");
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);
        cliente.setFechaAlta(LocalDate.now());

        clienteDao.saveCliente(cliente);

        //Elimino mi cliente
        Cliente aux = clienteDao.deleteCliente(cliente.getDni());
        //Testo si el cliente se elimino correctamente
        assertNull(clienteDao.findCliente(cliente.getDni()));
    }

    @Test
    public void testEliminarClienteNoEncontrado() {

        //Testo si al ingresar un dni que no existe, se lanza la excepcion correcta
        assertThrows(ClienteNoEncontradoException.class, () -> clienteDao.deleteCliente(43535626L));

    }
}
