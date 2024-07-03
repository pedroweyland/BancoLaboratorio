package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class  Cuenta {
    private String nombre;
    private boolean estado;
    private double saldo;
    private long CVU;
    private long dniTitular;
    private LocalDate fechaCreacion;
    private TipoCuenta tipoCuenta;
    private TipoMoneda tipoMoneda;

    public Cuenta(){
        Random r = new Random();
        this.saldo = 0;
        this.CVU = r.nextInt(900000) + 100000;
        this.fechaCreacion = LocalDate.now();
        this.estado = true;
    }

    public Cuenta(CuentaDto cuentaDto){
        Random r = new Random();
        this.saldo = 0;
        this.CVU = r.nextInt(900000) + 100000;
        this.fechaCreacion = LocalDate.now();
        this.estado = true;
        this.dniTitular = cuentaDto.getDniTitular();
        this.nombre = cuentaDto.getNombre();
        this.tipoCuenta = TipoCuenta.fromString(cuentaDto.getTipoCuenta());
        this.tipoMoneda = TipoMoneda.fromString(cuentaDto.getTipoMoneda());
    }

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


    public TipoMoneda getTipoMoneda() {
        return tipoMoneda;
    }
    public void setTipoMoneda(TipoMoneda tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public long getDniTitular() {
        return dniTitular;
    }
    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }
}
