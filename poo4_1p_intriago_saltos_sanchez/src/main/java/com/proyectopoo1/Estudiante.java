package com.proyectopoo1;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import io.github.cdimascio.dotenv.*;
import java.util.Properties;


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

}
