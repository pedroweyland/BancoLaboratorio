package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


public class Cliente extends Persona{
    private String banco;
    private String mail;
    private TipoPersona tipoPersona;
    private LocalDate fechaAlta;

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
