package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsultaTest {
    @Mock
    CuentaDao cuentaDao;

    @Mock
    MovimientosDao movimientosDao;

    @InjectMocks
    Consulta consulta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        consulta = new Consulta(movimientosDao, cuentaDao);
    }

    @Test
    public void testConsultaSuccess(){

    }
}
