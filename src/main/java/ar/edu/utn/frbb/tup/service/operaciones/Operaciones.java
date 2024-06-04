package ar.edu.utn.frbb.tup.service.operaciones;

import ar.edu.utn.frbb.tup.exception.*;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.operaciones.modulos.*;
import org.springframework.stereotype.Component;

@Component
public class Operaciones extends baseOperaciones {

    Deposito deposito;
    Retiro retiro;
    Transferencia transferencia;
    Consulta consulta;
    MostrarMovimientos movimientos;
    ClienteDao clienteDao;
    CuentaDao cuentaDao;

    public Operaciones(Deposito deposito, Retiro retiro, Transferencia transferencia, Consulta consulta, MostrarMovimientos movimientos, ClienteDao clienteDao, CuentaDao cuentaDao) {
        this.deposito = deposito;
        this.retiro = retiro;
        this.transferencia = transferencia;
        this.consulta = consulta;
        this.movimientos = movimientos;
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
    }

    //Funcion para que el usaurio encuentre la cuenta que desea operar, y retorna la cuenta o null si no la encuentra
    public Cuenta cuentaOperar(long dni, long cvu) throws ClienteNoEncontradoException, CuentaNoEncontradaException, CuentaEstaDeBajaException {
        while (true) {

            Cliente cliente = clienteDao.findCliente(dni);

            if (cliente == null){ //Si no se encuentra el cliente lanza una excepcion
                throw new ClienteNoEncontradoException("No se encontro ningun cliente con el DNI dado");
            }

            Cuenta cuenta = cuentaDao.findCuentaDelCliente(cvu, dni);

            if (cuenta == null) { //Si no se encontro la cuenta lanza una excepcion
                throw new CuentaNoEncontradaException("El Cliente no tiene ninguna cuenta con el CVU: " + cvu);
            }

            if (!cuenta.getEstado()){ //Si la cuenta esta dada de baja lanzo una excepcion
                throw new CuentaEstaDeBajaException("La cuenta esta de baja, no puede operar.");
            }

            return cuenta;

        }
    }

    public Cuenta cuentaATransferir(long cvu) throws CuentaNoEncontradaException {

        if (cvu == 0) {
            System.out.println("Saliendo...");
            return null;
        }

        Cuenta cuenta = cuentaDao.findCuenta(cvu);

        if (cuenta == null){
            throw new CuentaNoEncontradaException("No se encontro ninguna cuenta con el CVU dado " + cvu);
        } else {
            return cuenta;
        }
    }
}
