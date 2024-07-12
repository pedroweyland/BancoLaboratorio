package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ModificarClienteTest {
    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    ModificarCliente modificarCliente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        modificarCliente = new ModificarCliente(clienteDao);
    }

    @Test
    public void testModificarClienteSuccess() throws ClienteNoEncontradoException, ClienteMenorDeEdadException {
        ClienteDto pepoDto = BaseAdministracionTest.getClienteDto("Pepo", 12341234L);
        ClienteDto pepoDtoModificado = BaseAdministracionTest.getClienteDto("Juan", 12341234L);
        Cliente pepo = new Cliente(pepoDto);

        when(clienteDao.findCliente(pepoDto.getDni())).thenReturn(pepo);

        Cliente pepoModificado = modificarCliente.modificarCliente(pepoDtoModificado);

        verify(clienteDao, times(1)).findCliente(pepoDto.getDni());
        verify(clienteDao, times(1)).deleteCliente(pepo.getDni());
        verify(clienteDao, times(1)).saveCliente(pepo);

        assertEquals(pepoModificado.getDni(), pepo.getDni());
        assertEquals(pepoModificado.getNombre(), "Juan");
    }

    @Test
    public void testModificarClienteNoEncontrado() {
        ClienteDto pepoDto = BaseAdministracionTest.getClienteDto("Pepo", 12341234L);

        when(clienteDao.findCliente(pepoDto.getDni())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> modificarCliente.modificarCliente(pepoDto));

    }
}
