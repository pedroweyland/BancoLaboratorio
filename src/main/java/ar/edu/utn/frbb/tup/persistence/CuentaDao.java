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

            if (!archivo.exists()) {
                //Si no existe, lo creo y guardo el Encabezado para saber el orden de los datos
                FileWriter fileWriter = new FileWriter(RUTA_ARCHIVO, true);
                writer = new PrintWriter(fileWriter);
                writer.println("CVU, DNI titular, nombre, estado, saldo, fecha creacion, tipo de cuenta");
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

            writer.println(cuenta.getCVU() + "," + cuenta.getDniTitular() + "," + cuenta.getNombre() + "," + cuenta.getEstado() + "," + cuenta.getSaldo() + "," + cuenta.getFechaCreacion() + "," + cuenta.getTipoCuenta());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCuenta(long CVU){

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

                if (Long.parseLong(datos[0]) != CVU){ //Voy agregando todas las lineas del Archivo excepto las lineas que tengo que eliminar con el CVU dado
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

    public Cuenta findCuenta(long CVU) {
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
        //Retorno Null por si no lo encuentra
        return null;
    }

    public Cuenta findCuentaDelCliente(long cvu, long dni){
        //Funcion para encontrar la cuenta del cliente, se hace para que el usuario SOLO pueda eliminar la cuenta del cliente que le pertenece
        try {
            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de las relaciones, y cada linea la divido por comas con el '.split(",")', para tener los datos de las relaciones
                String[] datos = linea.split(",");

                //Retorno la cuenta si se encuentra la cuenta del cliente ingresado
                if (Long.parseLong(datos[0]) == cvu && Long.parseLong(datos[1]) == dni) {
                    return parseStringToCuenta(datos);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Retorno Null por si no lo encuentra
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

    public List<Long> getRelacionesDni(Long dni){
        //Funcion para guardar todos los CVUs que tiene el dni ingresado

        List<Long> CvuRelacionados = new ArrayList<>();
        try {
            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            //Primero agrego el encabezado al contenido,
            String linea = reader.readLine();

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[1]) == dni){ //Agrego la el cvu relacionado con el dni
                    CvuRelacionados.add(Long.parseLong(datos[0]));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return CvuRelacionados;

    }
    public Cuenta parseStringToCuenta(String[] datos) {
        Cuenta cuenta = new Cuenta();
        cuenta.setCVU(Long.parseLong(datos[0]));
        cuenta.setDniTitular(Long.parseLong(datos[1]));
        cuenta.setNombre(datos[2]);
        cuenta.setEstado(Boolean.parseBoolean(datos[3]));
        cuenta.setSaldo(Double.parseDouble(datos[4]));
        cuenta.setFechaCreacion(LocalDate.parse(datos[5]));
        cuenta.setTipoCuenta(TipoCuenta.valueOf(datos[6]));

        return cuenta;
    }
}
