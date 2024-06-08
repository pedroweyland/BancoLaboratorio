package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import ar.edu.utn.frbb.tup.service.administracion.clientes.*;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.springframework.stereotype.Component;
import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuCliente;

@Component
public class MenuClientes extends BasePresentation {
    private final ClienteInput clienteInput;
    private final ClienteService clienteService;
    private final EliminarCliente eliminar;
    private final ModificarCliente mod;
    private final MostrarCliente mostrar;
    private final MostrarTodosClientes mostrarTodos;

    public MenuClientes(ClienteInput clienteInput, ClienteService clienteService, EliminarCliente eliminarCliente, ModificarCliente modificarCliente, MostrarCliente mostrarCliente, MostrarTodosClientes mostrarTodosClientes) {
        this.clienteInput = clienteInput;
        this.clienteService = clienteService;
        this.eliminar = eliminarCliente;
        this.mod = modificarCliente;
        this.mostrar = mostrarCliente;
        this.mostrarTodos = mostrarTodosClientes;
    }

    //Funcion que administra los clientes del banco
    public void menuProcessor() {
        boolean salir = false;

        while (!salir) {
            try {
                long dni;
                Cliente cliente;

                int opcion = menuCliente();

                if (opcion != 1 && opcion != 0) {
                    clienteService.findAllClientes();
                }

                switch (opcion) {
                    case 1:
                        clienteInput.ingresoCliente();
                        break;
                    case 2:
                        dni = pedirDni("Escriba el DNI para el cliente que quiere modificar: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;
                        mod.modificarCliente(dni);

                        break;

                    case 3:
                        dni = pedirDni("Escriba el DNI para el cliente que quiere eliminar: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;

                        cliente = eliminar.eliminarCliente(dni);
                        if (cliente != null) System.out.println(toString(cliente, "------------ Cliente eliminado -----------"));

                        break;
                    case 4:
                        dni = pedirDni("Escriba el DNI para el cliente que quiere mostrar: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;

                        cliente = mostrar.mostrarCliente(dni);
                        if (cliente != null) System.out.println(toString(cliente, "------------ Muestra cliente -----------"));

                        break;
                    case 5:
                        clearScreen();
                        List<Cliente> clientes = mostrarTodos.mostrarTodosClientes();
                        int contador = 1;
                        for (Cliente c : clientes) {
                            System.out.println(toString(c, "------------ Cliente " + contador + " -----------"));
                            contador++;
                        }

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
            } finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
