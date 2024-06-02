package ar.edu.utn.frbb.tup.service.handler;

import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.stereotype.Component;

@Component
public class MovimientoService {
    MovimientosDao movimientosDao = new MovimientosDao();

    public void inicializarMovimientos() {
        movimientosDao.inicializarMovimientos();
    }

}
