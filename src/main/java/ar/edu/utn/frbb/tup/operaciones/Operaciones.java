package ar.edu.utn.frbb.tup.operaciones;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.operaciones.modulos.*;

import java.util.List;

import static ar.edu.utn.frbb.tup.menuBuilder.Menus.menuOperaciones;

public class Operaciones extends baseOperaciones {

    public void operaciones (Banco banco) {
        boolean seguir = true;
        Cuenta cuenta = cuentaOperar(banco);

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
                        Cuenta cuentaDestino = cuentaATransferir(banco);

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

    }

    //Funcion para que el usaurio encuentre la cuenta que desea operar, y retorna la cuenta o null si no la encuentra
    public Cuenta cuentaOperar(Banco banco){

        while (true) {

            //Pido el DNI del cliente, y busco si existe
            long dni = pedirDni("Para realizar una operacion ingrese el DNI del cliente: (0 para salir)");
            Cliente cliente = encontrarCliente(banco.getClientes(), dni);
            if (dni == 0) break;

            if (cliente == null){ //Verifico si el cliente existe
                System.out.println("No se encontro ningun cliente con el DNI dado");
            } else if (cliente.getCuentas().isEmpty()){ //Verifico si el cliente tiene cuentas asociadas
                System.out.println("El cliente no tiene cuentas asociadas");
                break;
            } else { //Si el cliente existe y tiene cuentas asociadas se procede a buscar la cuenta

                while (true) { //Bucle while por si el usuario se equivoca del CVU y que intente de poner de vuelta CVU
                    //Pido el ID de la cuenta, y busco si existe
                    long cvu = pedirCvu("Ahora ingrese el CVU de la cuenta en la que quiere operar: (0 para salir)");
                    Cuenta cuenta = encontrarCuenta(cliente.getCuentas(), cvu);
                    if (cvu == 0) break;

                    if (cuenta != null) { //Verifico si la cuenta existe
                        clearScreen();
                        return cuenta;
                    } else {
                        System.out.println("No se encontro ninguna cuenta con el CVU dado");
                    }
                }
            }
            clearScreen();
        }
        return null;
    }

    public Cuenta cuentaATransferir(Banco banco) {

        long cvu = pedirCvu("Ingrese el CVU de la cuenta a transferir: (0 para salir)");

        if (cvu == 0) {
            System.out.println("Saliendo...");
            return null;
        }

        List<Cliente> clientes = banco.getClientes();

        for (Cliente cliente : clientes) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                if (cuenta.getCVU() == cvu) {
                    return cuenta;
                }
            }
        }

        System.out.println("No se encontro ninguna cuenta con el CVU dado " + cvu);
        return null;
    }
}
