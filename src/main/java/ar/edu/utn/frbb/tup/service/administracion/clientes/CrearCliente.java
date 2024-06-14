package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteFechaDeAltaInvalidaException;
import ar.edu.utn.frbb.tup.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CrearCliente extends BaseAdministracion {
    private final ClienteDao clienteDao;


    public CrearCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente crearCliente(Cliente cliente) throws ClienteExistenteException, ClienteMenorDeEdadException, ClienteFechaDeAltaInvalidaException {

        if (clienteDao.findCliente(cliente.getDni()) != null){
            throw new ClienteExistenteException("Ya existe un cliente con el DNI ingresado");
        }

        //Verifico que el cliente sea mayor de edad
        esMayor(cliente);

        //Verifico que la fecha de alta sea valida
        esFechaAltaValida(cliente.getFechaAlta(), cliente.getFechaNacimiento());

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

    private void esFechaAltaValida(LocalDate fechaAlta, LocalDate fechaNacimiento) throws ClienteMenorDeEdadException, ClienteFechaDeAltaInvalidaException {
        Period period = Period.between(fechaNacimiento, fechaAlta);

        if (fechaNacimiento.isBefore(fechaAlta)) { //Valido que la fehca de alta no sea antes que la fecha de nacimiento

            if (fechaAlta.isBefore(LocalDate.now())) { //Valido que la fecha de alta no sea despues de la fecha actual

                if (period.getYears() < 18) { //Valido que la fecha de alta no sea menor a 18 años
                    throw new ClienteFechaDeAltaInvalidaException("Tiene que haber minimo una diferencia de 18 anios entre la fecha de alta y nacimiento, (Formato: YYYY-MM-DD)");
                }

            } else {
                throw new ClienteFechaDeAltaInvalidaException("La fecha de alta no puede ser despues de la fecha actual, (Formato: YYYY-MM-DD)");
            }

        } else {
            throw new ClienteFechaDeAltaInvalidaException("La fecha de alta no puede ser antes que la fecha de nacimiento, (Formato: YYYY-MM-DD)");
        }
    }
}
