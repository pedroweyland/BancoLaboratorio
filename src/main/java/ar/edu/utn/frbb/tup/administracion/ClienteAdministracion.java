package ar.edu.utn.frbb.tup.administracion;


import ar.edu.utn.frbb.tup.administracion.gestion.clientes.*;
import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;

import java.util.List;

import static ar.edu.utn.frbb.tup.menubuilder.Menus.menuCliente;

public class ClienteAdministracion {
    private boolean salir = false;

    //Funcion que administra los clientes del banco
    public void clienteAdministracion(Banco banco) {
        List<Cliente> clientes = banco.getClientes();

        while (!salir) {
            int opcion = menuCliente();

            switch (opcion) {
                case 1:
                    CrearCliente crear = new CrearCliente();
                    crear.crearCliente(clientes);
                    break;
                case 2:
                    ModificarCliente mod = new ModificarCliente();
                    mod.modificarCliente(clientes);
                    break;
                case 3:
                    EliminarCliente eliminar = new EliminarCliente();
                    eliminar.eliminarCliente(clientes);
                    break;
                case 4:
                    MostrarCliente mostrar = new MostrarCliente();
                    mostrar.mostrarCliente(clientes);
                    break;
                case 5:
                    MostrarTodosClientes mostrarTodos = new MostrarTodosClientes();
                    mostrarTodos.mostrarTodosClientes(clientes);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
            }
        }

    }
}


