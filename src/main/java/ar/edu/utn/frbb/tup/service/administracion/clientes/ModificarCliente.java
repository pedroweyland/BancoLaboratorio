package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import org.springframework.stereotype.Service;

@Service
public class ModificarCliente extends BaseAdministracion {
    private final ClienteDao clienteDao;

    public ModificarCliente(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    // Modificacion Cliente
    public Cliente modificarCliente(long dni, Cliente clienteModificado) throws ClienteNoEncontradoException {
        //Busco al cliente que ingreso
        Cliente cliente = clienteDao.findCliente(dni);

        if (cliente == null) { //Si no existe lanza una excepcion
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        //Elimino el cliente ya que va ser modificado
        clienteDao.deleteCliente(dni);
        //Actualizo el Cliente
        actualizarCliente(cliente, clienteModificado);

        //Guardo lo modificado
        clienteDao.saveCliente(cliente);

        return cliente;
    }

    private void actualizarCliente(Cliente cliente, Cliente clienteModificado) {
        //Solo se modifica lo que el usuario escribio, si el usuario no especifica el nombre no se va a modificar
        if (clienteModificado.getNombre() != null) { //Nombre
            cliente.setNombre(clienteModificado.getNombre());
        }
        if (clienteModificado.getApellido() != null) { //Apellido
            cliente.setApellido(clienteModificado.getApellido());
        }
        if (clienteModificado.getDireccion() != null) { //Direccion
            cliente.setDireccion(clienteModificado.getDireccion());
        }
        if (clienteModificado.getTipoPersona() != null) { //Tipo de persona
            cliente.setTipoPersona(clienteModificado.getTipoPersona());
        }
        if (clienteModificado.getBanco() != null) { //Banco
            cliente.setBanco(clienteModificado.getBanco());
        }
        if (clienteModificado.getMail() != null) { //Mail
            cliente.setMail(clienteModificado.getMail());
        }
    }
}
