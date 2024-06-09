package ar.edu.utn.frbb.tup.service.administracion.clientes;

import ar.edu.utn.frbb.tup.exception.ClienteExistenteException;
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
    public Cliente eliminarCliente(long dni) throws ClienteNoEncontradoException {

        if (clienteDao.findCliente(dni) != null){
            throw new ClienteNoEncontradoException("Ya existe un cliente con el DNI ingresado");
        }

        //Elimino el cliente con el DNI ingresado, si no existe el cliente lanza una excepcion,
        //devuelve el cliente eliminado para mostrar en pantalla
        Cliente cliente = clienteDao.deleteCliente(dni);


        //Elimino las relaciones que tiene con las Cuentas y movimientos

        List<Long> CvuEliminar = cuentaDao.getRelacionesDni(dni); //Obtengo lista de todos los CVUs a eliminar

        for (Long cvu : CvuEliminar){
            cuentaDao.deleteCuenta(cvu);
            movimientosDao.deleteMovimiento(cvu);
        }

        return cliente;
    }

}
