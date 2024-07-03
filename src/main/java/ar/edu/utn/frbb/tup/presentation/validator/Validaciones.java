package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.exception.FaltaDeDatosException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class Validaciones {

    public static Cliente clienteEsValido(Cliente cliente) throws FaltaDeDatosException{
        //Valido que el usuario ingreso todos los datos para poder crear nuestro cliente
        //Nombre
        if (cliente.getNombre() == null) throw new FaltaDeDatosException("Error: Ingrese un nombre");
        //Apellido
        if (cliente.getApellido() == null) throw new FaltaDeDatosException("Error: Ingrese un apellido");
        //Direccion
        if (cliente.getDireccion() == null) throw new FaltaDeDatosException("Error: Ingrese una direccion");
        //Fecha Nacimiento
        if (cliente.getFechaNacimiento() == null) throw new FaltaDeDatosException("Error: Ingrese una fecha de nacimiento");
        //Banco
        if (cliente.getBanco() == null) throw new FaltaDeDatosException("Error: Ingrese un banco");
        //Mail
        if (cliente.getMail() == null) throw new FaltaDeDatosException("Error: Ingrese un mail");
        //Tipo persona
        if (cliente.getTipoPersona() == null) throw new FaltaDeDatosException("Error: Ingrese un tipo de persona");
        //Fecha de alta
        if (cliente.getFechaAlta() == null) throw new FaltaDeDatosException("Error: Ingrese una fecha de alta");
        //DNI primero valido que lo haya ingresado despues valido que el dni sea de 8 digitos
        if (cliente.getDni() == 0) throw new FaltaDeDatosException("Error: Ingrese un dni");
        if (cliente.getDni() < 10000000 || cliente.getDni() > 99999999) throw new FaltaDeDatosException("Error: El dni debe tener 8 digitos");

        return cliente;
    }

    public static Cuenta cuentaEsValida(Cuenta cuenta) throws FaltaDeDatosException {
        //Valido que el usuario ingreso todos los datos para poder crear nuestro cliente
        //Nombre de la cuenta
        if (cuenta.getNombre() == null) throw new FaltaDeDatosException("Error: Ingrese un nombre de cuenta");
        //Tipo de cuenta
        if (cuenta.getTipoCuenta() == null) throw new FaltaDeDatosException("Error: Ingrese un tipo de cuenta");
        //Tipo de moneda
        if (cuenta.getTipoMoneda() == null) throw new FaltaDeDatosException("Error: Ingrese un tipo de moneda");
        //Dni titular
        if (cuenta.getDniTitular() == 0) throw new FaltaDeDatosException("Error: Ingrese un dni del titular");
        return cuenta;
    }
    public static long esNumeroLong(String str) throws NumberFormatException {

        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El valor ingresado no es un numero entero");
        }
    }

    public static LocalDate esFechaValida(String str) throws DateTimeParseException {
        try {
            return LocalDate.parse(str);

        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Fecha no valida, siga el formato correspondiente: YYYY-MM-DD" , str, 0);
        }
    }

    public static double esDouble(String str) throws NumberFormatException {
        try{
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El valor ingresado no es un numero");
        }
    }

    public static void esMayor(LocalDate fechaNacimiento) throws ClienteMenorDeEdadException {

        //Validacion para saber comprobar que el usuario es mayor de edad para ser cliente

        Period periodo = Period.between(fechaNacimiento, LocalDate.now());

        if (periodo.getYears() < 18){
            throw new ClienteMenorDeEdadException("Tiene que ser mayor de edad para ser cliente");
        }
    }



}
