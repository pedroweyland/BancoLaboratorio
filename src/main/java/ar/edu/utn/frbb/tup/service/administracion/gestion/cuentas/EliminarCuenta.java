package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.exception.CuentaNoEncontradaException;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirCvu;
import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

public class EliminarCuenta extends BaseGestion {

    public void eliminarCuenta() {

        while (true) {

            long dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            Cliente cliente = clienteDao.findCliente(dni);

            try {
                if (cliente == null) {
                    //Lanzo excepcion si el cliente no fue encontrado
                    throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
                }
                //Pido CVU de la cuenta que quiere eliminar
                long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                clearScreen();

                if (cvu == 0) break; //Si escribe 0 termina con el bucle

                //Funcion que devuelve la cuenta encontrada o vuelve Null si no lo encontro, solo devuelve las cuentas que tiene asocida el cliente
                Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

                if (cuenta == null){
                    //Lanzo excepcion si la cuenta no fue encontrada
                    throw new CuentaNoEncontradaException("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
                }

                //Borro la cuenta en Cuentas y Movimientos de esta misma
                cuentaDao.deleteCuenta(cvu);
                movimientosDao.deleteMovimiento(cvu);

                System.out.println("----------------------------------------");
                System.out.println("La cuenta se borro exitosamente");
                System.out.println("----------------------------------------");

                break;

            } catch (ClienteNoEncontradoException | CuentaNoEncontradaException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
            } finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
