package ar.edu.utn.frbb.tup.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
    private String nombre;
    private boolean estado;
    private double saldo;
    private long CVU;
    private LocalDate fechaCreacion;
    private TipoCuenta tipoCuenta;
    private List<Movimiento> movimientos = new ArrayList<>();
    private int id;
    private static int ultimoId = 0;

    public Cuenta() {
        ultimoId++; // Incrementa el ID cada vez que se crea una nueva cuenta
        this.id = ultimoId;
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

    public int getId() {
        return id;
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
}
