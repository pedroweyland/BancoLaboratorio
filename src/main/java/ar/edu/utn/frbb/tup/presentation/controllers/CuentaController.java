package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.*;
import ar.edu.utn.frbb.tup.service.handler.CuentaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.validator.Validaciones.cuentaEsValida;

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
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaCrear = cuentaEsValida(cuenta);
            return cuentaService.crearCuenta(cuentaCrear);
        } catch (FaltaDeDatosException | ClienteNoEncontradoException | TipoCuentaExistenteException | CuentaExistenteException e) {
            System.out.println(e.getMessage());
        }
        return null;
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
