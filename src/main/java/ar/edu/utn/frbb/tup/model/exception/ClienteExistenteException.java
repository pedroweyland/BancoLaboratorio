package ar.edu.utn.frbb.tup.model.exception;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String message) {
        super(message);
    }
}
