package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentasVaciasException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import ar.edu.utn.frbb.tup.presentation.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private CuentaValidator cuentaValidator;
    private CuentaService cuentaService;

    public CuentaController(CuentaValidator cuentaValidator, CuentaService cuentaService) {
        this.cuentaValidator = cuentaValidator;
        this.cuentaService = cuentaService;
        cuentaService.inicializarCuentas();
    }

    @GetMapping("/{dni}")
    public List<Cuenta> getAllCuentas(@PathVariable long dni) throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        return cuentaService.mostrarCuenta(dni);
    }

    @PostMapping
    public ResponseEntity<Cuenta> createCuenta(@RequestBody CuentaDto cuentaDto) throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        cuentaValidator.validate(cuentaDto);
        Cuenta cuenta = cuentaService.crearCuenta(cuentaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cuenta);
    }

    @PutMapping("/{dni}/{cvu}")
    public Cuenta darAltaBajaCuenta(@PathVariable long dni, @PathVariable long cvu, @RequestParam boolean opcion) throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        return cuentaService.darAltaBaja(dni, cvu, opcion);
    }

    @DeleteMapping("/{dni}/{cvu}")
    public Cuenta deleteCuenta(@PathVariable long dni, @PathVariable long cvu) throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        return cuentaService.eliminarCuenta(dni, cvu);
    }
}
