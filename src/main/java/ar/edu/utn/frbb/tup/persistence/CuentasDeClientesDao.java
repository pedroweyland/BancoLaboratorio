package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;

import java.io.*;

public class CuentasDeClientesDao {
    //Clase para tener tener la relacion que existe entre clientes y cuentas
    //Si aparece un DNI con un CVU significa que el cliente tiene una cuenta con el CVU que este

    private final String RUTA_ARCHIVO = "src/main/java/ar/edu/utn/frbb/tup/persistence/data/cuentasDeClientes.txt";

    public void inicializarRelaciones(){
        PrintWriter writer = null;
        try {
            //Me fijo si el archivo existe
            File archivo = new File(RUTA_ARCHIVO);
            boolean existe = archivo.exists();

            //Si no existe, lo creo y guardo el Encabezado para saber el orden de los datos
            FileWriter fileWriter = new FileWriter(RUTA_ARCHIVO, true);
            writer = new PrintWriter(fileWriter);

            if (!existe) {
                writer.println("DNI, CVU");
            }

        } catch (IOException e) {
            System.out.println("No se pudo abrir el archivo");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void saveRelacion(Long dni, Long cvu) {
        try {
            FileWriter archivo = new FileWriter(RUTA_ARCHIVO, true);
            PrintWriter writer = new PrintWriter(archivo);

            writer.println(dni + "," + cvu);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRelacion(Long CVU){

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

                if (Long.parseLong(datos[1]) == CVU){ //Si encuentra el CVU que ingreso el cliente, no lo agrego en el contenido por ende lo elimino
                    continue;
                } else {
                    contenido.append(linea).append("\n"); //Agrego la linea al contenido
                }
            }

            FileWriter fileWriter = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fileWriter);

            //Escribo todoo el contenido excepto la relacion que quiero eliminar
            writer.write(contenido.toString());

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean findRelacion(Long dni) {
        //Si encuentra una relacion con el DNI ingresado y una cuenta retorna true, de lo contrario retorna false
        try {
            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de las relaciones, y cada linea la divido por comas con el '.split(",")', para tener los datos de las relaciones
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[0]) == dni) {
                    reader.close();
                    return true;
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean relacionDniYCbu(long dni, Long cvu){
        //Funcion para Saber si el dni y cvu ingresado tienen relacion
        try {
            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de las relaciones, y cada linea la divido por comas con el '.split(",")', para tener los datos de las relaciones
                String[] datos = linea.split(",");

                //Retorna True si encuentra el que el Dni tiene un CVU
                if (Long.parseLong(datos[0]) == dni && Long.parseLong(datos[1]) == cvu) {
                    return true;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

}
