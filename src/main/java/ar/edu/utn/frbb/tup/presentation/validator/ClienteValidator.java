package ar.edu.utn.frbb.tup.presentation.validator;

import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
public class ClienteValidator {

    public void validateCliente(ClienteDto clienteDto) {
        validateDatosCompletos(clienteDto);
        validateFechaNacimiento(clienteDto.getFechaNacimiento());
    }

    public void validateClienteModificacion(ClienteDto clienteDto) {
        if (clienteDto.getDni() == 0) throw new IllegalArgumentException("Error: Ingrese un DNI");
        if (clienteDto.getDni() < 10000000 || clienteDto.getDni() > 99999999) throw new IllegalArgumentException("Error: El dni debe tener 8 digitos");

        if (clienteDto.getFechaNacimiento() != null){
            validateFechaNacimiento(clienteDto.getFechaNacimiento());
        }

    }

    private void validateDatosCompletos(ClienteDto clienteDto) {

        //Valido que el usuario ingreso todos los datos para poder crear nuestro cliente
        //Nombre
        if (clienteDto.getNombre() == null || clienteDto.getNombre().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un nombre");
        //Apellido
        if (clienteDto.getApellido() == null || clienteDto.getApellido().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un apellido");
        //Direccion
        if (clienteDto.getDireccion() == null || clienteDto.getDireccion().isEmpty()) throw new IllegalArgumentException("Error: Ingrese una direccion");
        //Fecha Nacimiento
        if (clienteDto.getFechaNacimiento() == null || clienteDto.getFechaNacimiento().isEmpty()) throw new IllegalArgumentException("Error: Ingrese una fecha de nacimiento");
        //Banco
        if (clienteDto.getBanco() == null || clienteDto.getBanco().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un banco");
        //Mail
        if (clienteDto.getMail() == null || clienteDto.getMail().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un mail");
        //Tipo persona
        if (clienteDto.getTipoPersona() == null || clienteDto.getTipoPersona().isEmpty()) throw new IllegalArgumentException("Error: Ingrese un tipo de persona");

        //DNI primero valido que lo haya ingresado despues valido que el dni sea de 8 digitos
        if (clienteDto.getDni() == 0) throw new IllegalArgumentException("Error: Ingrese un dni");
        if (clienteDto.getDni() < 10000000 || clienteDto.getDni() > 99999999) throw new IllegalArgumentException("Error: El dni debe tener 8 digitos");
    }

    private void validateFechaNacimiento(String fechaNacimiento) {
        try{
            LocalDate.parse(fechaNacimiento);
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Error: La fecha de nacimiento no es correcta");
        }
    }


}
