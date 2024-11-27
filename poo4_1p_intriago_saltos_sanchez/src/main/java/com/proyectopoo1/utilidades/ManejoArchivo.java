package com.proyectopoo1.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ManejoArchivo {

    // Método para lectura de archivos
     public static ArrayList<String> leerArchivo(String ruta) {
        ArrayList<String> lineas = new ArrayList<>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(ruta);  
            fr = new FileReader(archivo,StandardCharsets.UTF_8);
            br = new BufferedReader(fr);

 
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);

            }

        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {

            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace(System.out);
            }
        }
        return lineas;
    }

    public static void escribirArchivo(String nombreArchivo, ArrayList<String> datosAEscr) {

        FileWriter fichero = null;
        BufferedWriter bw = null;
      
        try {
            fichero = new FileWriter(nombreArchivo);
            bw = new BufferedWriter(fichero);
            for(String linea: datosAEscr){
                bw.write(linea+"\n");
            }
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace(System.out);
        } finally {
            try {

                if (null != fichero) {
                    bw.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace(System.out);
            }
        }
    }

}
