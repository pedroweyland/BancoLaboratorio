package ar.edu.utn.frbb.tup.validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class Validaciones {


    public static boolean esNumeroLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean esFechaValida(String str) {
        try {
            LocalDate.parse(str);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public static boolean esNumero(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double esDouble(String str){
        try{
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean esMayor(LocalDate fechaNacimiento) {

        //Validacion para saber comprobar que el usuario es mayor de edad para ser cliente

        Period periodo = Period.between(fechaNacimiento, LocalDate.now());

        return periodo.getYears() >= 18;

    }

    public static boolean esFechaAltaValida(String fechaAlta, LocalDate fechaNacimiento) {
        //Valido si el formato es correcto
        if (esFechaValida(fechaAlta)){
            //Valido si el periodo que hay entre fecha de nacimiento y fecha alta es mayor a 18, ya que tiene que ser mayor
            Period periodo = Period.between(fechaNacimiento, LocalDate.parse(fechaAlta));

            if (fechaNacimiento.isBefore(LocalDate.parse(fechaAlta))) {

                if (periodo.getYears() >= 18) {
                    return true;
                } else {
                    System.out.println("Tiene que haber minimo una diferencia de 18 años entre la fecha de alta y nacimiento, (Formato: YYYY-MM-DD):");
                }
            } else {
                System.out.println("La fecha de alta no puede ser antes que la fecha de nacimiento");
            }

        } else {
            System.out.println("Siga el formato correspondiente (Formato: YYYY-MM-DD): ");
            return false;
        }

        return false;
    }


}
