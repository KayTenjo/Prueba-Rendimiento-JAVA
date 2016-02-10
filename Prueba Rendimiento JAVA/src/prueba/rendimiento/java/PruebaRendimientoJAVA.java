/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba.rendimiento.java;
import api.Glove;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kayjt
 */
public class PruebaRendimientoJAVA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        
        
        List<Integer> positivePins = Arrays.asList(10, 9, 6, 5, 3 );
        List<Integer> negativePins = Arrays.asList(15, 14, 12, 8, 2 );
        List<String> negativeInit = Arrays.asList("LOW", "LOW", "LOW", "LOW", "LOW");
        int cantidadMotores = 5;
        int numeroPruebas = 3;
        
        


        Glove glove = new Glove();
        for (String port : glove.getPortNames()) {

            System.out.println(port);
        }

        boolean isOpen = glove.openPort("COM3", 38400);
        
        if(isOpen){
            
            System.out.println("Puerto abierto");
        }
        
        else{
            System.out.println("Imposible abrir puerto");
            return;
        }
        Thread.sleep(2000);
        glove.initializeMotor(positivePins);
        glove.initializeMotor(negativePins);
        glove.activateMotor(negativePins, negativeInit);
        glove.activateMotor(negativePins, negativeInit);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();
        String fecha = dateFormat.format(date);
        System.out.println(dateFormat.format(date));
        for (int i = 0; i < cantidadMotores; i++) {
            int indice = i + 1;

            String nombreArchivoDigital = "prueba-" +fecha +"-"+ indice + "Motor-Digital.txt";
            System.out.println(nombreArchivoDigital);
            FileWriter fichero = new FileWriter(nombreArchivoDigital);
            PrintWriter writerDigital = new PrintWriter(fichero);
            
            
            writerDigital.println("TiempoGeneracionYEnvioMensaje,TiempoActivacionActuadores,TiempoTotal");

           
            Integer[] pinesPruebaDigitalArray = new Integer[i + 1];
            String[] valuesONPruebaDigitalArray = new String[i + 1];
            String[] valuesOFFPruebaDigitalArray = new String[i + 1];
            
            List<Integer> pinesPruebaDigital;
            List<String> valuesONPruebaDigital;
            List<String> valuesOFFPruebaDigital;
                    
            for (int j = 0; j < indice ; j++ ) {

                pinesPruebaDigitalArray[j] = positivePins.get(j);
                valuesONPruebaDigitalArray[j] = "HIGH";
                valuesOFFPruebaDigitalArray[j] = "LOW";
            }
            
            pinesPruebaDigital = Arrays.asList(pinesPruebaDigitalArray);
            valuesONPruebaDigital = Arrays.asList(valuesONPruebaDigitalArray);
            valuesOFFPruebaDigital = Arrays.asList(valuesOFFPruebaDigitalArray);
            for (int k = 0; k < numeroPruebas; k++) {

                long startTime = System.nanoTime();
                glove.activateMotorTimeTest(pinesPruebaDigital, valuesONPruebaDigital);
                long stopTime = System.nanoTime();
                long tiempoMensaje =  (stopTime - startTime)/1000;
                long tiempoActivacion = Long.valueOf(glove.readLine());
                long tiempoTotal = tiempoMensaje + tiempoActivacion; 
                writerDigital.println(tiempoMensaje + "," + tiempoActivacion + "," +tiempoTotal);
                Thread.sleep(200);
                glove.activateMotor(pinesPruebaDigital, valuesOFFPruebaDigital);
                Thread.sleep(500);
            }

            writerDigital.close();
                 
            String nombreArchivoAnalogo = "prueba-" +fecha +"-"+ indice + "Motor-Analogo.txt";
            System.out.println(nombreArchivoAnalogo);
            FileWriter ficheroAnalogo = new FileWriter(nombreArchivoAnalogo);
            PrintWriter writerAnalogo = new PrintWriter(ficheroAnalogo);
            
            
            writerAnalogo.println("TiempoGeneracionYEnvioMensaje,TiempoActivacionActuadores,TiempoTotal");

           
            Integer[] pinesPruebaAnalogoArray = new Integer[i + 1];
            String[] valuesONPruebaAnalogoArray = new String[i + 1];
            String[] valuesOFFPruebaAnalogoArray = new String[i + 1];
            
            List<Integer> pinesPruebaAnalogo;
            List<String> valuesONPruebaAnalogo;
            List<String> valuesOFFPruebaAnalogo;
                    
            for (int j = 0; j < indice ; j++ ) {

                pinesPruebaAnalogoArray[j] = positivePins.get(j);
                valuesONPruebaAnalogoArray[j] = "HIGH";
                valuesOFFPruebaAnalogoArray[j] = "LOW";
            }
            
            pinesPruebaAnalogo = Arrays.asList(pinesPruebaAnalogoArray);
            valuesONPruebaAnalogo = Arrays.asList(valuesONPruebaAnalogoArray);
            valuesOFFPruebaAnalogo = Arrays.asList(valuesOFFPruebaAnalogoArray);
            for (int k = 0; k < numeroPruebas; k++) {

                long startTime = System.nanoTime();
                glove.activateMotorTimeTest(pinesPruebaAnalogo, valuesONPruebaAnalogo);
                long stopTime = System.nanoTime();
                long tiempoMensaje =  (stopTime - startTime)/1000;
                long tiempoActivacion = Long.valueOf(glove.readLine());
                long tiempoTotal = tiempoMensaje + tiempoActivacion; 
                writerAnalogo.println(tiempoMensaje + "," + tiempoActivacion + "," +tiempoTotal);
                Thread.sleep(200);
                glove.activateMotor(pinesPruebaAnalogo, valuesOFFPruebaAnalogo);
                Thread.sleep(500);
            }

            writerAnalogo.close();
        }
        
        
    }
    
}
