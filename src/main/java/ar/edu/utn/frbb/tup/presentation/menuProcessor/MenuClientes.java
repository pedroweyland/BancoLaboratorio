package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import ar.edu.utn.frbb.tup.service.administracion.clientes.*;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import org.springframework.stereotype.Component;
import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuCliente;
import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuModificacion;

@Component
public class MenuClientes extends BasePresentation {
    private final ClienteInput clienteInput;
    private final ClienteService clienteService;
    private final EliminarCliente eliminar;
    private final ModificarCliente mod;
    private final ar.edu.utn.frbb.tup.service.administracion.clientes.MostrarCliente mostrar;
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

                //Leo toda la lista de clientes, si no hay clientes lanza una excepcion, no lanza exepcion cuando el usuario pone 1 ya que la va a crear o 0 ya que va a salir
                if ((opcion != 1 && opcion != 0) && clienteService.findAllClientes().isEmpty()) {
                    throw new ClientesVaciosException("No hay clientes registrados");
                }

                switch (opcion) {
                    case 1: //Crear Cliente
                        clienteInput.ingresoCliente();
                        break;
                    case 2: //Modificar Cliente
                        dni = pedirDni("Escriba el DNI para el cliente que quiere modificar: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;

                        while (true) {
                            int opcionMod = menuModificacion();  //Usuario ingresa que quiere modificar
                            if (opcionMod == 0) break;
                            String modificacion = mod.modificarCliente(dni, opcionMod);

                            System.out.println(modificacion);
                        }
                        break;
                    case 3: //Eliminar Cliente
                        dni = pedirDni("Escriba el DNI para el cliente que quiere eliminar: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;

                        cliente = eliminar.eliminarCliente(dni);
                        if (cliente != null) System.out.println(toString(cliente, "------------ Cliente eliminado -----------"));

                        break;
                    case 4: //Mostrar Cliente
                        dni = pedirDni("Escriba el DNI para el cliente que quiere mostrar: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;

                        cliente = mostrar.mostrarCliente(dni);
                        if (cliente != null) System.out.println(toString(cliente, "------------ Muestra cliente -----------"));

                        break;
                    case 5: //Mostrar todos los Clientes
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
            } catch (ClientesVaciosException | ClienteExistenteException | ClienteNoEncontradoException e) {
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
