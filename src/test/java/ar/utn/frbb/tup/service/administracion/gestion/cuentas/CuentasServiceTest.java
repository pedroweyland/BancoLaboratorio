package ar.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CuentasServiceTest {

    private static CuentaDao cuentaDao;

    @BeforeAll
    public static void setUp(){
        cuentaDao = new CuentaDao();
        cuentaDao.inicializarCuentas();
    }

    @Test
    public void testCuentaSuccess(){
        Cuenta cuenta = new Cuenta();

        cuenta.setNombre("Peperino");
        cuenta.setEstado(true);
        cuenta.setSaldo(0);
        cuenta.setCVU(12345678);
        cuenta.setDniTitular(12345678);
        cuenta.setFechaCreacion(LocalDate.now());
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);

        cuentaDao.saveCuenta(cuenta);

        //Verifico que se haya guardado correctamente la cuenta
        assertNotNull(cuentaDao.findCuenta(cuenta.getCVU()));

        cuentaDao.deleteCuenta(cuenta.getCVU());

        assertNull(cuentaDao.findCuenta(cuenta.getCVU()));

    }
}
