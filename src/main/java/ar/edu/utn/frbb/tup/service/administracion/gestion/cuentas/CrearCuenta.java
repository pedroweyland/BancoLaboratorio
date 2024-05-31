package ar.edu.utn.frbb.tup.service.administracion.gestion.cuentas;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import ar.edu.utn.frbb.tup.presentation.input.ClienteInput;
import ar.edu.utn.frbb.tup.service.administracion.gestion.BaseGestion;
import ar.edu.utn.frbb.tup.presentation.input.CuentaInput;
import ar.edu.utn.frbb.tup.service.exception.ClienteNoEncontradoException;
import org.springframework.stereotype.Service;

import static ar.edu.utn.frbb.tup.presentation.input.BaseInput.pedirDni;

@Service
public class CrearCuenta extends BaseGestion {
    ClienteDao clienteDao;
    CuentaDao cuentaDao;
    CuentaInput cuentaInput;

    public CrearCuenta(ClienteDao clienteDao, CuentaDao cuentaDao, CuentaInput cuentaInput) {
        this.clienteDao = clienteDao;
        this.cuentaDao = cuentaDao;
        this.cuentaInput = cuentaInput;
    }

    public void crearCuenta() {

        boolean seguir = true;

        while (seguir) {

            long dni = pedirDni("Escriba el DNI del cliente para crearle una cuenta: (0 para salir)");

            clearScreen();
            if (dni == 0) break; //Si escribe 0 termina con el bucle

            Cliente cliente = clienteDao.findCliente(dni);

            try {
                if (cliente == null){
                    throw new ClienteNoEncontradoException("No se encontro el cliente con el DNI: " + dni);
                }

                //Usuario ingresa los datos y se guarda en la variable cliente
                Cuenta cuenta = cuentaInput.creacionCuenta(dni);

                //Agrego la cuenta al archivo
                cuentaDao.saveCuenta(cuenta);

                //Muestro en pantalla el resultado
                System.out.println("----- Cuenta creada del cliente " + cliente.getNombre() + " -----");
                System.out.println(toString(cuenta));
                System.out.println("-------- Cuenta creada con exito --------");
                System.out.println("----------------------------------------");

                seguir = false;

            } catch (ClienteNoEncontradoException e) {
                System.out.println("----------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("----------------------------------------");
            }  finally {
                System.out.println("Enter para seguir");
                scanner.nextLine();
                clearScreen();
            }
        }
    }
}
