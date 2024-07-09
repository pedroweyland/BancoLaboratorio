package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracionTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CrearClienteTest {

    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    CrearCliente crearCliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        crearCliente = new CrearCliente(clienteDao);
    }


    @Test
    public void testCrearClienteSuccess() throws ClienteExistenteException, ClienteMenorDeEdadException {
        ClienteDto pepoDto = BaseAdministracionTest.getClienteDto("Pepo", 12345678L);

        when(clienteDao.findCliente(pepoDto.getDni())).thenReturn(null);

        Cliente pepo = crearCliente.crearCliente(pepoDto);

        //Verifico que clienteDao se haya ejecutado una ves
        verify(clienteDao, times(1)).saveCliente(pepo);
        verify(clienteDao, times(1)).findCliente(pepoDto.getDni());

        assertEquals(pepo.getDni(), pepoDto.getDni());
        assertNotNull(pepo);

    }

    @Test
    public void testCrearClienteExistente(){
        ClienteDto pepo = BaseAdministracionTest.getClienteDto("Pepo", 12345678L);

        when(clienteDao.findCliente(pepo.getDni())).thenReturn(new Cliente());

        assertThrows(ClienteExistenteException.class, () -> crearCliente.crearCliente(pepo));
    }

    @Test
    public void testCrearClienteMenorDeEdad(){
        ClienteDto pepoDto = BaseAdministracionTest.getClienteDto("Pepo", 12345678L);
        pepoDto.setFechaNacimiento("2010-02-02");

        when(clienteDao.findCliente(pepoDto.getDni())).thenReturn(null);

        assertThrows(ClienteMenorDeEdadException.class, () -> crearCliente.crearCliente(pepoDto));
    }

    @Test
    public void testCrearClienteFechaNacimientoInvalida(){
        ClienteDto pepoDto = BaseAdministracionTest.getClienteDto("Pepo", 12345678L);
        pepoDto.setFechaNacimiento("Fecha-Invalida-!");

        assertThrows(DateTimeException.class, () -> crearCliente.crearCliente(pepoDto));

    }

}
