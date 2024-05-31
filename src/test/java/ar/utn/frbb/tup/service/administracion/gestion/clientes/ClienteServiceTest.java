package ar.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.service.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    private static ClienteDao clienteDao;

    @BeforeAll
    public static void setUp(){
        clienteDao = new ClienteDao();
        clienteDao.inicializarClientes();
    }

    @Test
    public void testClienteSuccess() throws ClienteNoEncontradoException {
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

        //Testeo si el cliente se ha creado correctamente en mi archivo
        assertNotNull(clienteDao.findCliente(cliente.getDni()));

        //Elimino el cliente que fue guardado previamente
        clienteDao.deleteCliente(cliente.getDni());

        //Testeo si el cliente se ha eliminado correctamente en mi archivo
        assertNull(clienteDao.findCliente(cliente.getDni()));

    }

    @Test
    public void testClienteExistenteException() throws ClienteNoEncontradoException{
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

        Cliente clienteNuevo = new Cliente();

        clienteNuevo.setNombre("Carlitos");
        clienteNuevo.setApellido("Weyland");
        clienteNuevo.setDireccion("Berutti");
        clienteNuevo.setDni(45501926);
        clienteNuevo.setFechaNacimiento(LocalDate.of(2004, 1, 21));

        clienteNuevo.setMail("Carlitos@gmail.com");
        clienteNuevo.setBanco("Santader");
        clienteNuevo.setTipoPersona(TipoPersona.PERSONA_FISICA);
        clienteNuevo.setFechaAlta(LocalDate.now());

        assertThrows(ClienteExistenteException.class, () -> clienteDao.saveCliente(clienteNuevo));

        clienteDao.deleteCliente(cliente.getDni());

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
        clienteDao.deleteCliente(cliente.getDni());

        //Testo si el cliente se elimino correctamente
        assertNull(clienteDao.findCliente(cliente.getDni()));
    }

    @Test
    public void testEliminarClienteNoEncontrado() {

        //Testo si al ingresar un dni que no existe, se lanza la excepcion correcta
        assertThrows(ClienteNoEncontradoException.class, () -> clienteDao.deleteCliente(43535626L));

    }
}
