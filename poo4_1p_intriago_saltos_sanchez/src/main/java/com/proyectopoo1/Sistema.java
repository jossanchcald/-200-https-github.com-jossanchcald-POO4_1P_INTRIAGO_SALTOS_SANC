package com.proyectopoo1;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Sistema {
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Espacio> espacios = new ArrayList<>();
    public static ArrayList<Reserva> reservas = new ArrayList<>(); 


    public static boolean validarCuenta(String user, String contrasenia){
        for (Usuario usuario : usuarios) {
            if(usuario.getUser().equals(user) & usuario.getPassword().equals(contrasenia)){
                return true;
            }
        }
        return false;
    }

    public static Usuario iniciarSesion(String user, String contrasenia){
        for(Usuario usuario: usuarios){
            if(usuario.getUser().equals(user) & usuario.getPassword().equals(contrasenia)){
                return usuario;
            }
        }
        return null;
    }



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        // carga de ArrayList de usuarios
        for(String linea: ManejoArchivo.leerArchivo("usuarios.txt")){
            String[] datos = linea.split(" \\| ");

            switch(datos[7]){
                case "E":
                for(String line: ManejoArchivo.leerArchivo("estudiantes.txt")){
                    String[] info = line.split(" \\| ");
                    if(datos[2].equals(info[2]) && datos[3].equals(info[3])){
                        Sistema.usuarios.add(new Estudiante(info[4], info[5],datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6]));
                    }             
                }

                case "P":
                for(String line: ManejoArchivo.leerArchivo("profesores.txt")){
                    String[] info = line.split(" \\| ");
                    if(datos[2].equals(info[2]) && datos[3].equals(info[3])){
                        ArrayList<String> materias = new ArrayList<>(Arrays.asList(info[5].split(",\\s+")));
                        Sistema.usuarios.add(new Profesor(info[4],materias ,datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6]));
                    }
                }

                case "A":
                for(String line: ManejoArchivo.leerArchivo("administradores.txt")){
                    String[] info = line.split(" \\| ");
                    if(datos[2].equals(info[2]) && datos[3].equals(info[3])){
                        Sistema.usuarios.add(new Administrador(info[4],datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6]));
                    }
                }
                default:
                    break;
            }
        }
        
        // carga de Arraylist de espacios 
        for(String linea: ManejoArchivo.leerArchivo("espacios.txt")){
            String[] datos = linea.split(" \\| ");
            espacios.add(new Espacio(datos[0], TipoEspacio.valueOf(datos[1]), datos[2], Integer.parseInt(datos[3]), DisponibilidadEsp.valueOf(datos[4]), UsuarioPermitido.valueOf(datos[5])));
        }
       
        // PROGRAMA PRINCIPAL
        System.out.println("BIENVENIDO AL SISTEMA DE RESERVAS DE ESPACIOS -ESPOL-");
        boolean booly;
        String userIn;
        String passwordIn;

        do{
            System.out.print("Ingrese su usuario: ");
            userIn = sc.nextLine(); 
            System.out.print("Ingrese su contraseña: ");
            passwordIn = sc.nextLine();
            booly = validarCuenta(userIn, passwordIn);

            if(booly==false){
                System.out.println("Credenciales incorrectas");
            }

        }while(booly==false);
        Usuario user = iniciarSesion(userIn, passwordIn);
        
        do{
            // cargar reserva
            for(String linea: ManejoArchivo.leerArchivo("reservas.txt")){
                String[] datos = linea.split(" \\| ");
                Usuario userR = null;
                Espacio espacio = null;
                for(Usuario u: usuarios){
                    if(u.getCodUnico().equals(datos[1])){
                        userR = u;
                    }
                }

                for(Espacio e: espacios){
                    if(e.getCodUnico().equals(datos[4])){
                        espacio = e;
                    }
                }
                reservas.add(new Reserva(datos[0], userR, datos[3],espacio,EstadoReserva.valueOf(datos[6]),datos[7]));

            }   
         
        }

    
 
      
    
 
       

           

       
    
    }


}
