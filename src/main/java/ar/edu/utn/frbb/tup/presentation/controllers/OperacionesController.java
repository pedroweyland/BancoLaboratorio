package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaDistintaMonedaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.TransferenciaFailException;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.model.Operaciones;
import ar.edu.utn.frbb.tup.presentation.modelDto.TransferDto;
import ar.edu.utn.frbb.tup.presentation.validator.TransferValidator;
import ar.edu.utn.frbb.tup.service.handler.OperacionesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/consulta/{cvu}")
    public ResponseEntity<Operaciones> getConsulta(@PathVariable long cvu) throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        return new ResponseEntity<>(operacionesService.consulta(cvu), HttpStatus.OK);
    }

    //Deposito
    @PutMapping("/deposito/{cvu}")
    public ResponseEntity<Operaciones> getDeposito(@PathVariable long cvu, @RequestParam double monto) throws CuentaNoEncontradaException, CuentaEstaDeBajaException {
        return new ResponseEntity<>(operacionesService.deposito(cvu, monto), HttpStatus.OK);
    }

    //Mostrar movimientos
    @GetMapping("/movimientos/{cvu}")
    public ResponseEntity<List<Movimiento>> getMostrarMovimientos(@PathVariable long cvu) throws CuentaNoEncontradaException, MovimientosVaciosException, CuentaEstaDeBajaException {
        return new ResponseEntity<>(operacionesService.mostrarMovimientos(cvu), HttpStatus.OK);
    }

    //Retiro
    @PutMapping("/retiro/{cvu}")
    public ResponseEntity<Operaciones> getRetiro(@PathVariable long cvu, @RequestParam double monto) throws CuentaNoEncontradaException, CuentaSinDineroException, CuentaEstaDeBajaException {
        return new ResponseEntity<>(operacionesService.retiro(cvu, monto), HttpStatus.OK);
    }

    //Transferencia
    @PostMapping("/transferencia")
    public ResponseEntity<Operaciones> getTransferencia(@RequestBody TransferDto transferDto) throws CuentaNoEncontradaException, CuentaSinDineroException, CuentaEstaDeBajaException, CuentaDistintaMonedaException, TransferenciaFailException {
        TransferValidator.validate(transferDto);
        return new ResponseEntity<>(operacionesService.transferencia(transferDto), HttpStatus.OK);
    }
}
