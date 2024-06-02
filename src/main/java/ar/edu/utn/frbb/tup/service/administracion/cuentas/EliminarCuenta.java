package ar.edu.utn.frbb.tup.service.administracion.cuentas;

import ar.edu.utn.frbb.tup.exception.CuentasVaciasException;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.service.administracion.BaseAdministracion;
import ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;

import static ar.edu.utn.frbb.tup.presentation.BasePresentation.pedirCvu;
import static ar.edu.utn.frbb.tup.presentation.BasePresentation.pedirDni;

@Service
public class EliminarCuenta extends BaseAdministracion {
    ClienteDao clienteDao;
    CuentaDao cuentaDao;
    MovimientosDao movimientosDao;

    public EliminarCuenta(ClienteDao clienteDao, CuentaDao cuentaDao, MovimientosDao movimientosDao) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.movimientosDao = movimientosDao;
    }

    public void eliminarCuenta() {

        while (true) {

            long dni = pedirDni("Escriba el DNI al cliente para eliminar una cuenta: (0 para salir) ");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            Cliente cliente = clienteDao.findCliente(dni);

            try {
                if (cliente == null) {
                    //Lanzo excepcion si el cliente no fue encontrado
                    throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
                }

                List<Long> cuentasCvu = cuentaDao.getRelacionesDni(dni); //Valido si el DNI tiene cuentas asociadas
                if (cuentasCvu.isEmpty()) {
                    throw new CuentasVaciasException("No hay cuentas asociadas al cliente con DNI: " + dni);
                }

                //Pido CVU de la cuenta que quiere eliminar
                long cvu = pedirCvu("Escriba el CVU de la cuenta que quiere eliminar: (0 para salir) ");
                clearScreen();

                if (cvu == 0) break; //Si escribe 0 termina con el bucle

                //Funcion que devuelve la cuenta encontrada o vuelve Null si no lo encontro, solo devuelve las cuentas que tiene asocida el cliente
                Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

                if (cuenta == null){
                    //Lanzo excepcion si la cuenta no fue encontrada
                    throw new CuentaNoEncontradaException("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
                }

                //Borro la cuenta en Cuentas y Movimientos de esta misma
                cuentaDao.deleteCuenta(cvu);
                movimientosDao.deleteMovimiento(cvu);

                System.out.println("------------ Cuenta eliminada -----------");
                System.out.println(toString(cuenta));


            } catch (ClienteNoEncontradoException | CuentaNoEncontradaException | CuentasVaciasException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
            } finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
