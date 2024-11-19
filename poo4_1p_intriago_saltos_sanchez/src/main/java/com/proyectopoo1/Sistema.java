package com.proyectopoo1;
import java.util.ArrayList;


public class Sistema {
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Espacio> espacios;
    public static ArrayList<Reserva> reservas;

    public static boolean validarCuenta(String user, String contrasenia){

        for(String linea: ManejoArchivo.leerArchivo("usuarios.txt")){
            String[] datos = linea.split(" \\| ");
            if(datos[4].equals(user) && datos[5].equals(contrasenia)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        System.out.println(validarCuenta("amolina", "molina456"));
        
    }


}
