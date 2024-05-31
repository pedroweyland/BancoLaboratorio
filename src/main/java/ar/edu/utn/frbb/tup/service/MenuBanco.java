package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import ar.edu.utn.frbb.tup.service.administracion.ClienteAdministracion;
import ar.edu.utn.frbb.tup.service.administracion.CuentaAdministracion;
import ar.edu.utn.frbb.tup.presentation.input.BaseInput;
import ar.edu.utn.frbb.tup.service.operaciones.Operaciones;
import org.springframework.stereotype.Component;

import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuPrincipal;

@Component
public class MenuBanco extends BaseInput {

    ClienteAdministracion cliente;
    CuentaAdministracion cuenta;
    Operaciones operaciones;
    ClienteDao clienteDao;
    CuentaDao cuentaDao;
    MovimientosDao movimientosDao;

    private boolean salir = false;

    public MenuBanco(ClienteAdministracion cliente, CuentaAdministracion cuenta, Operaciones operaciones, ClienteDao clienteDao, CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.cliente = cliente;
        this.cuenta = cuenta;
        this.operaciones = operaciones;
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    public void menuInicio(){
        //Inicio del banco

        //Inicializo los archivos de cliente, cuentas, las relaciones que hay entre estas mismas y los movimientos
        clienteDao.inicializarClientes();
        cuentaDao.inicializarCuentas();
        movimientosDao.inicializarMovimientos();

        while(!salir){
            //Usuario decide
            int opcion = menuPrincipal();
            switch (opcion) {
                case 1:
                    cliente.clienteAdministracion();
                    break;
                case 2:
                    cuenta.cuentaAdministracion();
                    break;
                case 3:
                    operaciones.operaciones();
                    break;
                case 0:
                    salir = true;
                    break;
            }
            clearScreen();
        }
    }

}
