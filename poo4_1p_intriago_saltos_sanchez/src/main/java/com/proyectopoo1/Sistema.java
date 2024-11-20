package com.proyectopoo1;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Sistema {
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Espacio> espacios = new ArrayList<>();
    public static ArrayList<Reserva> reservas = new ArrayList<>(); 


    public static boolean validarCuenta(String user, String contrasenia){
        for (Usuario usuario : usuarios) {
            if(usuario.user.equals(user) & usuario.password.equals(contrasenia)){
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        
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
                        Sistema.usuarios.add(new Administrador(Cargo.valueOf(info[4]),datos[0],datos[1],datos[2],datos[3],datos[4],datos[5],datos[6]));
                    }
                }
            }
        }

        // carga de Arraylist de espacios 
        for(String linea: ManejoArchivo.leerArchivo("espacios.txt")){
            String[] datos = linea.split(" \\| ");
            espacios.add(new Espacio(datos[0], TipoEspacio.valueOf(datos[1]), datos[2], Integer.parseInt(datos[3]), DisponibilidadEsp.valueOf(datos[4]), UsuarioPermitido.valueOf(datos[5])));

            }







        Scanner sc = new Scanner(System.in);



        // Inicio de Sesión
        System.out.print("Ingrese su usuario: ");
        String userIn = sc.nextLine(); 
        System.out.print("Ingrese su contraseña: ");
        String passwordIn = sc.nextLine();

        while (Sistema.validarCuenta(userIn, passwordIn) == false) {
            System.out.println("\nLas credenciales ingresadas no pertenecen a ningun usuario");
            System.out.print("Ingrese su usuario: ");
            userIn = sc.nextLine(); 
            System.out.print("Ingrese su contraseña: ");
            passwordIn = sc.nextLine();
        }

           
        



     
       
        sc.close();
    }


}
