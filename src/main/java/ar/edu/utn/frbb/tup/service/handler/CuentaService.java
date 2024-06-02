package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import org.springframework.stereotype.Component;

@Component
public class CuentaService {
    CuentaDao cuentaDao = new CuentaDao();

    public void findAllCuentas() throws CuentasVaciasException {
        cuentaDao.findAllCuentas();
    }

    public void inicializarCuentas() {
        cuentaDao.inicializarCuentas();
    }
}
