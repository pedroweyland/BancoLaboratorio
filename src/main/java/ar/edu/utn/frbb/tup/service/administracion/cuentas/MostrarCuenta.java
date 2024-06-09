package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MostrarCuenta extends BaseAdministracion {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;

    public MostrarCuenta(ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    public List<Cuenta> mostrarCuenta(long dni) throws ClienteNoEncontradoException, CuentasVaciasException, CuentaNoEncontradaException {

        //Funcion que devuelve el cliente encontrado o vuelve Null si no lo encontro
        Cliente cliente = clienteDao.findCliente(dni);

        if (cliente == null) {
            //Lanzo excepcion si el cliente no fue encontrado
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
        }

        //Me devuelve la lista de todas las cuentas
        List<Cuenta> cuentas = cuentaDao.findAllCuentas();

        if (cuentas.isEmpty()){ //Si la lista esta vacia significa que no hay cuentas registradas
            throw new CuentasVaciasException("No hay cuentas registradas");
        }

        List<Cuenta> aux = new ArrayList<>(); //Lista auxiliar para guardar las cuentas del cliente

        boolean encontrada = false;
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getDniTitular() == dni) {
                aux.add(cuenta);
                encontrada = true;
            }
        }

        if (!encontrada) {
            throw new CuentaNoEncontradaException("No hay cuentas asociadas al cliente con DNI: " + dni);
        }

        //Retorna la lista de cuentas que tiene asociada el cliente
        return aux;

    }
}
