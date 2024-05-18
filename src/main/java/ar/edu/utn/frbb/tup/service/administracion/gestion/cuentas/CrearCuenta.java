package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.presentation.input.CuentaInput;

import java.util.List;
import java.util.Set;

public class CrearCuenta extends BaseGestion {
    CuentaInput cuentaInput = new CuentaInput();

    public void crearCuenta(List<Cliente> clientes) {

        if (clientes.isEmpty()){ //Si no hay clientes no hay cuenta para asignar
            System.out.println("No hay clientes para asignarle una cuenta");
        } else {
            boolean seguir = true;

            while (seguir){

                long dni = pedirDni("Escriba el DNI del cliente para crearle una cuenta: (0 para salir)");

                if (dni == 0) break; //Si escribe 0 termina con el bucle

                Cliente cliente = clienteDao.findCliente(dni);
                clearScreen();

                if (cliente == null) { //Si cliente es == null significa que no se encontro
                    System.out.println("No se encontro ningun cliente con el dni dado");
                } else {
                    //Usuario ingresa los datos y se guarda en la variable cliente
                    Cuenta cuenta = cuentaInput.creacionCuenta();

                    //Agrego la cuenta al archivo y guardo la relacion que tiene con las cuentas
                    cuentaDao.saveCuenta(cuenta);
                    cuentasDeClientes.saveRelacion(dni, cuenta.getCVU());

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
            }
        }
    }
}
