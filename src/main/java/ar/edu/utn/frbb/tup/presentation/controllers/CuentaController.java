package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaExistenteException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasException.CuentasVaciasException;
import ar.edu.utn.frbb.tup.exception.CuentasException.TipoCuentaExistenteException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
        cuentaService.inicializarCuentas();
    }

    @GetMapping("/{dni}")
    public List<Cuenta> getAllCuentas(@PathVariable long dni) throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        return cuentaService.mostrarCuenta(dni);
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) throws TipoCuentaExistenteException, CuentaExistenteException, ClienteNoEncontradoException {
        return cuentaService.crearCuenta(cuenta);
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
