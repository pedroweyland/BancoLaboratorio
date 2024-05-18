package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Movimiento {
    private LocalDate fechaOperacion;
    private LocalTime horaOperacion;
    private String tipoOperacion;
    private double monto;

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }
    public Movimiento setFecha(LocalDate fecha){
        this.fechaOperacion = fecha;
        return this;
    }

    public LocalTime getHoraOperacion() {
        return horaOperacion;
    }
    public Movimiento setHora(LocalTime hora){
        this.horaOperacion = hora;
        return this;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
}
