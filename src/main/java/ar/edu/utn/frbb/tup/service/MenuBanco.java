package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.CuentasDeClientesDao;
import ar.edu.utn.frbb.tup.service.administracion.ClienteAdministracion;
import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.service.administracion.CuentaAdministracion;
import ar.edu.utn.frbb.tup.presentation.input.BaseInput;
import ar.edu.utn.frbb.tup.service.operaciones.Operaciones;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuPrincipal;


public class MenuBanco extends BaseInput {

    private boolean salir = false;


    public void menuInicio(Banco banco){
        //Inicio del banco

        //Inicializo los archivos de cliente, cuentas y las relaciones que hay entre estas mismas
        ClienteDao clienteDao = new ClienteDao();
        clienteDao.inicializarClientes();

        CuentaDao cuentaDao = new CuentaDao();
        cuentaDao.inicializarCuentas();

        CuentasDeClientesDao cuentasDeClientes = new CuentasDeClientesDao();
        cuentasDeClientes.inicializarRelaciones();

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
                    cuenta.cuentaAdministracion();
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
