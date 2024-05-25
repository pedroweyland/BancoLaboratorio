package ar.edu.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.exception.ClientesVaciosException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.*;

import java.util.List;
import java.util.Scanner;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuCuenta;

public class CuentaAdministracion {
    private boolean salir = false;
    ClienteDao clienteDao = new ClienteDao();
    CuentaDao cuentaDao = new CuentaDao();
    Scanner scanner = new Scanner(System.in);

    //Funcion que administra las cuentas de los clientes
    public void cuentaAdministracion() {

        try {
            //Leo toda la lista de clientes, si no hay clientes lanza una excepcion ya que no se puede crear cuentas sin clientes
            clienteDao.findAllClientes();

            while (!salir) {
                List<Cuenta> cuentas = cuentaDao.findAllCuentas();
                int opcion = menuCuenta();

                if (cuentas.isEmpty() && opcion != 1 && opcion != 0) {
                    System.out.println("No hay Cuentas registradas");
                } else {

                    switch (opcion) {
                        case 1:
                            CrearCuenta crear = new CrearCuenta();
                            crear.crearCuenta();
                            break;
                        case 2:
                            EliminarCuenta eliminar = new EliminarCuenta();
                            eliminar.eliminarCuenta();
                            break;
                        case 3:
                            MostrarCuenta mostrar = new MostrarCuenta();
                            mostrar.mostrarCuenta();
                            break;
                        case 4:
                            DarAltaBaja altaBaja = new DarAltaBaja();
                            altaBaja.gestionarEstado();
                            break;
                        case 0:
                            System.out.println("Saliendo...");
                            salir = true;
                            break;
                    }
                }
            }
        } catch (ClientesVaciosException ex){
            System.out.println("----------------------------------------");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------");
            System.out.println("Enter para seguir");
            scanner.nextLine();
        }
    }

}
