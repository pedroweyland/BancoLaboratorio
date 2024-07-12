package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ModificarCliente {
    private final ClienteDao clienteDao;

    public ModificarCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    //Modificacion Cliente
    public Cliente modificarCliente(ClienteDto clienteDto) throws ClienteNoEncontradoException, ClienteMenorDeEdadException {
        Cliente clienteModificado = new Cliente(clienteDto);

        //Si el usuario quiere modificar la fecha de nacimiento, verifico que sea mayor de edad
        if (clienteModificado.getFechaNacimiento() != null) esMayor(clienteModificado);

        //Busco al cliente que ingreso
        Cliente cliente = clienteDao.findCliente(clienteModificado.getDni());

        if (cliente == null) { //Si no existe lanza una excepcion
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + clienteModificado.getDni());
        }

        //Elimino el cliente ya que va ser modificado
        clienteDao.deleteCliente(clienteModificado.getDni());

        //Actualizo el Cliente
        actualizarCliente(cliente, clienteModificado);

        //Guardo lo modificado
        clienteDao.saveCliente(cliente);

        return cliente;
    }

    private void actualizarCliente(Cliente cliente, Cliente clienteModificado) {
        //Solo se modifica lo que el usuario escribio, si el usuario no especifica el nombre no se va a modificar
        if (clienteModificado.getNombre() != null) cliente.setNombre(clienteModificado.getNombre());
        //Apellido
        if (clienteModificado.getApellido() != null) cliente.setApellido(clienteModificado.getApellido());
        //Direccion
        if (clienteModificado.getDireccion() != null) cliente.setDireccion(clienteModificado.getDireccion());
        //Tipo de persona
        if (clienteModificado.getTipoPersona() != null) cliente.setTipoPersona(clienteModificado.getTipoPersona());
        //Banco
        if (clienteModificado.getBanco() != null) cliente.setBanco(clienteModificado.getBanco());
        //Mail
        if (clienteModificado.getMail() != null) cliente.setMail(clienteModificado.getMail());
        //Fecha de nacimiento
        if (clienteModificado.getFechaNacimiento() != null) cliente.setFechaNacimiento(clienteModificado.getFechaNacimiento());

    }

    private void esMayor(Cliente cliente) throws ClienteMenorDeEdadException {
        Period periodo = Period.between(cliente.getFechaNacimiento(), LocalDate.now());

        if (periodo.getYears() < 18){
            throw new ClienteMenorDeEdadException("Tiene que ser mayor de edad para ser cliente");
        }
    }
}
