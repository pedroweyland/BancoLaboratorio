package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.CrearCuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.DarAltaBaja;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.MostrarCuenta;
import ar.edu.utn.frbb.tup.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import org.springframework.stereotype.Component;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuCuenta;

@Component
public class MenuCuentas extends BasePresentation {
    CrearCuenta crear;
    EliminarCuenta eliminar;
    MostrarCuenta mostrar;
    DarAltaBaja altaBaja;
    ClienteService clienteService;
    CuentaService cuentaService;

    public MenuCuentas(CrearCuenta crear, EliminarCuenta eliminar, MostrarCuenta mostrar, DarAltaBaja darAltaBaja, ClienteService clienteService, CuentaService cuentaService) {
        this.crear = crear;
        this.eliminar = eliminar;
        this.mostrar = mostrar;
        this.altaBaja = darAltaBaja;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;

    }

    //Funcion que administra las cuentas de los clientes
    public void menuCuentas() {
        boolean salir = false;
        try {
            //Leo toda la lista de clientes, si no hay clientes lanza una excepcion ya que no se puede crear cuentas sin clientes
            clienteService.findAllClientes();

            while (!salir) {
                int opcion = menuCuenta();

                if (opcion != 1 && opcion != 0) {
                    //Leo toda la lista de cuentas, si no hay cuentas lanza una excepcion, no lanza exepcion cuando el usuario pone 1 ya que la va a crear o 0 ya que va a salir
                    cuentaService.findAllCuentas();
                }

                switch (opcion) {
                    case 1:
                        crear.crearCuenta();
                        break;
                    case 2:
                        eliminar.eliminarCuenta();
                        break;
                    case 3:
                        mostrar.mostrarCuenta();
                        break;
                    case 4:
                        altaBaja.gestionarEstado();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        salir = true;
                        break;
                }
            }
        } catch (ClientesVaciosException | CuentasVaciasException ex){
            System.out.println("----------------------------------------");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------");
            System.out.println("Enter para seguir");
            scanner.nextLine();
        }
    }
}
