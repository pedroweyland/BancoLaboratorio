package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;


import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;

import java.util.List;

public class CrearCliente extends BaseGestion {
    ClienteInput clienteInput = new ClienteInput();

    // Creacion Cliente
    public void crearCliente() {
        boolean existe = false;

        //Usuario ingresa los datos y se guarda en la variable cliente
        Cliente cliente = clienteInput.ingresoCliente();

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente c = clienteDao.findCliente(cliente.getDni());

        if (c == null){ //Si no existe el cliente lo agrego al banco y lo muestro
            clienteDao.saveCliente(cliente); //Guardo el cliente en el archivo

            //Muestro en pantalla el resultado
            System.out.println("------- Cliente creado con exito -------");
            System.out.println(toString(cliente));

        } else {
            System.out.println("El cliente ya existe");
            System.out.println(c.getCuentas());
            System.out.println(c.getCuentas().toString());
        }


        System.out.println("Enter para seguir");
        scanner.nextLine();
        clearScreen();
    }

}
