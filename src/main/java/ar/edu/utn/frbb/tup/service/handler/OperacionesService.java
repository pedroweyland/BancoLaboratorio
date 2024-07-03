package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MismaCuentaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.Operaciones;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.service.operaciones.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperacionesService {
    private final Consulta consulta;
    private final Deposito deposito;
    private final MostrarMovimientos mostrarMovimientos;
    private final Retiro retiro;
    private final Transferencia transferencia;
    private final MovimientosDao movimientosDao;

    public OperacionesService(Consulta consulta, Deposito deposito, MostrarMovimientos mostrarMovimientos, Retiro retiro, Transferencia transferencia, MovimientosDao movimientosDao) {
        this.consulta = consulta;
        this.deposito = deposito;
        this.mostrarMovimientos = mostrarMovimientos;
        this.retiro = retiro;
        this.transferencia = transferencia;
        this.movimientosDao = movimientosDao;
    }

    public void inicializarMovimientos() {
        movimientosDao.inicializarMovimientos();
    }

    public Operaciones consulta(long cvu) throws CuentaNoEncontradaException {
        return consulta.consulta(cvu);
    }

    public Operaciones deposito(long cvu, double monto) throws CuentaNoEncontradaException {
        return deposito.deposito(cvu, monto);
    }

    public List<Movimiento> mostrarMovimientos(long cvu) throws CuentaNoEncontradaException, MovimientosVaciosException, MovimientosVaciosException {
        return mostrarMovimientos.mostrarMovimientos(cvu);
    }

    public Operaciones retiro(long cvu, double monto) throws CuentaNoEncontradaException, CuentaSinDineroException {
        return retiro.retiro(cvu, monto);
    }

    public Operaciones transferencia(long cvu, long cvuDestino, double monto) throws CuentaNoEncontradaException, MismaCuentaException, CuentaEstaDeBajaException, CuentaSinDineroException {
        return transferencia.transferencia(cvu, cvuDestino, monto);
    }



}
