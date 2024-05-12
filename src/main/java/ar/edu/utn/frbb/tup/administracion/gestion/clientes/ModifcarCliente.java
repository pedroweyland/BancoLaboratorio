package ar.edu.utn.frbb.tup.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.inputs.ClienteInput;
import java.util.List;

import static ar.edu.utn.frbb.tup.menubuilder.Menus.menuModificacion;


public class ModifcarCliente extends BaseGestion {

    // Modificacion Cliente
    public void ModifcarCliente(Banco banco){
        List<Cliente> clientes = banco.getClientes(); //Creo una lista auxiliar 'cliente' con la copia de Clientes que esta en banco ++Legibilidad

        long dni = pedirDni("Escriba el DNI para el cliente que quiere modificar: ");

        clearScreen();

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente cliente = encontrarCliente(clientes, dni);

        if (cliente == null) {
            System.out.println("No existe ningun cliente con el DNI ingresado");
        } else {

            boolean seguir = true;

            while (seguir) {
                int opcion = menuModificacion();  //Usuario ingresa que quiere modificar

                //Creo intancia de cliente Input para que el usuario modifique lo que eligio
                ClienteInput mod = new ClienteInput();

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
                        seguir = false;
                        break;
                }
                System.out.println(); //Espacion en blanco
            }
        }
    }

}
