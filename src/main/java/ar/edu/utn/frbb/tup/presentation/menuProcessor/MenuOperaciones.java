package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import ar.edu.utn.frbb.tup.service.operaciones.Operaciones;
import ar.edu.utn.frbb.tup.service.operaciones.modulos.*;
import org.springframework.stereotype.Component;

import static ar.edu.utn.frbb.tup.presentation.menuProcessor.Menus.menuOperaciones;

@Component
public class MenuOperaciones extends BasePresentation {
    Deposito deposito;
    Retiro retiro;
    Transferencia transferencia;
    Consulta consulta;
    MostrarMovimientos movimientos;
    ClienteService clienteService;
    CuentaService cuentaService;
    Operaciones operaciones;

    public MenuOperaciones(Deposito deposito, Retiro retiro, Transferencia transferencia, Consulta consulta, MostrarMovimientos movimientos, ClienteService clienteService, CuentaService cuentaService, Operaciones operaciones) {
        this.deposito = deposito;
        this.retiro = retiro;
        this.transferencia = transferencia;
        this.consulta = consulta;
        this.movimientos = movimientos;
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.operaciones = operaciones;
    }

    public void menuOperacion() {
        boolean seguir = true;

        try {
            clienteService.findAllClientes(); //Si hay clientes entonces se puede operar, si no hay vuelve excepcion
            cuentaService.findAllCuentas(); //Si hay cuentas entonces se puede operar, si no hay vuelve excepcion
            long dni, cvu;

            clearScreen();
            dni = pedirDni("Para realizar una operacion ingrese el DNI del cliente: (0 para salir)");
            clearScreen();
            cvu = pedirCvu("Para realizar una operacion ingrese el CVU de la cuenta: (0 para salir)");
            clearScreen();

            Cuenta cuenta = operaciones.cuentaOperar(dni, cvu);

            if (cuenta == null) { // Si cuenta es null, significa que el usuario decidio irse
                System.out.println("Saliendo...");
            } else {
                while (seguir) {

                    int opcion = menuOperaciones();

                    switch (opcion) {
                        case 1:
                            //Deposito
                            deposito.deposito(cuenta);
                            break;
                        case 2:
                            //Retirar dinero
                            retiro.retiro(cuenta);
                            break;
                        case 3:
                            //Transferencia
                            long cvuDestino = pedirCvu("Ingrese el CVU de la cuenta a transferir: (0 para salir)");
                            //Usuario ingresa a quien quiere transferir, vuelve una excepcion si la cuenta no fue encontrada
                            Cuenta cuentaDestino = operaciones.cuentaATransferir(cvuDestino);

                            transferencia.transferencia(cuenta, cuentaDestino);
                            break;
                        case 4:
                            consulta.consulta(cuenta);
                            break;
                        case 5:
                            movimientos.mostrarMovimientos(cuenta);
                            break;
                        case 0:
                            seguir = false;
                            break;
                    }
                }
            }
        } catch (ClientesVaciosException | CuentasVaciasException | CuentaNoEncontradaException |
                 ClienteNoEncontradoException | CuentaEstaDeBajaException ex){
            System.out.println("----------------------------------------");
            System.out.println(ex.getMessage());
            System.out.println("----------------------------------------");
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }
    }

}
