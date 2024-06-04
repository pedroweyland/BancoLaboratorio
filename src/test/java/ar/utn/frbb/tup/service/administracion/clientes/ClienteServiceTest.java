package ar.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.administracion.clientes.CrearCliente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceTest {

    @Mock
    private ClienteDao clienteDao;

    @InjectMocks
    private CrearCliente CrearCliente;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    /*
    @Test
    public void testClienteSuccess() {
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

        CrearCliente.crearCliente(cliente);

        verify(clienteDao, times(1)).saveCliente(any(Cliente.class));

    }

    @Test
    public void testClienteExistenteException() throws ClienteNoEncontradoException {
        ClienteDao clienteDao = new ClienteDao();

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

        //Verifico si es llamado correctamente
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
    public void testEliminarClienteSuccess() throws ClienteNoEncontradoException, ClienteExistenteException {
        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = new Cliente();
        clienteDao.inicializarClientes();

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

     */
}
