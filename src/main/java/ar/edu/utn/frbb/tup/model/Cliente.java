package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


public class Cliente extends Persona{
    private String banco;
    private String mail;
    private TipoPersona tipoPersona;
    private LocalDate fechaAlta;

    public Cliente(){
        super();
        this.fechaAlta = LocalDate.now();
    }

    public Cliente(ClienteDto clienteDto) {
        //Creo dos ternarios en fechaNacimiento y tipoPersona para cuando el usuario no quiera ingresarlas cuando lo quiera modificar
        super(
                clienteDto.getNombre(),
                clienteDto.getApellido(),
                clienteDto.getDireccion(),
                clienteDto.getDni(),
                clienteDto.getFechaNacimiento() != null ? LocalDate.parse(clienteDto.getFechaNacimiento()) : null
        );
        this.banco = clienteDto.getBanco();
        this.mail = clienteDto.getMail();
        this.tipoPersona = clienteDto.getTipoPersona() != null ? TipoPersona.fromString(clienteDto.getTipoPersona()) : null;
        this.fechaAlta = LocalDate.now();
    }
    public String getBanco() {
        return banco;
    }
    public void setBanco(String banco) {
        this.banco = banco;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }
    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }


}
