package com.proyectopoo1;

//paquete para manejar ArrayList
import java.util.ArrayList;


//paquetes para enviar correo
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import io.github.cdimascio.dotenv.*;
import java.util.Properties;

//paquetes para trabajar con fechas
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Estudiante extends Usuario{

    // Variables de instancia
    private String numMatricula;
    private String carrera;


    // Constructores
    public Estudiante(String numMatricula, String carrera, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.numMatricula = numMatricula;
        this.carrera = carrera;
    }


    // Getters
    public String getNumMatricula() {
        return numMatricula;
    }

    public String getCarrera() {
        return carrera;
    }


    // Setters
    public void setNumMatricula(String numMatricula) {
        this.numMatricula = numMatricula;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    //enviar correo sobrescrito para estudiante
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

    //gestionarReserva() para estudiante
    public void gestionarReserva(ArrayList<Espacio> espacios){
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
        scanner.close();
        //mostrar las canchas o aulas disponibles

        for(Espacio espacio : espacios){
            if((espacio.getTipoEspacio() == (TipoEspacio.CANCHA) || espacio.getTipoEspacio() == (TipoEspacio.AULA)) && espacio.getEstadoEsp() == (DisponibilidadEsp.DISPONIBLE)){
                System.out.println(espacio.toString());
            }
        }
    }

}
