package ar.edu.utn.frbb.tup.presentation;

import java.util.Map;

public class Resultados {
    private String mensaje;
    private boolean resultado; //true si es exitoso, false si es error

    public Resultados(String mensaje, boolean resultado) {
        this.mensaje = mensaje;
        this.resultado = resultado;
    }

    public Resultados getResultado(){
        return new Resultados(mensaje, resultado);
    }

}
