package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.presentation.input.CuentaInput;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.exception.CuentaExistenteException;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

public class CrearCuenta extends BaseGestion {
    CuentaInput cuentaInput = new CuentaInput();

    public void crearCuenta() {

        boolean seguir = true;

        while (seguir) {

            long dni = pedirDni("Escriba el DNI del cliente para crearle una cuenta: (0 para salir)");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            Cliente cliente = clienteDao.findCliente(dni);

            try {
                if (cliente == null){
                    throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
                }

                //Usuario ingresa los datos y se guarda en la variable cliente
                Cuenta cuenta = cuentaInput.creacionCuenta(dni);

                //Agrego la cuenta al archivo
                cuentaDao.saveCuenta(cuenta);

                //Muestro en pantalla el resultado
                System.out.println("----- Cuenta creada del cliente " + cliente.getNombre() + " -----");
                System.out.println(toString(cuenta));
                System.out.println("-------- Cuenta creada con exito --------");
                System.out.println("----------------------------------------");

                seguir = false;

            } catch (ClienteNoEncontradoException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
            }  finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
            /*
            if (cliente == null) { //Si cliente es == null significa que no se encontro
                System.out.println("No se encontro ningun cliente con el dni dado");
            } else {
                //Usuario ingresa los datos y se guarda en la variable cliente
                Cuenta cuenta = cuentaInput.creacionCuenta(dni);

                //Agrego la cuenta al archivo y guardo la relacion que tiene con las cuentas
                cuentaDao.saveCuenta(cuenta);

                //Muestro en pantalla el resultado
                System.out.println("----- Cuenta creada del cliente " + cliente.getNombre() + " -----");
                System.out.println(toString(cuenta));
                System.out.println("-------- Cuenta creada con exito --------");
                System.out.println("----------------------------------------");

                seguir = false;

                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
            */
        }
    }
}
