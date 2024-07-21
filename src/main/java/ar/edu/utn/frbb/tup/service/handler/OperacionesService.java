package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaDistintaMonedaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.TransferenciaFailException;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.Operaciones;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;
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

    public Operaciones consulta(long cvu) throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        return consulta.consulta(cvu);
    }

    public Operaciones deposito(long cvu, double monto) throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        return deposito.deposito(cvu, monto);
    }

    public List<Movimiento> mostrarMovimientos(long cvu) throws CuentaNoEncontradaException, MovimientosVaciosException, CuentaEstaDeBajaException {
        return mostrarMovimientos.mostrarMovimientos(cvu);
    }

    public Operaciones retiro(long cvu, double monto) throws CuentaNoEncontradaException, CuentaSinDineroException, CuentaEstaDeBajaException {
        return retiro.retiro(cvu, monto);
    }

    public Operaciones transferencia(TransferDto transferDto) throws CuentaNoEncontradaException, CuentaEstaDeBajaException, CuentaSinDineroException, CuentaDistintaMonedaException, TransferenciaFailException {
        return transferencia.transferencia(transferDto);
    }
}
