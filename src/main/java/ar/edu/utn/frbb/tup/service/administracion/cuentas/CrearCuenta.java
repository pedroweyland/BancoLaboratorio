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
    ClienteDao clienteDao;
    CuentaDao cuentaDao;
    CuentaInput cuentaInput;

    public CrearCuenta(ClienteDao clienteDao, CuentaDao cuentaDao, CuentaInput cuentaInput) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.cuentaInput = cuentaInput;
    }

    public Cuenta crearCuenta(long dni) {

        Cliente cliente = clienteDao.findCliente(dni);

        try {
            if (cliente == null){
                throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
            }

            //Usuario ingresa los datos y se guarda en la variable cliente
            Cuenta cuenta = cuentaInput.creacionCuenta(dni);

            //Agrego la cuenta al archivo
            cuentaDao.saveCuenta(cuenta);

            //Muestro en pantalla el resultado
            return cuenta;

        } catch (ClienteNoEncontradoException e) {
            System.out.println("----------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("----------------------------------------");
        }
        return null;
    }
}
