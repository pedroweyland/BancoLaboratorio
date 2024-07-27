package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentasVaciasException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MostrarCuenta {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;

    public MostrarCuenta(ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    public Set<Cuenta> mostrarCuenta(long dni) throws ClienteNoEncontradoException, CuentaNoEncontradaException {

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente cliente = clienteDao.findCliente(dni);

        if (cliente == null) {
            //Lanzo excepcion si el cliente no fue encontrado
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        //Funcion que me devuelve todas las cuentas que tiene el cliente
        Set<Cuenta> cuentas = cuentaDao.findAllCuentasDelCliente(dni);

        if (cuentas.isEmpty()){
            throw new CuentaNoEncontradaException("No hay cuentas asociadas al cliente con DNI: " + dni);
        }

        //Retorna la lista de cuentas que tiene asociada el cliente
        return cuentas;

    }
}
