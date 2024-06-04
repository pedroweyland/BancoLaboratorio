package ar.edu.utn.frbb.tup.presentation.menuProcessor;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.presentation.BasePresentation;
import ar.edu.utn.frbb.tup.service.handler.ClienteService;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import ar.edu.utn.frbb.tup.service.operaciones.Operaciones;
import ar.edu.utn.frbb.tup.service.operaciones.modulos.*;
import org.springframework.stereotype.Component;

import java.util.List;

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

        while (seguir) {
            try {
                clienteService.findAllClientes(); //Si hay clientes entonces se puede operar, si no hay vuelve excepcion
                cuentaService.findAllCuentas(); //Si hay cuentas entonces se puede operar, si no hay vuelve excepcion
                long dni, cvu;

                clearScreen();
                dni = pedirDni("Para realizar una operacion ingrese el DNI del cliente: (0 para salir)");
                clearScreen();
                if (dni == 0) break;

                cvu = pedirCvu("Para realizar una operacion ingrese el CVU de la cuenta: (0 para salir)");
                clearScreen();
                if (cvu == 0) break;

                Cuenta cuenta = operaciones.cuentaOperar(dni, cvu);

                if (cuenta == null) { // Si cuenta es null, significa que el usuario decidio irse
                    System.out.println("Saliendo...");
                } else {

                    int opcion = menuOperaciones();
                    double monto;

                    switch (opcion) {
                        case 1: //Deposito
                            monto = ingresarDinero("Ingrese el monto del deposito: ");
                            deposito.deposito(cuenta, monto);

                            break;
                        case 2: //Retirar dinero

                            monto = ingresarDinero("Ingrese el monto del retiro: ");
                            retiro.retiro(cuenta, monto);
                            break;
                        case 3://Transferencia

                            long cvuDestino = pedirCvu("Ingrese el CVU de la cuenta a transferir: (0 para salir)");
                            seguir = false;
                            if (cvuDestino == 0) break;


                            //Usuario ingresa a quien quiere transferir, vuelve una excepcion si la cuenta no fue encontrada
                            Cuenta cuentaDestino = operaciones.cuentaATransferir(cvuDestino);

                            monto = ingresarDinero("Ingrese el monto a transferir a la cuenta " + cuentaDestino.getNombre() + ": ");
                            transferencia.transferencia(cuenta, cuentaDestino, monto);
                            break;
                        case 4: //Consulta
                            consulta.consulta(cuenta);
                            break;
                        case 5: //Mostrar movimientos
                            List<Movimiento> mov = movimientos.mostrarMovimientos(cuenta);

                            System.out.println("---------- Movimientos de la cuenta " + cuenta.getNombre() + " ----------");
                            if (!mov.isEmpty()) {
                                for (Movimiento movimiento : mov) {
                                    System.out.println(toString(movimiento));
                                }
                            }
                            break;
                        case 0:
                            seguir = false;
                            break;
                    }
                }
            } catch (ClientesVaciosException | CuentasVaciasException | CuentaNoEncontradaException |
                     ClienteNoEncontradoException | CuentaEstaDeBajaException ex) {
                System.out.println("----------------------------------------");
                System.out.println(ex.getMessage());
                System.out.println("----------------------------------------");
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }

}
