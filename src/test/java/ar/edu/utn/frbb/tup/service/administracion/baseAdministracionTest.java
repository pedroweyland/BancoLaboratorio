package ar.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class baseAdministracionTest {

    public Cliente getCliente(String nombre, long dni){
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido("Weyland");
        cliente.setDireccion("Berutti");
        cliente.setDni(dni);
        cliente.setFechaNacimiento(LocalDate.of(2004, 1, 21));
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        return cliente;
    }

    public Cuenta getCuenta(String nombre, long dniTitular, TipoCuenta tipoCuenta, TipoMoneda tipoMoneda){
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(nombre);
        cuenta.setDniTitular(dniTitular);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setEstado(true);
        cuenta.setCVU(123456);
        cuenta.setSaldo(10000);
        cuenta.setTipoMoneda(tipoMoneda);
        return cuenta;
    }

    public List<Cuenta> getCuentasList(Cuenta cuenta){
        List<Cuenta> lista = new ArrayList<>();
        lista.add(cuenta);

        return lista;
    }
}
