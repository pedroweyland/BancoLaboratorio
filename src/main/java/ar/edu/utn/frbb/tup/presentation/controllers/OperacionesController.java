package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.service.handler.MovimientoService;
import ar.edu.utn.frbb.tup.service.operaciones.modulos.*;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operaciones")

public class OperacionesController {
    private final Consulta consulta;
    private final Deposito deposito;
    private final MostrarMovimientos mostrarMovimientos;
    private final Retiro retiro;
    private final Transferencia transferencia;
    private final MovimientoService movimientoService;

    public OperacionesController(Consulta consulta, Deposito deposito, MostrarMovimientos mostrarMovimientos, Retiro retiro, Transferencia transferencia, MovimientoService movimientoService) {
        this.consulta = consulta;
        this.deposito = deposito;
        this.mostrarMovimientos = mostrarMovimientos;
        this.retiro = retiro;
        this.transferencia = transferencia;
        this.movimientoService = movimientoService;
        movimientoService.inicializarMovimientos();
    }

    //Consulta de saldo
    @GetMapping("/{cvu}")
    public double getConsulta(@PathVariable long cvu) throws CuentaNoEncontradaException {
        return consulta.consulta(cvu);
    }

    //Deposito
    @PutMapping("/deposito/{cvu}")
    public double getDeposito(@PathVariable long cvu, @RequestParam double monto) throws CuentaNoEncontradaException {
        return deposito.deposito(cvu, monto);
    }

    //Mostrar movimientos
    @GetMapping("/movimientos/{cvu}")
    public List<Movimiento> getMostrarMovimientos(@PathVariable long cvu) throws CuentaNoEncontradaException, MovimientosVaciosException {
        return mostrarMovimientos.mostrarMovimientos(cvu);
    }

    //Retiro
    @PutMapping("/retiro/{cvu}")
    public double getRetiro(@PathVariable long cvu, @RequestParam double monto) throws CuentaNoEncontradaException, CuentaSinDineroException {
        return retiro.retiro(cvu, monto);
    }

    //Trransferencia
    @PutMapping("/transferencia/{cvu}/{cvuDestino}")
    public double getTransferencia(@PathVariable long cvu, @PathVariable long cvuDestino, @RequestParam double monto) throws CuentaNoEncontradaException, CuentaSinDineroException, MismaCuentaException, CuentaEstaDeBajaException {
        return transferencia.transferencia(cvu, cvuDestino, monto);
    }
    
}
