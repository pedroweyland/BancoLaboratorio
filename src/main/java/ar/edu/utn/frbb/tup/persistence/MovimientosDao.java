package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Movimiento;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MovimientosDao {
    private final String RUTA_ARCHIVO = "src/main/java/ar/edu/utn/frbb/tup/persistence/data/movimientos.txt";

    public void inicializarMovimientos(){
        PrintWriter writer = null;

        try {
            //Me fijo si el archivo existe
            File archivo = new File(RUTA_ARCHIVO);

            if (!archivo.exists()) {
                //Si no existe, lo creo y guardo el Encabezado para saber el orden de los datos
                FileWriter fileWriter = new FileWriter(RUTA_ARCHIVO, true);
                writer = new PrintWriter(fileWriter);
                writer.println("CVU Origen, fecha Operacion, hora Operacion, tipo operacion, monto");
            }

        } catch (IOException e) {
            System.out.println("No se pudo abrir el archivo");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void saveMovimiento(Movimiento movimiento) {
        try {
            FileWriter archivo = new FileWriter(RUTA_ARCHIVO, true);
            PrintWriter writer = new PrintWriter(archivo);

            writer.println(movimiento.getCVU() + "," + movimiento.getFechaOperacion() + "," + movimiento.getHoraOperacion() + "," + movimiento.getTipoOperacion() + "," + movimiento.getMonto());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMovimiento(Long cvu){
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

                if (Long.parseLong(datos[0]) != cvu){ //Voy agregando todas las lineas del Archivo excepto las lineas que tengo que eliminar con el CVU dado
                    contenido.append(linea).append("\n"); //Agrego la linea al contenido
                }
            }

            FileWriter fileWriter = new FileWriter(file);
            PrintWriter writer = new PrintWriter(fileWriter);

            //Escribo todoo el contenido excepto el movimiento que quiero eliminar
            writer.write(contenido.toString());

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Movimiento> findMovimientos(long CVU){
        List<Movimiento> movimientos = new ArrayList<>();
        try {

            File file = new File(RUTA_ARCHIVO);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String linea; //Leo el encabezado
            linea = reader.readLine(); //Salto encabezado

            while ((linea = reader.readLine()) != null) { //Condicion para que lea el archivo hasta el final y lo guarde en la variable linea

                //Empiezo a leer las lineas de los movimientos, y cada linea la divido por comas con el '.split(",")', para tener los datos de los movimientos
                String[] datos = linea.split(",");

                if (Long.parseLong(datos[0]) == CVU){
                    //Guardo en una lista todos los movimientos del cvu ingresado
                    movimientos.add(parseStringToMovimiento(datos));
                }

            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movimientos;
    }

    public Movimiento parseStringToMovimiento(String[] datos){
        Movimiento movimiento = new Movimiento();

        movimiento.setCVU(Long.parseLong(datos[0]));
        movimiento.setFecha(LocalDate.parse(datos[1]));
        movimiento.setHora(LocalTime.parse(datos[2]));
        movimiento.setTipoOperacion(datos[3]);
        movimiento.setMonto(Double.parseDouble(datos[4]));

        return movimiento;
    }

}
