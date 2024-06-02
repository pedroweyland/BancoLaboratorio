package ar.edu.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.service.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.*;
import ar.edu.utn.frbb.tup.service.exception.CuentasVaciasException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuCuenta;

@Component
public class CuentaAdministracion {
    CrearCuenta crear;
    DarAltaBaja altaBaja;
    EliminarCuenta eliminar;
    MostrarCuenta mostrar;
    ClienteDao clienteDao;
    CuentaDao cuentaDao;
    Scanner scanner = new Scanner(System.in);

    public CuentaAdministracion(CrearCuenta crear, DarAltaBaja altaBaja, EliminarCuenta eliminar, MostrarCuenta mostrar, ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.crear = crear;
        this.altaBaja = altaBaja;
        this.eliminar = eliminar;
        this.mostrar = mostrar;
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    //Funcion que administra las cuentas de los clientes
    public void cuentaAdministracion() {
        boolean salir = false;
        try {
            //Leo toda la lista de clientes, si no hay clientes lanza una excepcion ya que no se puede crear cuentas sin clientes
            clienteDao.findAllClientes();

            while (!salir) {
                int opcion = menuCuenta();

                if (opcion != 1 && opcion != 0) {
                    //Leo toda la lista de cuentas, si no hay cuentas lanza una excepcion, no lanza exepcion cuando el usuario pone 0 ya que la va a crear
                    cuentaDao.findAllCuentas();
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
