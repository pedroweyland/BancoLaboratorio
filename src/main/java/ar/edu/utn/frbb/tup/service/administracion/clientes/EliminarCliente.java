package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EliminarCliente extends BaseAdministracion {

    ClienteDao clienteDao;
    CuentaDao cuentaDao;
    MovimientosDao movimientosDao;

    public EliminarCliente(ClienteDao clienteDao, CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    //Eliminar cliente
    public void eliminarCliente(long dni) {

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
