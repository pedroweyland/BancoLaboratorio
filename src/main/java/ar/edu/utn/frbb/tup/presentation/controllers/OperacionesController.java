package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MismaCuentaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.Operaciones;
import ar.edu.utn.frbb.tup.service.handler.OperacionesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operaciones")

public class OperacionesController {
    private final OperacionesService operacionesService;

    public OperacionesController(OperacionesService operacionesService) {
        this.operacionesService = operacionesService;
        operacionesService.inicializarMovimientos();
    }

    //Consulta de saldo
    @GetMapping("/{cvu}")
    public Operaciones getConsulta(@PathVariable long cvu) throws CuentaNoEncontradaException {
        return operacionesService.consulta(cvu);
    }

    //Deposito
    @PutMapping("/deposito/{cvu}")
    public Operaciones getDeposito(@PathVariable long cvu, @RequestParam double monto) throws CuentaNoEncontradaException {
        return operacionesService.deposito(cvu, monto);
    }

    //Mostrar movimientos
    @GetMapping("/movimientos/{cvu}")
    public List<Movimiento> getMostrarMovimientos(@PathVariable long cvu) throws CuentaNoEncontradaException, MovimientosVaciosException {
        return operacionesService.mostrarMovimientos(cvu);
    }

    //Retiro
    @PutMapping("/retiro/{cvu}")
    public Operaciones getRetiro(@PathVariable long cvu, @RequestParam double monto) throws CuentaNoEncontradaException, CuentaSinDineroException {
        return operacionesService.retiro(cvu, monto);
    }

    //Trransferencia
    @PutMapping("/transferencia/{cvu}/{cvuDestino}")
    public Operaciones getTransferencia(@PathVariable long cvu, @PathVariable long cvuDestino, @RequestParam double monto) throws CuentaNoEncontradaException, CuentaSinDineroException, MismaCuentaException, CuentaEstaDeBajaException {
        return operacionesService.transferencia(cvu, cvuDestino, monto);
    }
    
}
