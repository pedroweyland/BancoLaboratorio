package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteExistenteException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CrearCliente {
    private final ClienteDao clienteDao;


    public CrearCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente crearCliente(ClienteDto clienteDto) throws ClienteExistenteException, ClienteMenorDeEdadException {
        Cliente cliente = new Cliente(clienteDto);

        Cliente clienteEncontrado = clienteDao.findCliente(cliente.getDni());
        if (clienteEncontrado != null){
            throw new ClienteExistenteException("Ya existe un cliente con el DNI ingresado");
        }

        //Verifico que el cliente sea mayor de edad
        esMayor(cliente);

        //Guardo el cliente ingresado, si ya existe se lanza la excepcion por ende no se guarda y el catch agarra la excepcion
        clienteDao.saveCliente(cliente);

        return cliente;

    }

    private void esMayor(Cliente cliente) throws ClienteMenorDeEdadException {
        Period periodo = Period.between(cliente.getFechaNacimiento(), LocalDate.now());

        if (periodo.getYears() < 18){
            throw new ClienteMenorDeEdadException("Tiene que ser mayor de edad para ser cliente");
        }
    }

}
