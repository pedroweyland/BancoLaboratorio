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
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CrearCuenta {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;

    public CrearCuenta(ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    public Cuenta crearCuenta(CuentaDto cuentaDto) throws ClienteNoEncontradoException, TipoCuentaExistenteException, CuentaExistenteException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        //Valido que exista el cliente, si no lanza excepcion
        Cliente cliente = clienteDao.findCliente(cuenta.getDniTitular());

        if (cliente == null){
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + cuenta.getDniTitular());
        }

        //Valido que si la cuenta mandada ya existia previamente, si no lanza excepcion
        Cuenta cuentaExiste = cuentaDao.findCuenta(cuenta.getCVU());

        if (cuentaExiste != null) {
            throw new CuentaExistenteException("Ya tiene una cuenta con ese CVU");
        }

        //Valido que no exista una cuenta con el mismo tipo de cuenta y tipo de moneda
        tieneCuenta(cuenta.getTipoCuenta(), cuenta.getTipoMoneda(), cuenta.getDniTitular());

        //Agrego la cuenta al archivo
        cuentaDao.saveCuenta(cuenta);

        //Muestro en pantalla el resultado
        return cuenta;

    }

    private void tieneCuenta(TipoCuenta tipoCuenta, TipoMoneda tipoMoneda, long dniTitular) throws TipoCuentaExistenteException {
        Set<Cuenta> cuentasClientes = cuentaDao.findAllCuentasDelCliente(dniTitular);

        for (Cuenta cuenta: cuentasClientes) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && tipoMoneda.equals(cuenta.getTipoMoneda())) {
                throw new TipoCuentaExistenteException("Ya tiene una cuenta con ese tipo de cuenta y tipo de moneda");
            }
        }
    }
}
