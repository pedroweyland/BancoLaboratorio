package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Movimiento {
    private long CVU;
    private LocalDate fechaOperacion;
    private LocalTime horaOperacion;
    private String tipoOperacion;
    private double monto;

    public long getCVU() {
        return CVU;
    }

    public void setCVU(long CVU) {
        this.CVU = CVU;
    }

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(LocalDate fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public LocalTime getHoraOperacion() {
        return horaOperacion;
    }

    public void setHoraOperacion(LocalTime horaOperacion) {
        this.horaOperacion = horaOperacion;
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
