package ar.edu.utn.frbb.tup.service.administracion;


import ar.edu.utn.frbb.tup.service.administracion.gestion.clientes.*;
import org.springframework.stereotype.Component;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuCliente;

@Component
public class ClienteAdministracion {
    CrearCliente crear;
    ModificarCliente modificar;
    EliminarCliente eliminar;
    MostrarCliente mostrar;
    MostrarTodosClientes mostrarTodos;

    private boolean salir = false;

    public ClienteAdministracion(CrearCliente crear, ModificarCliente modificar, EliminarCliente eliminar, MostrarCliente mostrar, MostrarTodosClientes mostrarTodos) {
        this.crear = crear;
        this.modificar = modificar;
        this.eliminar = eliminar;
        this.mostrar = mostrar;
        this.mostrarTodos = mostrarTodos;
    }

    //Funcion que administra los clientes del banco
    public void clienteAdministracion() {

        while (!salir) {
            int opcion = menuCliente();

            switch (opcion) {
                case 1:
                    crear.crearCliente();
                    break;
                case 2:
                    modificar.modificarCliente();
                    break;
                case 3:
                    eliminar.eliminarCliente();
                    break;
                case 4:
                    mostrar.mostrarCliente();
                    break;
                case 5:
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


