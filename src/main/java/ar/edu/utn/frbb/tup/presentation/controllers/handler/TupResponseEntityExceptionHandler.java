package ar.edu.utn.frbb.tup.presentation.controllers.handler;

import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteExistenteException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.ClientesException.ClientesVaciosException;
import ar.edu.utn.frbb.tup.exception.CuentasException.*;
import ar.edu.utn.frbb.tup.exception.OperacionesException.CuentaEstaDeBajaException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.MovimientosVaciosException;
import ar.edu.utn.frbb.tup.exception.OperacionesException.TransferenciaFailException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TupResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ClienteNoEncontradoException.class,
            ClientesVaciosException.class, CuentaNoEncontradaException.class,
            CuentasVaciasException.class, MovimientosVaciosException.class})
    protected ResponseEntity<Object> handleMateriaNotFound(Exception ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorCode(404);
        error.setErrorMessage(exceptionMessage);

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class,
            ClienteExistenteException.class, ClienteMenorDeEdadException.class,
            TipoCuentaExistenteException.class, CuentaExistenteException.class,
            TransferenciaFailException.class, CuentaDistintaMonedaException.class,
            CuentaSinDineroException.class, CuentaEstaDeBajaException.class,})
    protected ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        String exceptionMessage = ex.getMessage();
        CustomApiError error = new CustomApiError();
        error.setErrorCode(400);
        error.setErrorMessage(exceptionMessage);

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(ex.getMessage());
            body = error;
        }

        return new ResponseEntity<>(body, headers, status);
    }
}