package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import ar.edu.utn.frbb.tup.service.handler.MovimientoService;
import org.springframework.stereotype.Component;

@Component
public class MenuBanco extends BasePresentation {
    MenuClientes menuClientes;
    MenuCuentas menuCuentas;
    MenuOperaciones menuOperaciones;
    ClienteService clienteService;
    CuentaService cuentaService;
    MovimientoService movimientoService;

    public MenuBanco(MenuClientes menuClientes, MenuCuentas menuCuentas, MenuOperaciones menuOperaciones, ClienteService clienteService, CuentaService cuentaService, MovimientoService movimientoService) {
        this.menuClientes = menuClientes;
        this.menuCuentas = menuCuentas;
        this.menuOperaciones = menuOperaciones;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.movimientoService = movimientoService;
    }

    public void menuInicio(){
        //Inicio del banco
        boolean salir = false;

        //Inicializo los archivos de cliente, cuentas, las relaciones que hay entre estas mismas y los movimientos

        clienteService.inicializarClientes();
        cuentaService.inicializarCuentas();
        movimientoService.inicializarMovimientos();

        while(!salir){
            clearScreen();
            //Usuario decide
            int opcion = Menus.menuPrincipal();

            switch (opcion) {
                case 1:
                    menuClientes.menuClientes();
                    break;
                case 2:
                    menuCuentas.menuCuentas();
                    break;
                case 3:
                    //operaciones.operaciones();
                    menuOperaciones.menuOperacion();
                    break;
                case 0:
                    salir = true;
                    break;
            }
            clearScreen();
        }
    }


}
