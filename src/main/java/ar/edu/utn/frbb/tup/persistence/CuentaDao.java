package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CuentaDao {
    private final String RUTA_ARCHIVO = "src/main/java/ar/edu/utn/frbb/tup/persistence/data/cuentas.txt";

    public void inicializarCuentas(){

        PrintWriter writer = null;
        try {
            //Me fijo si el archivo existe
            File archivo = new File(RUTA_ARCHIVO);
            boolean existe = archivo.exists();

            //Si no existe, lo creo y guardo el Encabezado para saber el orden de los datos
            FileWriter fileWriter = new FileWriter(RUTA_ARCHIVO, true);
            writer = new PrintWriter(fileWriter);

            if (!existe) {
                writer.println("CVU, nombre, estado, saldo, fecha creacion, tipo de cuenta");
            }

        } catch (IOException e) {
            System.out.println("No se pudo abrir el archivo");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void saveCuenta(Cuenta cuenta) {
        try {
            FileWriter archivo = new FileWriter(RUTA_ARCHIVO, true);
            PrintWriter writer = new PrintWriter(archivo);

            writer.println(cuenta.getCVU() + "," + cuenta.getNombre() + "," + cuenta.getEstado() + "," + cuenta.getSaldo() + "," + cuenta.getFechaCreacion() + "," + cuenta.getTipoCuenta());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCuenta(Long CVU){

        try {
            File file = new File(RUTA_ARCHIVO);

            StringBuilder contenido = new StringBuilder(); //Creo el contenido para guardar todoo el archivo menos el Cuenta que quiero eliminar
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            //Primero agrego el encabezado al contenido,
            String linea = reader.readLine();
            contenido.append(linea).append("\n");

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[0]) == CVU){ //Si encuentra el CVU que ingreso el cliente, no lo agrego en el contenido por ende lo elimino
                    continue;
                } else {
                    contenido.append(linea).append("\n"); //Agrego la linea al contenido
                }
            }

            FileWriter fileWriter = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fileWriter);

            //Escribo todoo el contenido excepto la cuenta que quiero eliminar
            writer.write(contenido.toString());

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Cuenta findCuenta(Long CVU) {
        try {
            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de las cuentas, y cada linea la divido por comas con el '.split(",")', para tener los datos de la cuenta
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[0]) == CVU) {
                    reader.close();
                    return parseStringToCuenta(datos);
                }
            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Cuenta> findAllCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        try {

            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de las cuentas, y cada linea la divido por comas con el '.split(",")', para tener los datos de la cuenta
                String[] datos = linea.split(",");

                //Guardo en una lista todos las cuentas
                cuentas.add(parseStringToCuenta(datos));

            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cuentas;
    }

    public Cuenta parseStringToCuenta(String[] datos) {
        Cuenta cuenta = new Cuenta();
        cuenta.setCVU(Long.parseLong(datos[0]));
        cuenta.setNombre(datos[1]);
        cuenta.setEstado(Boolean.parseBoolean(datos[2]));
        cuenta.setSaldo(Double.parseDouble(datos[3]));
        cuenta.setFechaCreacion(LocalDate.parse(datos[4]));
        cuenta.setTipoCuenta(TipoCuenta.valueOf(datos[5]));

        return cuenta;
    }
}
