package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;

import java.util.List;
import java.util.Set;

public class EliminarCuenta extends BaseGestion {

    public void eliminarCuenta(List<Cliente> clientes){

        boolean seguir = true;

        if (clientes.isEmpty()){ //Si no hay clientes no hay cuenta para eliminar
            System.out.println("No hay clientes para eliminarle una cuenta");
        } else {
            while (seguir) {

                long dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");

                if (dni == 0) break; //Si escribe 0 termina con el bucle

                Cliente cliente = clienteDao.findCliente(dni);
                clearScreen();
                if (cliente == null) {
                    System.out.println("No existe ningun cliente con el DNI ingresado ");

                } else if (!cuentasDeClientes.findRelacion(dni)) { //Valido si el cliente tiene cuentas o no
                    System.out.println("Este cliente no tiene cuentas asociadas");
                    break;

                } else {

                    //Pido CVU de la cuenta que quiere eliminar
                    long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                    clearScreen();
                    if (cvu == 0) break; //Si escribe 0 termina con el bucle

                    //Devuelve la cuenta encontrada o Null si no la encontro
                    Cuenta cuenta = cuentaDao.findCuenta(cvu);

                    if (cuenta == null) {
                        System.out.println("No existe la cuenta con el CVU: " + cvu);
                    } else {
                        //Borro la cuenta en Cuentas
                        cuentaDao.deleteCuenta(cvu);
                        cuentasDeClientes.deleteRelacion(cvu);
                        System.out.println("----------------------------------------");
                        System.out.println("La cuenta se borro exitosamente");
                        System.out.println("----------------------------------------");
                    }
                    seguir = false;

                    System.out.println("Enter para seguir");
                    scanner.nextLine();
                    clearScreen();
                }
            }
        }
    }

}
