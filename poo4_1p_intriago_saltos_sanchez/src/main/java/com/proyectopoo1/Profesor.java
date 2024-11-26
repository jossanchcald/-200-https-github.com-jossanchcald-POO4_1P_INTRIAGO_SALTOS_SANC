package com.proyectopoo1;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import io.github.cdimascio.dotenv.Dotenv;

public class Profesor extends Usuario{

    // Variables de instancia
    private String facultad;
    private ArrayList<String> materiasDict;


    // Constructores
    public Profesor(String facultad, ArrayList<String> materiasDict, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.facultad = facultad;
        this.materiasDict = materiasDict;
    }


    // Getters
    public String getFacultad(){
        return facultad;
    }

    public ArrayList<String> getMateriasDict() {
        return materiasDict;
    }
    
    
    // Setters
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public void setMateriasDict(ArrayList<String> materiasDict) {
        this.materiasDict = materiasDict;
    }

    //enviar correo sobrescrito para profesor
    //falta configurar el mensaje
    @Override
    public void enviarCorreo(String materia){

        //obtener el administrador al que solicitamos la reserva

        Dotenv dot = Dotenv.load();

        String host = dot.get("MAIL_HOST");
        String port = dot.get("MAIL_PORT");
        String user = dot.get("MAIL_USER");
        String pass = dot.get("MAIL_PASS");

        Properties prop = new Properties();
        prop.put("mail.smtp.host",host);
        prop.put("mail.smtp.port",port);
        prop.put("mail.smtp.auth",true);
        prop.put("mail.smtp.starttls.enable",true);

        Session sesion = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(user,"APP DE RESERVAS"));
            //el atributo de la clase que va a enviar el correo debe recibir correo
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("kelvintr@espol.edu.ec"));
            message.setSubject("Reserva realizada");
            message.setText("El estudiante " + this.nombres + " " + this.apellidos + "");
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas) {
        Scanner scanner = new Scanner(System.in);
        LocalDate fecha = null;
        boolean fechaValida = false;
        String entrada;

        //valida si la fecha ingresada es valida en el formato yyyy-MM-dd
        while (!fechaValida) {
            System.out.print("Ingresa una fecha (yyyy-MM-dd): ");
            entrada = scanner.nextLine();

            try {
                fecha = LocalDate.parse(entrada);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Por favor, intenta de nuevo.");
            }
        }
        
        //mostrar las canchas o aulas disponibles
        System.out.println("1. AULA");
        System.out.println("2. CANCHA");
        System.out.println("3. AUDITORIO");
        System.out.print("Ingrese el numero de la opcion del tipo de espacio que desea reservar: ");
        int opcionTipoElegido = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Espacio> espaciosDisponibles = new ArrayList<Espacio>(); 
        if(opcionTipoElegido == 1){
            for(Espacio espacio : espacios){
                if(espacio.getTipoEspacio() == TipoEspacio.AULA && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                    espaciosDisponibles.add(espacio);
                }
            }
        }

        else if(opcionTipoElegido == 2){
            for(Espacio espacio : espacios){
                if(espacio.getTipoEspacio() == TipoEspacio.CANCHA && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                    espaciosDisponibles.add(espacio);
                }
            }
        }

        else if(opcionTipoElegido == 3){
            for(Espacio espacio : espacios){
                if(espacio.getTipoEspacio() == TipoEspacio.AUDITORIO && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                    espaciosDisponibles.add(espacio);
                }
            }
        }


        for(int i=0; i<espaciosDisponibles.size();i++){
            System.out.println((i+1)+". " + espaciosDisponibles.get(i).toString());
        }

        System.out.println("Ingrese la opcion del espacio que desea reservar: ");
        int ops = scanner.nextInt();
        scanner.nextLine();
        while(ops < 1 || ops > espaciosDisponibles.size() ){
            System.out.println("opcion invalida. Ingresa una opcion valida: ");
            ops = scanner.nextInt();
            scanner.nextLine();
        }
        Espacio esp = espaciosDisponibles.get(ops-1);

        System.out.println("Elija la materia para la cual es la reserva");
        for(int i=0;i<materiasDict.size();i++){
            System.out.println((i+1) + ".- " + materiasDict.get(i));
        }
        System.out.println("Elija la materia para la cual es la reserva");
        int opcionMateriaDictada = scanner.nextInt();
        scanner.nextLine();
        String motivoDeReserva = materiasDict.get(opcionMateriaDictada - 1);
        
        System.out.println("¿Desea crear la reserva: ");
        System.out.println("1. SI");
        System.out.println("2. NO");
        System.out.print("Ingrese la opción: ");
        int opcionCrearReserva = scanner.nextInt();
        scanner.nextLine();
        while(opcionCrearReserva < 1 || opcionCrearReserva > 2 ){
            System.out.println("opcion invalida. Ingresa una opcion valida: ");
            opcionCrearReserva = scanner.nextInt();
            scanner.nextLine();
        }

        if(opcionCrearReserva == 1){
            Reserva reserva = new Reserva(this,fecha,esp,EstadoReserva.APROBADO,motivoDeReserva);
            reserva.cargarReserva();
        }
        else{
            Sistema.main(null);
        }
    }
}
