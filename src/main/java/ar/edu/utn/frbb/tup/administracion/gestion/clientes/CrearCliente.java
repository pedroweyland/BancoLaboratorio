package ar.edu.utn.frbb.tup.administracion.gestion.clientes;


import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.inputs.ClienteInput;

public class CrearCliente extends BaseGestion {
    ClienteInput clienteInput = new ClienteInput();

    // Creacion Cliente
    public void crearCliente(Banco banco) {
        boolean existe = false;

        //Usuario ingresa los datos y se guarda en la variable cliente
        Cliente cliente = clienteInput.ingresoCliente();

        //Valido si el cliente ya existe por el dni que ingreso anteriormente
        for (Cliente c : banco.getClientes()) {
            if (c.getDni() == cliente.getDni()) {
                System.out.println("El cliente ya existe");
                existe = true;
                break;
            }
        }

        if (!existe){ //Si no existe el cliente lo agrego al banco y lo muestro
            banco.getClientes().add(cliente);
            System.out.println("------- Cliente creado con exito -------");

            System.out.println(toString(cliente)); //Muestro el cliente
        }

    }

}
