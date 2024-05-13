package ar.edu.utn.frbb.tup.banco;

import ar.edu.utn.frbb.tup.administracion.ClienteAdministracion;
import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.administracion.CuentaAdministracion;
import ar.edu.utn.frbb.tup.inputs.BaseInput;
import ar.edu.utn.frbb.tup.operaciones.Operaciones;

import static ar.edu.utn.frbb.tup.menubuilder.Menus.menuPrincipal;


public class MenuBanco extends BaseInput {

    private boolean salir = false;


    public void menuInicio(Banco banco){
        //Inicio del banco
        while(!salir){
            //Usuario decida 
            int opcion = menuPrincipal();
            switch (opcion) {
                case 1:
                    ClienteAdministracion cliente = new ClienteAdministracion();
                    cliente.clienteAdministracion(banco);
                    break;
                case 2:
                    CuentaAdministracion cuenta = new CuentaAdministracion();
                    cuenta.cuentaAdministracion(banco);
                    break;
                case 3:
                    Operaciones operaciones = new Operaciones();
                    operaciones.operaciones(banco);
                    break;
                case 0:
                    salir = true;
                    break;
            }
            clearScreen();
        }
    }

}
