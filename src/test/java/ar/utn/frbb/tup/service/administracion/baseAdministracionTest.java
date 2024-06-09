package ar.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.TipoPersona;

import java.time.LocalDate;

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

    public Cuenta getCuenta(String nombre, long dniTitular, TipoCuenta tipoCuenta){
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(nombre);
        cuenta.setDniTitular(dniTitular);
        cuenta.setTipoCuenta(tipoCuenta);
        cuenta.setEstado(true);
        cuenta.setCVU(123456);
        cuenta.setSaldo(10000);

        return cuenta;
    }
}
