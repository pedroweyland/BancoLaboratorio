package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public class ClienteDao extends BaseDao<Cliente>{
    private final String RUTA_ARCHIVO = "src/main/java/ar/edu/utn/frbb/tup/persistence/data/cliente.txt";

    private final CuentaDao cuentaDao;

    public ClienteDao(CuentaDao cuentaDao) {
        this.cuentaDao = cuentaDao;
    }

    public void inicializarClientes(){
        String encabezado = "DNI, Nombre, Apellido, Direccion, Fecha nacimiento, Mail, Banco, Tipo Persona, Fecha alta";
        inicializarArchivo(encabezado, RUTA_ARCHIVO);
    }

    public void saveCliente(Cliente cliente) {

        String infoAguardar = cliente.getDni() + "," + cliente.getNombre() + "," + cliente.getApellido() + "," + cliente.getDireccion() + "," + cliente.getFechaNacimiento() + "," + cliente.getMail() + "," + cliente.getBanco() + "," + cliente.getTipoPersona() + "," + cliente.getFechaAlta();

        saveInfo(infoAguardar, RUTA_ARCHIVO);
    }

    public void deleteCliente(Long dni) {

        deleteInfo(dni, RUTA_ARCHIVO);

    }

    public Cliente findCliente(Long dni){
        Cliente cliente = findInfo(dni, RUTA_ARCHIVO);
        Set<Cuenta> cuentas = cuentaDao.findAllCuentasDelCliente(dni);

        if (!cuentas.isEmpty()){
            cliente.setCuentas(cuentas);
        }

        return cliente;
    }

    public List<Cliente> findAllClientes() {
        List<Cuenta> cuentas = cuentaDao.findAllCuentas();

        List<Cliente> clientes = findAllInfo(RUTA_ARCHIVO);

        if (!cuentas.isEmpty()){
            for (Cliente cliente : clientes){
                for (Cuenta cuenta : cuentas){
                    if (cuenta.getDniTitular() == cliente.getDni()){
                        cliente.addCuenta(cuenta);
                    }
                }
            }
        }
        return clientes;
    }

    //Funcion para parsear los datos leidos del archivo a un objeto tipo 'Cliente'
    @Override
    public Cliente parseDatosToObjet(String[] datos){
        Cliente cliente = new Cliente();

        cliente.setDni(Long.parseLong(datos[0]));
        cliente.setNombre(datos[1]);
        cliente.setApellido(datos[2]);
        cliente.setDireccion(datos[3]);
        cliente.setFechaNacimiento(LocalDate.parse(datos[4]));
        cliente.setMail(datos[5]);
        cliente.setBanco(datos[6]);
        cliente.setTipoPersona(TipoPersona.valueOf(datos[7]));
        cliente.setFechaAlta(LocalDate.parse(datos[8]));

        //Retorno el cliente leido
        return cliente;
    }
}
