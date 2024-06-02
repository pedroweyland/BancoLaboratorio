package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import ar.edu.utn.frbb.tup.service.administracion.clientes.EliminarCliente;
import ar.edu.utn.frbb.tup.service.administracion.clientes.ModificarCliente;
import ar.edu.utn.frbb.tup.service.administracion.clientes.MostrarCliente;
import ar.edu.utn.frbb.tup.service.administracion.clientes.MostrarTodosClientes;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.springframework.stereotype.Component;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuCliente;

@Component
public class MenuClientes extends BasePresentation {
    ClienteInput clienteInput;
    ModificarCliente modificar;
    EliminarCliente eliminar;
    MostrarCliente mostrar;
    MostrarTodosClientes mostrarTodos;
    ClienteService clienteService;

    public MenuClientes(ClienteInput clienteInput, ModificarCliente modificar, EliminarCliente eliminar, MostrarCliente mostrar, MostrarTodosClientes mostrarTodos, ClienteService clienteService) {
        this.clienteInput = clienteInput;
        this.modificar = modificar;
        this.eliminar = eliminar;
        this.mostrar = mostrar;
        this.mostrarTodos = mostrarTodos;
        this.clienteService = clienteService;
    }

    //Funcion que administra los clientes del banco
    public void menuClientes() {
        boolean salir = false;

        while (!salir) {
            try {

                int opcion = menuCliente();

                if (opcion != 1 && opcion != 0) {
                    clienteService.findAllClientes();
                }

                switch (opcion) {
                    case 1:
                        clienteInput.ingresoCliente();
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
            } catch (ClientesVaciosException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
                System.out.println("Enter para seguir");
                scanner.nextLine();
            }
        }
    }
}
