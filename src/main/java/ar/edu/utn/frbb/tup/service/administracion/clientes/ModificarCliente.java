package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.BasePresentation.pedirDni;
import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuModificacion;


@Service
public class ModificarCliente extends BaseAdministracion {
    ClienteInput mod;
    ClienteDao clienteDao;


    public ModificarCliente(ClienteDao clienteDao, ClienteInput mod) {
        this.clienteDao = clienteDao;
        this.mod = mod;
    }

    // Modificacion Cliente
    public void modificarCliente() {
        boolean salir = false;

        while (!salir) {
            long dni = pedirDni("Escriba el DNI para el cliente que quiere modificar: (0 para salir)");

            clearScreen();

            if (dni == 0) break;

            try {

                //Elimino el cliente con el DNI ingresado, si no existe el cliente lanza una excepcion,
                //Esta misma tambien retorna el cliente que fue eliminado para poder modificar
                Cliente cliente = clienteDao.deleteCliente(dni);

                while (!salir) {

                    int opcion = menuModificacion();  //Usuario ingresa que quiere modificar

                    //Switch para modifcar los datos del cliente
                    switch (opcion) {
                        case 1: //Nombre
                            cliente.setNombre(mod.ingresarNombre());
                            System.out.println("Nombre modificado correctamente");
                            break;
                        case 2: //Apellido
                            cliente.setApellido(mod.ingresarApellido());
                            System.out.println("Apellido modificado correctamente");
                            break;
                        case 3: //Direccion
                            cliente.setDireccion(mod.ingresarDireccion());
                            System.out.println("Direccion modificado correctamente");
                            break;
                        case 4: //Tipo de persona
                            cliente.setTipoPersona(mod.ingresarTipoPersona());
                            System.out.println("Tipo persona modificado correctamente");
                            break;
                        case 5: //Banco
                            cliente.setBanco(mod.ingresarBanco());
                            System.out.println("Banco modificado correctamente");
                            break;
                        case 6: //Mail
                            cliente.setMail(mod.ingresarMail());
                            System.out.println("Mail modificado correctamente");
                            break;
                        case 0:
                            //Guardo el cliente modificado en el archivo
                            clienteDao.saveCliente(cliente);
                            salir = true;
                            break;
                    }
                    if (opcion != 0){
                        System.out.println("----------------------------------------");
                        System.out.println("Enter para seguir");
                        scanner.nextLine();
                        clearScreen();
                    }
                }
            } catch (ClienteNoEncontradoException | ClienteExistenteException ex) {
                System.out.println("----------------------------------------");
                System.out.println(ex.getMessage());
                System.out.println("----------------------------------------");
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
