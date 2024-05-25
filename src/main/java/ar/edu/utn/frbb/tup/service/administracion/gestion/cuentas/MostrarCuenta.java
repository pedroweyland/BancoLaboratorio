package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.exception.CuentasVaciasException;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

public class MostrarCuenta extends BaseGestion {

    public void mostrarCuenta() {

        while (true) {
            long dni = pedirDni("Escriba el DNI del usuario para ver sus cuentas: (0 para salir)");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = clienteDao.findCliente(dni);

            try {
                if (cliente == null) {
                    //Lanzo excepcion si el cliente no fue encontrado
                    throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
                }

                //Me devuelve toda la lista de cuentas que hay
                List<Cuenta> cuentas = cuentaDao.findAllCuentas();

                for (Cuenta cuenta : cuentas) {
                    if (cuenta.getDniTitular() == dni) {
                        //Muestro en pantalla las cuentas que tiene asociada el cliente
                        System.out.println("------- Cuentas del cliente " + cliente.getNombre() + " -------");
                        System.out.println(toString(cuenta));
                    }
                }

                break;

            } catch (ClienteNoEncontradoException | CuentasVaciasException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
            } finally{
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
