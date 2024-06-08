package ar.edu.utn.frbb.tup.service.operaciones.modulos;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.exception.CuentaSinDineroException;
import ar.edu.utn.frbb.tup.service.operaciones.baseOperaciones;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.BasePresentation.ingresarDinero;

@Service
public class Retiro extends baseOperaciones {
    private final CuentaDao cuentaDao;
    private final MovimientosDao movimientosDao;

    private final String tipoOperacion = "Retiro";

    public Retiro(CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    public void retiro(Cuenta cuenta, double monto) {

        try {
            if (cuenta.getSaldo() == 0){ //Si no tiene dinero para retirar lanzo una excepcion
                throw new CuentaSinDineroException("No tiene dinero en esta cuenta para retirar");
            }

            if (cuenta.getSaldo() < monto){ //Si no le alcanza el dinero para retirar lanza una excepcion
                throw new CuentaSinDineroException("No puede retirar ese monto, su saldo es de $" + cuenta.getSaldo());
            }

            System.out.println("----------------------------------------");
            cuentaDao.deleteCuenta(cuenta.getCVU()); //Borro la cuenta ya que va ser modificada
            //Resto el monto al saldo que tenia la cuenta
            cuenta.setSaldo(cuenta.getSaldo() - monto);

            //Tomo registro de la operacion que se hizo
            Movimiento movimiento = crearMovimiento(tipoOperacion, monto, cuenta.getCVU());
            movimientosDao.saveMovimiento(movimiento);

            System.out.println("Se ha realizado el retiro de $" + monto + " a la cuenta " + cuenta.getNombre());

            cuentaDao.saveCuenta(cuenta); //Guardo la cuenta modificada

            System.out.println("----------------------------------------");

        } catch (CuentaSinDineroException e) {
            System.out.println("----------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("----------------------------------------");
        } finally {
            System.out.println("Enter para seguir");
            scanner.nextLine();
            clearScreen();
        }

    }
}
