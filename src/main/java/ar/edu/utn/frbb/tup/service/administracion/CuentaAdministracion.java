package ar.edu.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.DarAltaBaja;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.CrearCuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.MostrarCuenta;
import ar.edu.utn.frbb.tup.model.Cliente;


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

        List<Cliente> clientes = clienteDao.findAllClientes();

        if (!clientes.isEmpty()) {
            while (!salir) {
                List<Cuenta> cuentas = cuentaDao.findAllCuentas();
                int opcion = menuCuenta();

                //Si NO hay clientes o Cuentas vuelve el error, Excepto que el usuario ingrese '1' (crear cuenta) o '0' (Salir del menu)
                if (clientes.isEmpty() && opcion != 1 && opcion != 0) {
                    System.out.println("No hay clientes registrados");
                } else {
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
            }
        } else {
            System.out.println("----------------------------------------");
            System.out.println("No hay clientes registrados");
            System.out.println("----------------------------------------");
            System.out.println("Enter para seguir");
            scanner.nextLine();
        }
    }

}
