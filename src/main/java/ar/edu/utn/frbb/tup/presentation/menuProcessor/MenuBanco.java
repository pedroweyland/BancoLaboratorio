package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import ar.edu.utn.frbb.tup.service.handler.MovimientoService;
import org.springframework.stereotype.Component;

@Component
public class MenuBanco extends BasePresentation {
    private final MenuClientes menuClientes;
    private final MenuCuentas menuCuentas;
    private final MenuOperaciones menuOperaciones;
    private final ClienteService clienteService;
    private final CuentaService cuentaService;
    private final MovimientoService movimientoService;

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
                    menuClientes.menuProcessor();
                    break;
                case 2:
                    menuCuentas.menuProcessor();
                    break;
                case 3:
                    menuOperaciones.menuProcessor();
                    break;
                case 0:
                    salir = true;
                    break;
            }
            clearScreen();
        }
    }


}
