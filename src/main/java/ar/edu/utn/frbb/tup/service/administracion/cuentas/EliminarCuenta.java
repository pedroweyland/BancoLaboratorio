package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EliminarCuenta {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;
    private final MovimientosDao movimientosDao;

    public EliminarCuenta(ClienteDao clienteDao, CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    public Cuenta eliminarCuenta(long dni, long cvu) throws ClienteNoEncontradoException, CuentasVaciasException, CuentaNoEncontradaException {

        Cliente cliente = clienteDao.findCliente(dni);


        if (cliente == null) {
            //Lanzo excepcion si el cliente no fue encontrado
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        List<Long> cuentasCvu = cuentaDao.getRelacionesDni(dni); //Valido si el DNI tiene cuentas asociadas
        if (cuentasCvu.isEmpty()) {
            throw new CuentasVaciasException("No hay cuentas asociadas al cliente con DNI: " + dni);
        }

        //Funcion que devuelve la cuenta encontrada o vuelve Null si no lo encontro, solo devuelve las cuentas que tiene asocida el cliente
        Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

        if (cuenta == null) {
            //Lanzo excepcion si la cuenta no fue encontrada
            throw new CuentaNoEncontradaException("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
        }

        //Borro la cuenta en Cuentas y Movimientos de esta misma
        cuentaDao.deleteCuenta(cvu);
        movimientosDao.deleteMovimiento(cvu);

        return cuenta;

    }
}
