package com.proyectopoo1;

import java.util.ArrayList;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import io.github.cdimascio.dotenv.*;
import java.util.Properties;

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
    public void gestionarReserva(ArrayList<Espacio> espacios) {
        // TODO Auto-generated method stub
        
    }





}
