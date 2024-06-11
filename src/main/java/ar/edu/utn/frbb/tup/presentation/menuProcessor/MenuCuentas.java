package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import ar.edu.utn.frbb.tup.presentation.input.CuentaInput;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.DarAltaBaja;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.MostrarCuenta;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import org.springframework.stereotype.Component;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuCuenta;

@Component
public class MenuCuentas extends BasePresentation {
    private final CuentaInput cuentaInput;
    private final EliminarCuenta eliminar;
    private final MostrarCuenta mostrar;
    private final DarAltaBaja altaBaja;
    private final ClienteService clienteService;
    private final CuentaService cuentaService;

    public MenuCuentas(CuentaInput cuentaInput, EliminarCuenta eliminar, MostrarCuenta mostrar, DarAltaBaja darAltaBaja, ClienteService clienteService, CuentaService cuentaService) {
        this.cuentaInput = cuentaInput;
        this.eliminar = eliminar;
        this.mostrar = mostrar;
        this.altaBaja = darAltaBaja;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;

    }

    //Funcion que administra las cuentas de los clientes
    public void menuProcessor() {
        boolean salir = false;

        while (!salir) {
            try {
                //Leo toda la lista de clientes, si no hay clientes lanza una excepcion ya que no se puede crear cuentas sin clientes
                if (clienteService.findAllClientes().isEmpty()) {
                    throw new ClientesVaciosException("No hay clientes registrados");
                }

                long dni, cvu;
                Cuenta cuenta;

                int opcion = menuCuenta();

                //Leo toda la lista de cuentas, si no hay cuentas lanza una excepcion, no lanza exepcion cuando el usuario pone 1 ya que la va a crear o 0 ya que va a salir
                if ((opcion != 1 && opcion != 0) && cuentaService.findAllCuentas().isEmpty()) {
                    throw new CuentasVaciasException("No hay cuentas registradas");
                }

                switch (opcion) {
                    case 1: //Creacion de cuenta
                        dni = pedirDni("Escriba el DNI del cliente para crearle una cuenta: (0 para salir)");
                        if (dni == 0) break;
                        cuentaInput.creacionCuenta(dni);

                        break;
                    case 2: //Eliminar Cuenta
                        dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");
                        clearScreen();
                        if (dni == 0) break;
                        cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                        clearScreen();
                        if (cvu == 0) break;

                        cuenta = eliminar.eliminarCuenta(dni, cvu);
                        if (cuenta != null)
                            System.out.println(toString(cuenta, "------------ Cuenta eliminada -----------"));
                        break;

                    case 3: //Mostrar Cuenta
                        dni = pedirDni("Escriba el DNI del cliente para ver sus cuentas: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;

                        List<Cuenta> cuentas = mostrar.mostrarCuenta(dni);

                        if (cuentas != null) {
                            for (Cuenta c : cuentas) {
                                System.out.println(toString(c, "------------ Cuenta -----------"));
                            }
                        }

                        break;
                    case 4: //Dar Alta/Baja Cuenta
                        dni = pedirDni("Escriba el DNI del cliente para dar de Alta/Baja: (0 para salir)");
                        clearScreen();
                        if (dni == 0) break;
                        cvu = pedirCvu("Escriba el CVU de la cuenta que quiere dar de Alta/Baja: (0 para salir) ");
                        clearScreen();
                        if (cvu == 0) break;

                        boolean opc = pedirOpcion("Escriba (B) si quiere dar de Baja o (A) si quere dar de Alta");
                        clearScreen();
                        altaBaja.gestionarEstado(dni, cvu, opc);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                }

            } catch(ClientesVaciosException | CuentasVaciasException | ClienteNoEncontradoException |
                    CuentaNoEncontradaException ex){
                System.out.println("----------------------------------------");
                System.out.println(ex.getMessage());
                System.out.println("----------------------------------------");
                salir = true;

            } finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }

}
