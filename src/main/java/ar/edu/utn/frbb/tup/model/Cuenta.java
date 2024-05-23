package ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class  Cuenta {
    private String nombre;
    private boolean estado;
    private double saldo;
    private long CVU;
    private long dniTitular;
    private LocalDate fechaCreacion;
    private TipoCuenta tipoCuenta;

    private List<Movimiento> movimientos = new ArrayList<>();

    public String getNombre(){
        return nombre;
    }
    public Cuenta setNombre(String nombre){
        this.nombre = nombre;
        return this;
    }

    public boolean getEstado() {
        return estado;
    }
    public Cuenta setEstado(boolean estado) {
        this.estado = estado;
        return this;
    }

    public double getSaldo() {
        return saldo;
    }
    public Cuenta setSaldo(double saldo) {
        this.saldo = saldo;
        return this;
    }

    public long getCVU() {
        return CVU;
    }
    public Cuenta setCVU(long CVU) {
        this.CVU = CVU;
        return this;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public Cuenta setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }
    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void addMovimientos(Movimiento movimiento) {
        this.movimientos.add(movimiento);
    }


    public long getDniTitular() {
        return dniTitular;
    }
    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }
}
