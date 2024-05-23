package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirCvu;
import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

public class EliminarCuenta extends BaseGestion {

    public void eliminarCuenta() {

        boolean seguir = true;

        while (seguir) {

            long dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            Cliente cliente = clienteDao.findCliente(dni);

            if (cliente == null) {
                System.out.println("No existe ningun cliente con el DNI ingresado ");

            } else {

                //Pido CVU de la cuenta que quiere eliminar
                long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                clearScreen();

                if (cvu == 0) break; //Si escribe 0 termina con el bucle

                //Funcion que devuelve la cuenta encontrada o vuelve Null si no lo encontro, solo devuelve las cuentas que tiene asocida el cliente
                Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

                if (cuenta == null) {
                    System.out.println("----------------------------------------");
                    System.out.println("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
                    System.out.println("----------------------------------------");
                } else {
                    //Borro la cuenta en Cuentas y Movimientos de esta misma
                    cuentaDao.deleteCuenta(cvu);
                    movimientosDao.deleteMovimiento(cvu);

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
