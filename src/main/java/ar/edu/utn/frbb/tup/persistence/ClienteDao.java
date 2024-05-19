package ar.edu.utn.frbb.tup.persistence;


import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.TipoPersona;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private final String RUTA_ARCHIVO = "src/main/java/ar/edu/utn/frbb/tup/persistence/data/cliente.txt";

    public void inicializarClientes() {

        PrintWriter writer = null;
        try {
            //Me fijo si el archivo existe
            File archivo = new File(RUTA_ARCHIVO);

            if (!archivo.exists()) {
                //Si no existe, lo creo y guardo el Encabezado para saber el orden de los datos
                FileWriter fileWriter = new FileWriter(RUTA_ARCHIVO, true);
                writer = new PrintWriter(fileWriter);
                writer.println("DNI, Nombre, Apellido, Direccion, Fecha nacimiento, Mail, Banco, Tipo Persona, Fecha alta");
            }

        } catch (IOException e) {
            System.out.println("No se pudo abrir el archivo");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void saveCliente(Cliente cliente) {
        try {
            FileWriter archivo = new FileWriter(RUTA_ARCHIVO, true);
            PrintWriter writer = new PrintWriter(archivo);

            writer.println(cliente.getDni() + "," + cliente.getNombre() + "," + cliente.getApellido() + "," + cliente.getDireccion() + "," + cliente.getFechaNacimiento() + "," + cliente.getMail() + "," + cliente.getBanco() + "," + cliente.getTipoPersona() + "," + cliente.getFechaAlta());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCliente(Long dni){

        try {
            File file = new File(RUTA_ARCHIVO);

            StringBuilder contenido = new StringBuilder(); //Creo el contenido para guardar todoo el archivo menos el cliente que quiero eliminar
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            //Primero agrego el encabezado al contenido,
            String linea = reader.readLine();
            contenido.append(linea).append("\n");

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[0]) != dni){ //Voy agregando todas las lineas del Archivo excepto las lineas que tengo que eliminar con el DNI dado
                    contenido.append(linea).append("\n"); //Agrego la linea al contenido
                }
            }

            FileWriter fileWriter = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fileWriter);

            //Escribo todoo el contenido excepto el cliente que quiero eliminar
            writer.write(contenido.toString());

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente findCliente(Long dni) {
        try {
            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de los clientes, y cada linea la divido por comas con el '.split(",")', para tener los datos del cliente
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[0]) == dni) {
                    reader.close();
                    return parseStringToCliente(datos);
                }
            }
            reader.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Cliente> findAllClientes(){
        List<Cliente> clientes = new ArrayList<>();

        try {

            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de los clientes, y cada linea la divido por comas con el '.split(",")', para tener los datos del cliente
                String[] datos = linea.split(",");

                //Guardo en una lista todos los clientes
                clientes.add(parseStringToCliente(datos));

            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }

    //Funcion para parsear los datos leidos del archivo a un objeto cliente
    public Cliente parseStringToCliente(String[] datos) {
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
