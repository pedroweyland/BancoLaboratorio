package ar.edu.utn.frbb.tup.service.administracion.gestion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import org.springframework.stereotype.Service;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

@Service
public class EliminarCliente extends BaseGestion {

    //Eliminar cliente
    public void eliminarCliente() {

        boolean seguir = true;

        while (seguir) {
            long dni = pedirDni("Escriba el DNI al cliente que quiere eliminar: (0 para salir) ");

            clearScreen();

            if (dni == 0) break; //Si escribe 0 termina con el bucle

            try {

                //Elimino el cliente con el DNI ingresado, si no existe el cliente lanza una excepcion,
                //devuelve el cliente eliminado para mostrar en pantalla
                Cliente cliente = clienteDao.deleteCliente(dni);

                System.out.println("------------ Cliente eliminado -----------");
                System.out.println(toString(cliente)); //Muestro en pantalla el cliente eliminado

                //Elimino las relaciones que tiene con las Cuentas y movimientos

                List<Long> CvuEliminar = cuentaDao.getRelacionesDni(dni); //Obtengo lista de todos los CVUs a eliminar

                for (Long cvu : CvuEliminar){
                    cuentaDao.deleteCuenta(cvu);
                    movimientosDao.deleteMovimiento(cvu);
                }
                seguir = false;

            } catch (ClienteNoEncontradoException ex) {
                System.out.println("----------------------------------------");
                System.out.println(ex.getMessage());
                System.out.println("----------------------------------------");
            } finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
