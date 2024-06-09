package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.presentation.input.CuentaInput;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import org.springframework.stereotype.Service;

@Service
public class CrearCuenta extends BaseAdministracion {
    private final ClienteDao clienteDao;
    private final CuentaDao cuentaDao;

    public CrearCuenta(ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    public Cuenta crearCuenta(Cuenta cuenta) throws ClienteNoEncontradoException {

        Cliente cliente = clienteDao.findCliente(cuenta.getDniTitular());


        if (cliente == null){
            throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + cuenta.getDniTitular());
        }

        //Agrego la cuenta al archivo
        cuentaDao.saveCuenta(cuenta);

        //Muestro en pantalla el resultado
        return cuenta;

    }
}
