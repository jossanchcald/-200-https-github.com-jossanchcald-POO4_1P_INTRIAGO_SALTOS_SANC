package com.proyectopoo1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Sistema {
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Espacio> espacios;
    public static ArrayList<Reserva> reservas; 

    // Metodo para validar cuenta 
    public static boolean validarCuenta(String user, String contrasenia){
        for (Usuario usuario : usuarios) {
            if(usuario.getUser().equals(user) & usuario.getPassword().equals(contrasenia)){
                return true;
            }
        }
        return false;
    }
 
    // Método para iniciar sesión
    public static Usuario iniciarSesion(String user, String contrasenia){
        for(Usuario usuario: usuarios){
            if(usuario.getUser().equals(user) & usuario.getPassword().equals(contrasenia)){
                return usuario;
            }
        }
        return null;
    }


    // Método para cargar archivo de usuario
    public static void cargarUsuario(){
        usuarios = new ArrayList<>();
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

    }


    // Método para cargar espacios 
    public static void cargarEspacio(){
        espacios = new ArrayList<>();
        for(String linea: ManejoArchivo.leerArchivo("espacios.txt")){
            String[] datos = linea.split(" \\| ");
            espacios.add(new Espacio(datos[0], TipoEspacio.valueOf(datos[1]), datos[2], Integer.parseInt(datos[3]), DisponibilidadEsp.valueOf(datos[4]), UsuarioPermitido.valueOf(datos[5])));
        }
    }

    // Método para cargar reservas
    public static void cargarReserva(){
        reservas = new ArrayList<>();
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
            reservas.add(new Reserva(datos[0], userR, LocalDate.parse(datos[3]),espacio,EstadoReserva.valueOf(datos[6]),datos[7]));

        }   
    }


    // PROGRAMA PRINCIPAL
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        cargarUsuario();

       
        // PROGRAMA PRINCIPAL
        System.out.println(
                        "██████╗░███████╗░██████╗███████╗██████╗░██╗░░░██╗███████╗██████╗░░█████╗░██╗░░░░░\r\n" + 
                        "██╔══██╗██╔════╝██╔════╝██╔════╝██╔══██╗██║░░░██║██╔════╝██╔══██╗██╔══██╗██║░░░░░\r\n" + 
                        "██████╔╝█████╗░░╚█████╗░█████╗░░██████╔╝╚██╗░██╔╝█████╗░░██████╔╝██║░░██║██║░░░░░\r\n" + 
                        "██╔══██╗██╔══╝░░░╚═══██╗██╔══╝░░██╔══██╗░╚████╔╝░██╔══╝░░██╔═══╝░██║░░██║██║░░░░░\r\n" + 
                        "██║░░██║███████╗██████╔╝███████╗██║░░██║░░╚██╔╝░░███████╗██║░░░░░╚█████╔╝███████╗\r\n" + 
                        "╚═╝░░╚═╝╚══════╝╚═════╝░╚══════╝╚═╝░░╚═╝░░░╚═╝░░░╚══════╝╚═╝░░░░░░╚════╝░╚══════╝╝"
                        );

        System.out.println("\n\t\t   Sistema de Reserva de Espacios de ESPOL\n\n");

        boolean booly = false;
        String userIn;
        String passwordIn;
        

        do{
            System.out.print("Ingrese su usuario: ");
            userIn = sc.nextLine(); 
            System.out.print("Ingrese su contraseña: ");
            passwordIn = sc.nextLine();
            booly = validarCuenta(userIn, passwordIn);

            if(booly==false){
                System.out.println("Credenciales incorrectas\n");
            }

        }while(booly==false);

        Usuario user = iniciarSesion(userIn, passwordIn);

        do { 
            cargarEspacio();
            cargarReserva();

            if(user instanceof Estudiante || user instanceof Profesor){
                System.out.println("\n\n");
                System.out.println("Bienvenido/a " + user.nombres + ". ¿Qué deseas hacer el día de hoy?\n");
                System.out.println("1. Reservar");
                System.out.println("2. Consultar reserva");
                System.out.println("3. Cerrar sesión");
                System.out.print("\nIngresa el numero de la opcion a la que deseas ingresar: ");
                int opc = sc.nextInt();
                sc.nextLine();

                while (opc < 1 || opc > 3) {
                    System.out.print("Opción invalida. Ingresa una opción válida: ");
                    opc = sc.nextInt();
                    sc.nextLine();
                }

                switch (opc) {
                    case 1:
                        user.gestionarReserva(espacios, reservas);
                        break;

                    case 2:
                        // user.consultarReserva();
                        break;

                    case 3:
                        System.out.println("\nSesión correctamente cerrada.");
                        System.out.println("¡Gracias por usar reservepol! Ten un buen dia :)");
                        booly = false;
                        break;
                }
               
            }else if (user instanceof Administrador){
                System.out.println("\n\n");
                System.out.println("Bienvenido/a " + user.nombres + ". ¿Qué deseas hacer el día de hoy?\n");
                System.out.println("1. Gestionar Reserva");
                System.out.println("2. Consultar reserva");
                System.out.println("3. Cerrar sesión");
                System.out.print("\nIngresa el numero de la opcion a la que deseas ingresar: ");
                int opc = sc.nextInt();
                sc.nextLine();

                while (opc < 1 || opc > 3) {
                    System.out.println("Opción invalida. Ingresa una opción válida: ");
                    opc = sc.nextInt();
                    sc.nextLine();
                }

                switch (opc) {
                    case 1:
                        user.gestionarReserva(espacios, reservas);
                        break;

                    case 2:
                        // user.consultarReserva();
                        break;

                    case 3:
                        System.out.println("\nSesión correctamente cerrada.");
                        System.out.println("¡Gracias por usar reservepol! Ten un buen dia :)");
                        booly = false;
                        break;
                }
            }
    
        } while(booly == true);
        sc.close();

    }

        
      

    
 
      
    
 
       

           

       
    
    }



