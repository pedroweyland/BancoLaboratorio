package ar.edu.utn.frbb.tup.model;

public class Operaciones {
    private String tipoOperacion;
    private long cvu;
    private double saldoActual;
    private double monto;

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public Operaciones setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
        return this;
    }

    public long getCvu() {
        return cvu;
    }

    public Operaciones setCvu(long cvu) {
        this.cvu = cvu;
        return this;
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public Operaciones setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
        return this;
    }

    public double getMonto() {
        return monto;
    }

    public Operaciones setMonto(double monto) {
        this.monto = monto;
        return this;
    }
}
