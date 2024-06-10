package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuModificacion;

@Service
public class ModificarCliente extends BaseAdministracion {
    private final ClienteInput mod;
    private final ClienteDao clienteDao;


    public ModificarCliente(ClienteDao clienteDao, ClienteInput mod) {
        this.clienteDao = clienteDao;
        this.mod = mod;
    }

    // Modificacion Cliente
    public String modificarCliente(long dni, int opcion) throws ClienteNoEncontradoException {
        String modificacion = "----------------------------------------\n";
        Cliente cliente = clienteDao.findCliente(dni);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        //Elimino el cliente ya que va ser modificado
        clienteDao.deleteCliente(dni);

        //Switch para modifcar los datos del cliente
        switch (opcion) {
            case 1: //Nombre
                cliente.setNombre(mod.ingresarNombre());
                modificacion += "Nombre modificado correctamente\n";
                break;
            case 2: //Apellido
                cliente.setApellido(mod.ingresarApellido());
                modificacion += "Apellido modificado correctamente\n";
                break;
            case 3: //Direccion
                cliente.setDireccion(mod.ingresarDireccion());
                modificacion += "Direccion modificado correctamente\n";
                break;
            case 4: //Tipo de persona
                cliente.setTipoPersona(mod.ingresarTipoPersona());
                modificacion += "Tipo persona modificado correctamente\n";
                break;
            case 5: //Banco
                cliente.setBanco(mod.ingresarBanco());
                modificacion += "Banco modificado correctamente\n";
                break;
            case 6: //Mail
                cliente.setMail(mod.ingresarMail());
                modificacion += "Mail modificado correctamente\n";
                break;
        }
        //Guardo lo modificado
        clienteDao.saveCliente(cliente);
        modificacion += "----------------------------------------";
        return modificacion;
    }
}
