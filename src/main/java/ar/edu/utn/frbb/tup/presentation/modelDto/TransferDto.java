package ar.edu.utn.frbb.tup.presentation.modelDto;

public class TransferDto {
    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private String moneda;
    private String tipoTransaccion;

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }
    public void setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }
    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
}
