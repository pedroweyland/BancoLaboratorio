package ar.edu.utn.frbb.tup.presentation.controllers;

import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.service.administracion.cuentas.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CrearCuenta crearCuenta;
    private final DarAltaBaja darAltaBaja;
    private final EliminarCuenta eliminarCliente;
    private final MostrarCuenta mostrarCuenta;

    public CuentaController(CrearCuenta crearCuenta, DarAltaBaja darAltaBaja, EliminarCuenta eliminarCliente, MostrarCuenta mostrarCuenta) {
        this.crearCuenta = crearCuenta;
        this.darAltaBaja = darAltaBaja;
        this.eliminarCliente = eliminarCliente;
        this.mostrarCuenta = mostrarCuenta;
    }

    @GetMapping("/{dni}")
    public List<Cuenta> getAllCuentas(@PathVariable long dni) throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        return mostrarCuenta.mostrarCuenta(dni);
    }

    @PostMapping
    public Cuenta createCuenta(@RequestBody Cuenta cuenta) throws ClienteNoEncontradoException {
        return crearCuenta.crearCuenta(cuenta);
    }

    @PutMapping("/{dni}/{cvu}")
    public Cuenta darAltaBajaCuenta(@PathVariable long dni, @PathVariable long cvu, @RequestParam boolean opcion) throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        return darAltaBaja.gestionarEstado(dni, cvu, opcion);
    }

    @DeleteMapping("/{dni}/{cvu}")
    public Cuenta deleteCuenta(@PathVariable long dni, @PathVariable long cvu) throws CuentasVaciasException, CuentaNoEncontradaException, ClienteNoEncontradoException {
        return eliminarCliente.eliminarCuenta(dni, cvu);
    }

}
