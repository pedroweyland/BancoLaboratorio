package ar.edu.utn.frbb.tup.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.inputs.CuentaInput;

import java.util.List;
import java.util.Set;

public class CrearCuenta extends BaseGestion {
    CuentaInput cuentaInput = new CuentaInput();

    public void crearCuenta(List<Cliente> clientes){

        if (clientes.isEmpty()){ //Si no hay clientes no hay cuenta para asignar
            System.out.println("No hay clientes para asignarle una cuenta");
        } else {
            boolean seguir = true;

            while (seguir){

                long dni = pedirDni("Escriba el DNI del cliente para crearle una cuenta: (0 para salir)");

                if (dni == 0) break; //Si escribe 0 termina con el bucle

                Cliente cliente = encontrarCliente(clientes, dni);

                if (cliente == null) { //Si cliente es == null significa que no se encontro
                    System.out.println("No se encontro ningun cliente con el dni dado");
                } else {
                    Set<Cuenta> cuentas = cliente.getCuentas();
                    //Usuario ingresa los datos y se guarda en la variable cliente
                    Cuenta cuenta = cuentaInput.creacionCuenta();

                    //Agrego la cuenta al cliente (Tipo set)
                    cliente.addCuenta(cuenta);
                    //Agrego el nombre de la cuenta al cliente (Tipo String solo para mostrar en pantalla)
                    cliente.addNombreCuentas(cuenta.getNombre());

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
