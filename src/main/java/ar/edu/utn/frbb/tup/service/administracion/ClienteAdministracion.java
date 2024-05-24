package ar.edu.utn.frbb.tup.service.administracion;


import ar.edu.utn.frbb.tup.model.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.clientes.*;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuCliente;

public class ClienteAdministracion {
    private boolean salir = false;

    //Funcion que administra los clientes del banco
    public void clienteAdministracion() {

        while (!salir) {
            int opcion = menuCliente();

            switch (opcion) {
                case 1:
                    CrearCliente crear = new CrearCliente();
                    crear.crearCliente();
                    break;
                case 2:
                    ModificarCliente mod = new ModificarCliente();
                    mod.modificarCliente();
                    break;
                case 3:
                    EliminarCliente eliminar = new EliminarCliente();
                    eliminar.eliminarCliente();
                    break;
                case 4:
                    MostrarCliente mostrar = new MostrarCliente();
                    mostrar.mostrarCliente();
                    break;
                case 5:
                    MostrarTodosClientes mostrarTodos = new MostrarTodosClientes();
                    mostrarTodos.mostrarTodosClientes();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
            }
        }

    }
}


