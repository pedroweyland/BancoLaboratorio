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

    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;
    private final MovimientosDao movimientosDao;

    public EliminarCliente(ClienteDao clienteDao, CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    //Eliminar cliente
    public Cliente eliminarCliente(long dni) throws ClienteNoEncontradoException {
        //Busco el cliente si existe
        Cliente cliente = clienteDao.findCliente(dni);

        if (cliente == null){ //Si no existe retorna null por ende lanzo una excepcion
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        //Elimino el cliente con el DNI ingresado
        clienteDao.deleteCliente(dni);

        //Elimino las relaciones que tiene con las Cuentas y Movimientos
        List<Long> CvuEliminar = cuentaDao.getRelacionesDni(dni); //Obtengo lista de todos los CVUs a eliminar

        for (Long cvu : CvuEliminar){
            cuentaDao.deleteCuenta(cvu);
            movimientosDao.deleteMovimiento(cvu);
        }

        return cliente;
    }

}
