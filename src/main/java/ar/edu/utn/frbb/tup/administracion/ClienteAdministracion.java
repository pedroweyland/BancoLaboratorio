package ar.edu.utn.frbb.tup.administracion;


import ar.edu.utn.frbb.tup.administracion.gestion.clientes.*;
import ar.edu.utn.frbb.tup.entidades.Banco;

import static ar.edu.utn.frbb.tup.menuBuilder.Menus.menuCliente;

public class ClienteAdministracion {
    private boolean salir = false;

    public void clienteAdministracion(Banco banco) {
        while (!salir) {
            int opcion = menuCliente();
            switch (opcion) {
                case 1:
                    CrearCliente crear = new CrearCliente();
                    crear.crearCliente(banco);
                    break;
                case 2:
                    ModifcarCliente mod = new ModifcarCliente();
                    mod.ModifcarCliente(banco);
                    break;
                case 3:
                    EliminarCliente eliminar = new EliminarCliente();
                    eliminar.eliminarCliente(banco);
                    break;
                case 4:
                    MostrarCliente mostrar = new MostrarCliente();
                    mostrar.mostrarCliente(banco);
                    break;
                case 5:
                    MostrarTodosClientes mostrarTodos = new MostrarTodosClientes();
                    mostrarTodos.mostrarTodosClientes(banco);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
            }
        }

    }
}


