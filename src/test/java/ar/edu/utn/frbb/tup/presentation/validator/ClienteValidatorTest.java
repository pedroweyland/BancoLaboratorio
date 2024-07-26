package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteValidatorTest {

    ClienteValidator clienteValidator;

    @BeforeEach
    public void setUp() {
        clienteValidator = new ClienteValidator();
    }

    @Test
    public void testClienteValidatorSuccess(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        //Verifico que no haga ningun Throw
        assertDoesNotThrow(() -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinNombreException(){
        ClienteDto clienteDto = new ClienteDto();
        //clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinApellidoException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        //clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinDireccionException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        //clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinFechaNacimientoException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        //clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinBancoException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        //clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinMailException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        //clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinTipoPersonaException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        //clienteDto.setTipoPersona("F");
        clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteSinDniException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        //clienteDto.setDni(12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

    @Test
    public void testClienteDniNegativoException(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Peperino");
        clienteDto.setApellido("Pomoro");
        clienteDto.setDireccion("Alem");
        clienteDto.setFechaNacimiento("2002-02-02");
        clienteDto.setBanco("Macro");
        clienteDto.setMail("example@gmail.com");
        clienteDto.setTipoPersona("F");
        clienteDto.setDni(-12341234);

        assertThrows(IllegalArgumentException.class, () -> clienteValidator.validateCliente(clienteDto));
    }

}
