package ar.edu.utn.frbb.tup.administracion;

import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.DarAltaBaja;
import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.CrearCuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.MostrarCuenta;
import ar.edu.utn.frbb.tup.entidades.Cliente;

import java.util.List;

import static ar.edu.utn.frbb.tup.menubuilder.Menus.menuCuenta;

public class CuentaAdministracion {
    private boolean salir = false;

    //Funcion que administra las cuentas de los clientes
    public void cuentaAdministracion(Banco banco){

        List<Cliente> clientes = banco.getClientes(); //Guardo los clientes del banco en la lista 'clientes'

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
