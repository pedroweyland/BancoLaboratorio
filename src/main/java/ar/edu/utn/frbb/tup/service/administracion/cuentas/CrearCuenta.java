package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrearCuenta {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;

    public CrearCuenta(ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    public Cuenta crearCuenta(Cuenta cuenta) throws ClienteNoEncontradoException, TipoCuentaExistenteException, CuentaExistenteException {

        Cliente cliente = clienteDao.findCliente(cuenta.getDniTitular());

        if (cliente == null){
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + cuenta.getDniTitular());
        }

        Cuenta cuentaExiste = cuentaDao.findCuenta(cuenta.getCVU());

        if (cuentaExiste != null) {
            throw new CuentaExistenteException("Ya tiene una cuenta con ese CVU");
        }

        if (tieneCuenta(cuenta.getTipoCuenta(), cuenta.getTipoMoneda(), cuenta.getDniTitular())) {
            throw new TipoCuentaExistenteException("Ya tiene una cuenta con ese tipo de cuenta y tipo de moneda");
        }

        //Agrego la cuenta al archivo
        cuentaDao.saveCuenta(cuenta);

        //Muestro en pantalla el resultado
        return cuenta;

    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda tipoMoneda, long dniTitular) {
        List<Cuenta> cuentasClientes = cuentaDao.findAllCuentasDelCliente(dniTitular);

        for (Cuenta cuenta: cuentasClientes) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && tipoMoneda.equals(cuenta.getTipoMoneda())) {
                return true;
            }
        }

        return false;

    }
}
