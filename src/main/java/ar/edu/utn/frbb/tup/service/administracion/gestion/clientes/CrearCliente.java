package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;

public class CrearCliente extends BaseGestion {
    ClienteInput clienteInput = new ClienteInput();

    // Creacion Cliente
    public void crearCliente() throws ClienteExistenteException {
        boolean existe = false;

        //Usuario ingresa los datos y se guarda en la variable cliente
        Cliente cliente = clienteInput.ingresoCliente();

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente c = clienteDao.findCliente(cliente.getDni());

        try {

            if (c != null) { //Si existe el cliente lanzo una excepcion
                throw new ClienteExistenteException("El cliente ya existe");
            }

            clienteDao.saveCliente(cliente); //Guardo el cliente en el archivo

            //Muestro en pantalla el resultado
            System.out.println("------- Cliente creado con exito -------");
            System.out.println(toString(cliente));

        } catch (ClienteExistenteException e) { //Agarro la excepcion de que ya existe el cliente
            System.out.println("----------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("----------------------------------------");
        } finally {
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }
    }}
