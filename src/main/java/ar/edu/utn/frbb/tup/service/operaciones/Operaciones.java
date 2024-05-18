package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.operaciones.modulos.*;


import static ar.edu.utn.frbb.tup.presentation.input.Menus.menuOperaciones;

public class Operaciones extends baseOperaciones {

    public void operaciones () {
        boolean seguir = true;

        if (!clienteDao.findAllClientes().isEmpty()) { //Si no hay Clientes entonces no se puede operar
            if (!cuentaDao.findAllCuentas().isEmpty()) { //Si no hay Cuentas entonces no se puede operar
                Cuenta cuenta = cuentaOperar();

                if (cuenta == null) { // Si cuenta es null, significa que el usuario decidio irse o no tiene cuentas asociadas
                    System.out.println("Saliendo...");
                } else {
                    while (seguir) {

                        int opcion = menuOperaciones();

                        switch (opcion) {
                            case 1:
                                //Deposito
                                Deposito d = new Deposito();
                                d.deposito(cuenta);
                                break;
                            case 2:
                                //Retirar dinero
                                Retiro r = new Retiro();
                                r.retiro(cuenta);
                                break;
                            case 3:
                                //Transferencia
                                Cuenta cuentaDestino = cuentaATransferir();

                                if (cuentaDestino != null) {
                                    Transferencia t = new Transferencia();
                                    t.transferencia(cuenta, cuentaDestino);
                                }
                                break;
                            case 4:
                                Consulta c = new Consulta();
                                c.consulta(cuenta);
                                break;
                            case 5:
                                MostrarMovimientos m = new MostrarMovimientos();
                                m.mostrarMovimientos(cuenta);
                                break;
                            case 0:

                                seguir = false;
                                break;
                        }
                    }
                }
            } else {
                System.out.println("----------------------------------------");
                System.out.println("No hay cuentas registradas");
                System.out.println("----------------------------------------");
                System.out.println("Enter para seguir");
                scanner.nextLine();
            }
        } else {
            System.out.println("----------------------------------------");
            System.out.println("No hay clientes registrados");
            System.out.println("----------------------------------------");
            System.out.println("Enter para seguir");
            scanner.nextLine();
        }
    }

    //Funcion para que el usaurio encuentre la cuenta que desea operar, y retorna la cuenta o null si no la encuentra
    public Cuenta cuentaOperar(){

        while (true) {

            //Pido el DNI del cliente, y busco si existe
            long dni = pedirDni("Para realizar una operacion ingrese el DNI del cliente: (0 para salir)");
            if (dni == 0) break; //Si escribe 0 termina con el bucle


            Cliente cliente = clienteDao.findCliente(dni);

            if (cliente == null){ //Verifico si el cliente existe
                System.out.println("----------------------------------------");
                System.out.println("No se encontro ningun cliente con el DNI dado");
                System.out.println("----------------------------------------");
                System.out.println("Enter para seguir");
                scanner.nextLine();
                break;
            }

            while (true) { //Bucle while por si el usuario se equivoca del CVU y que intente de poner de vuelta CVU

                //Pido el ID de la cuenta, y busco si existe
                long cvu = pedirCvu("Ahora ingrese el CVU de la cuenta en la que quiere operar: (0 para salir)");
                if (cvu == 0) break;

                if (cuentasDeClientes.relacionDniYCbu(dni, cvu)){
                    Cuenta cuenta = cuentaDao.findCuenta(cvu);

                    if (cuenta != null) { //Verifico si la cuenta existe

                        if (cuenta.getEstado()){
                            clearScreen();
                            return cuenta;
                        } else {
                            System.out.println("----------------------------------------");
                            System.out.println("La cuenta esta de baja, no puede operar.");
                            System.out.println("----------------------------------------");
                            System.out.println("Enter para seguir");
                            scanner.nextLine();
                            return null;
                        }
                    }
                } else {
                    System.out.println("----------------------------------------");
                    System.out.println("El cliente no tiene cuentas asociadas");
                    System.out.println("----------------------------------------");
                    System.out.println("Enter para seguir");
                    scanner.nextLine();
                    break;
                }
            }
            clearScreen();
        }
        return null;
    }

    public Cuenta cuentaATransferir() {

        long cvu = pedirCvu("Ingrese el CVU de la cuenta a transferir: (0 para salir)");

        if (cvu == 0) {
            System.out.println("Saliendo...");
            return null;
        }

        Cuenta cuenta = cuentaDao.findCuenta(cvu);

        if (cuenta == null){
            System.out.println("No se encontro ninguna cuenta con el CVU dado " + cvu);
            return null;
        } else {
            return cuenta;
        }

    }
}
