package ar.edu.utn.frbb.tup.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;
import java.util.Set;

public class MostrarCuenta extends BaseGestion {

    public void mostrarCuenta(Banco banco){
        boolean seguir = true;
        List<Cliente> clientes = banco.getClientes(); //Creo una lista auxiliar 'cliente' con la copia de Clientes que esta en banco ++Legibilidad

        while (seguir){
            long dni = pedirDni("Escriba el DNI del usuario para ver sus cuentas: (0 para salir)");

            if (dni == 0) break;

            //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
            Cliente cliente = encontrarCliente(clientes, dni);
            clearScreen();

            if (cliente == null) {
                System.out.println("No existe ningun cliente con el DNI ingresado");

            } else if (cliente.getCuentas().isEmpty()) { //Valido si es que el usuario no tiene cuentas
                System.out.println("No tiene ninguna cuenta asociada");
                break;

            } else {
                int cantCuentas = 0;
                Set<Cuenta> cuentas = cliente.getCuentas();

                for (Cuenta cuenta : cuentas) {
                    System.out.println("------- Cuentas del cliente " + cliente.getNombre() + " -------");

                    System.out.println(toString(cuenta)); //Muestro en pantalla las cuentas del cliente
                    cantCuentas++;
                }
                System.out.println("Tiene " + cantCuentas + " cuentas asociadas");
                System.out.println("----------------------------------------");

                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();

                seguir = false;
            }
        }
    }

}
