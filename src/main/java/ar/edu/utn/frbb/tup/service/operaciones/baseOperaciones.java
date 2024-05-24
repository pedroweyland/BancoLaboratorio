package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.model.Movimiento;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class baseOperaciones {
    protected Scanner scanner = new Scanner(System.in);
    protected ClienteDao clienteDao = new ClienteDao();
    protected CuentaDao cuentaDao = new CuentaDao();
    protected MovimientosDao movimientosDao = new MovimientosDao();

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public Movimiento crearMovimiento(String tipoOperacion, double monto, long cvu){
        //Creo el movimiento para usar cuando se realizen las operaciones
        Movimiento movimiento = new Movimiento();
        movimiento.setCVU(cvu);
        movimiento.setFecha(LocalDate.now());
        movimiento.setHora(LocalTime.now());
        movimiento.setTipoOperacion(tipoOperacion);
        movimiento.setMonto(monto);
        return movimiento;
    }

}
