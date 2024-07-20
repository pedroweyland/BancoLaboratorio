package ar.edu.utn.frbb.tup.service.administracion;

import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseAdministracionTest {

    public static Cliente getCliente(String nombre, long dni){
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido("Weyland");
        cliente.setDireccion("Berutti");
        cliente.setDni(dni);
        cliente.setFechaNacimiento(LocalDate.of(2002, 2, 3));
        cliente.setTipoPersona(TipoPersona.PERSONA_JURIDICA);
        cliente.setBanco("Santander");

        return cliente;
    }

    public static ClienteDto getClienteDto(String nombre, long dni){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre(nombre);
        clienteDto.setApellido("Weyland");
        clienteDto.setDireccion("Berutti");
        clienteDto.setDni(dni);
        clienteDto.setFechaNacimiento("2001-02-02");
        clienteDto.setTipoPersona("J");

        return clienteDto;
    }

    public static Cuenta getCuenta(String nombre, long dniTitular, TipoCuenta tipoCuenta, TipoMoneda tipoMoneda){
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

    public static CuentaDto getCuentaDto(String nombre, long dniTitular, String tipoCuenta, String tipoMoneda){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre(nombre);
        cuentaDto.setDniTitular(dniTitular);
        cuentaDto.setTipoCuenta(tipoCuenta);
        cuentaDto.setTipoMoneda(tipoMoneda);

        return cuentaDto;
    }

    public static List<Cuenta> getCuentasList(Cuenta cuenta){
        List<Cuenta> lista = new ArrayList<>();
        lista.add(cuenta);

        return lista;
    }

    public static List<Cliente> getListaDeClientes() {
        List<Cliente> lista = new ArrayList<>();
        lista.add(getCliente("Pepo", 12341234L));
        lista.add(getCliente("Juan", 12345678L));
        return lista;
    }
}
