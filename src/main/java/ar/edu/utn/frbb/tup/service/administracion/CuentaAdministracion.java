package ar.edu.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.CuentasDeClientesDao;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.DarAltaBaja;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.CrearCuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas.MostrarCuenta;
import ar.edu.utn.frbb.tup.model.Cliente;


import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuCuenta;

public class CuentaAdministracion {
    private boolean salir = false;
    ClienteDao clienteDao = new ClienteDao();

    //Funcion que administra las cuentas de los clientes
    public void cuentaAdministracion(){

        List<Cliente> clientes = clienteDao.findAll();

        while (!salir){
            int opcion = menuCuenta();

            switch (opcion){
                case 1:
                    CrearCuenta crear = new CrearCuenta();
                    crear.crearCuenta(clientes);
                    break;
                case 2:
                    EliminarCuenta eliminar = new EliminarCuenta();
                    eliminar.eliminarCuenta(clientes);
                    break;
                case 3:
                    MostrarCuenta mostrar = new MostrarCuenta();
                    mostrar.mostrarCuenta(clientes);
                    break;
                case 4:
                    DarAltaBaja altaBaja = new DarAltaBaja();
                    altaBaja.gestionarEstado(clientes);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
            }
        }
    }

}
