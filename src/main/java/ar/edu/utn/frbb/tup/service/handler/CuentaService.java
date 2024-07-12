package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentasVaciasException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.CrearCuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.DarAltaBaja;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.EliminarCuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.MostrarCuenta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CuentaService {
    private final CrearCuenta crearCuenta;
    private final DarAltaBaja darAltaBaja;
    private final EliminarCuenta eliminarCuenta;
    private final MostrarCuenta mostrarCuenta;
    private final CuentaDao cuentaDao;

    public CuentaService(CrearCuenta crearCuenta, DarAltaBaja darAltaBaja, EliminarCuenta eliminarCuenta, MostrarCuenta mostrarCuenta, CuentaDao cuentaDao) {
        this.crearCuenta = crearCuenta;
        this.darAltaBaja = darAltaBaja;
        this.eliminarCuenta = eliminarCuenta;
        this.mostrarCuenta = mostrarCuenta;
        this.cuentaDao = cuentaDao;
    }

    public void inicializarCuentas() {
        cuentaDao.inicializarCuentas();
    }

    public Cuenta crearCuenta(CuentaDto cuentaDto) throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        return crearCuenta.crearCuenta(cuentaDto);
    }

    public Cuenta darAltaBaja(long dni, long cvu, boolean opcion) throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        return darAltaBaja.gestionarEstado(dni, cvu, opcion);
    }

    public Cuenta eliminarCuenta(long dni, long cvu) throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        return eliminarCuenta.eliminarCuenta(dni, cvu);
    }

    public List<Cuenta> mostrarCuenta(long dni) throws ClienteNoEncontradoException, CuentasVaciasException, CuentaNoEncontradaException {
        return mostrarCuenta.mostrarCuenta(dni);
    }
}
