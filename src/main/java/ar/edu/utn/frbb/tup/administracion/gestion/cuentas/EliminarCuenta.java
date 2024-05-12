package ar.edu.utn.frbb.tup.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.entidades.Banco;
import ar.edu.utn.frbb.tup.entidades.Cliente;
import ar.edu.utn.frbb.tup.entidades.Cuenta;
import ar.edu.utn.frbb.tup.administracion.gestion.BaseGestion;

import java.util.List;
import java.util.Set;

public class EliminarCuenta extends BaseGestion {

    public void eliminarCuenta(Banco banco){

        List<Cliente> clientes = banco.getClientes(); //Creo una lista auxiliar 'cliente' con la copia de Clientes que esta en banco ++Legibilidad
        boolean seguir = true;

        while (seguir){

            long dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");

            //Condicional por si se quiere ir el usuario
            if (dni == 0) break;


            Cliente cliente = encontrarCliente(clientes, dni);

            if (cliente == null){
                System.out.println("No existe ningun cliente con el DNI ingresado ");

            } else if (cliente.getCuentas().isEmpty()) { //Valido si el cliente tiene cuentas o no
                System.out.println("Este cliente  no tiene cuentas asociadas");
                break;

            } else {

                Set<Cuenta> cuentas = cliente.getCuentas();

                //Pido CVU de la cuenta que se quiere elimnar
                long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                //Condicional por si se quiere ir el usuario
                if (cvu == 0) break;

                //Recorro el set, busco si existe el id, si es asi borro cuenta
                boolean encontrado = false;
                for (Cuenta cuenta : cuentas){
                    if (cuenta.getCVU() == cvu){
                        cuentas.remove(cuenta);
                        encontrado = true;
                    }
                }

                if (!encontrado){
                    System.out.println("El cliente no tiene una cuenta con el ID ingresado");
                } else {
                    System.out.println("La cuenta se borro exitosamente");
                }

                seguir = false;
            }
        }
    }

}
