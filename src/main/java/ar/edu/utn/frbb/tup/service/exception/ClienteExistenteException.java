package ar.edu.utn.frbb.tup.service.exception;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String message) {
        super(message);
    }
}
