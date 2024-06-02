package ar.edu.utn.frbb.tup.service.administracion;


import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
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
    ClienteInput clienteInput;


    public ClienteAdministracion(CrearCliente crear, ModificarCliente modificar, EliminarCliente eliminar, MostrarCliente mostrar, MostrarTodosClientes mostrarTodos, ClienteInput clienteInput) {
        this.crear = crear;
        this.modificar = modificar;
        this.eliminar = eliminar;
        this.mostrar = mostrar;
        this.mostrarTodos = mostrarTodos;
        this.clienteInput = clienteInput;
    }

    //Funcion que administra los clientes del banco
    public void clienteAdministracion() {
        boolean salir = false;

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