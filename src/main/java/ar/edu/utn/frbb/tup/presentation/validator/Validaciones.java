package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.exception.ClienteFechaDeAltaInvalidaException;
import ar.edu.utn.frbb.tup.exception.ClienteMenorDeEdadException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class Validaciones {


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

    public static void esFechaAltaValida(LocalDate fechaAlta, LocalDate fechaNacimiento) throws ClienteFechaDeAltaInvalidaException{
        //Valido si el formato es correcto

        Period period = Period.between(fechaNacimiento, fechaAlta);

        if (fechaNacimiento.isBefore(fechaAlta)) { //Valido que la fehca de alta no sea antes que la fecha de nacimiento

            if (fechaAlta.isBefore(LocalDate.now())) { //Valido que la fecha de alta no sea despues de la fecha actual

                if (period.getYears() < 18) { //Valido que la fecha de alta no sea menor a 18 años
                    throw new ClienteFechaDeAltaInvalidaException("Tiene que haber minimo una diferencia de 18 anios entre la fecha de alta y nacimiento, (Formato: YYYY-MM-DD):");
                }

            } else {
                throw new ClienteFechaDeAltaInvalidaException("La fecha de alta no puede ser despues de la fecha actual, (Formato: YYYY-MM-DD):");
            }

        } else {
            throw new ClienteFechaDeAltaInvalidaException("La fecha de alta no puede ser antes que la fecha de nacimiento, (Formato: YYYY-MM-DD):");
        }
    }


}
