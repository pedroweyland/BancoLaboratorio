package ar.edu.utn.frbb.tup.administracion;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.CrearCuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.cuentas.MostrarCuenta;

import static ar.edu.utn.frbb.tup.menuBuilder.Menus.menuCuenta;

public class CuentaAdministracion {
    private boolean salir = false;

    public void cuentaAdministracion(Banco banco){
        while (!salir){
            int opcion = menuCuenta();

            switch (opcion){
                case 1:
                    CrearCuenta crear = new CrearCuenta();
                    crear.crearCuenta(banco);

                    break;
                case 2:
                    EliminarCuenta eliminar = new EliminarCuenta();
                    eliminar.eliminarCuenta(banco);

                    break;
                case 3:
                    MostrarCuenta mostrar = new MostrarCuenta();
                    mostrar.mostrarCuenta(banco);

                    break;
                case 0:
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
            }
        }
    }

}
