package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CuentaService {
    private final CuentaDao cuentaDao;

    public CuentaService(CuentaDao cuentaDao){
        this.cuentaDao = cuentaDao;
    }

    public List<Cuenta> findAllCuentas() throws CuentasVaciasException {
        return cuentaDao.findAllCuentas();
    }

    public void inicializarCuentas() {
        cuentaDao.inicializarCuentas();
    }
}
