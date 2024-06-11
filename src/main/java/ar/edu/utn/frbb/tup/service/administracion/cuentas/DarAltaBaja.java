package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.BasePresentation.*;

@Service
public class DarAltaBaja extends BaseAdministracion {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;

    public DarAltaBaja(ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    public Cuenta gestionarEstado (long dni, long cvu, boolean opcion) throws ClienteNoEncontradoException, CuentaNoEncontradaException {

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente cliente = clienteDao.findCliente(dni);

        if (cliente == null) {
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        //Funcion que devuelve la cuenta encontrada o vuelve Null si no lo encontro, solo devuelve las cuentas que tiene asocida el cliente
        Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

        if (cuenta == null) {
            throw new CuentaNoEncontradaException("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
        }

        cuentaDao.deleteCuenta(cvu); //Borro la cuenta ya que va ser actualizada

        cuenta.setEstado(opcion);

        cuentaDao.saveCuenta(cuenta); //Guardo la cuenta y la relacion actualizada

        return cuenta;
    }

}
