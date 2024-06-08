package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.stereotype.Component;

@Component
public class MovimientoService {
    private final MovimientosDao movimientosDao;

    public MovimientoService(MovimientosDao movimientosDao){
        this.movimientosDao = movimientosDao;
    }

    public void inicializarMovimientos() {
        movimientosDao.inicializarMovimientos();
    }

}
