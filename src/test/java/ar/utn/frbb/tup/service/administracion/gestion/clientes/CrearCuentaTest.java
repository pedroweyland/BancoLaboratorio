package ar.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CrearCuentaTest {

    private static ClienteDao clienteDao;


    @BeforeAll
    public static void setUp(){
        clienteDao = new ClienteDao();
    }

    @Test
    public void testClienteSuccess() throws ClienteExistenteException {
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

        assertNotNull(clienteDao.findCliente(cliente.getDni()));
        //Testeo si el cliente se ha creado correctamente en mi archivo

        clienteDao.deleteCliente(cliente.getDni());

        assertNull(clienteDao.findCliente(cliente.getDni()));
        //Testeo si el cliente se ha eliminado correctamente en mi archivo
    }
}
