package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.proyectopoo1.enums.EstadoReserva;

import io.github.cdimascio.dotenv.Dotenv;


public abstract class Usuario {
    // Variables de instancia protected porque serán heredados a los distintos tipos de usuarios
    protected String codUnico;
    protected String numCedula;
    protected String nombres;
    protected String apellidos;
    protected String user;
    protected String password;
    protected String correo;

    //constructor de la clase
    public Usuario(String codUnico,String numCedula,String nombres,String apellidos,String user,String password,String correo){
        this.codUnico = codUnico;
        this.numCedula = numCedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.user = user;
        this.password = password;
        this.correo = correo;
    }

    //metodos adicionales
    public abstract void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas);

    public abstract void consultarReserva(ArrayList<Reserva> reservas);

    public void enviarCorreo(){};

    public void enviarCorreo(String materia){}

    public Boolean verificarFecha(ArrayList<Reserva> r, LocalDate fecha){
        for(Reserva reserva: r){
            if(reserva.getUser()==this && reserva.getFechaReserva().equals(fecha)){
                return false;
            }
        }
        return true;

    }

    public void consultarReserva(LocalDate fecha){};

    //enviar correo para estudiante porque solo recibe la reserva que se generó
    public void enviarCorreo(Reserva reserva){

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
            message.setText("El estudiante " + this.nombres + " y " + this.apellidos + "ha realizado una reserva con código " + reserva.getCodUnico() + "para la fecha " + reserva.getFechaReserva() + " en " + reserva.getEspacio().getTipoEspacio());
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //enviar correo sobrecargado para profesor porque me recibe la reserva que se generó y la materia por la cual se reservó el lugar
    public void enviarCorreo(Reserva reserva, String materiaPorLaQueSeReserva){

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
            message.setText("Se le notifica que el profesor " + this.nombres + " " + this.apellidos + "ha realizado una reserva con código " + reserva.getCodUnico() + "para la fecha " + reserva.getFechaReserva() + " en " + reserva.getEspacio().getTipoEspacio() + " para la materia " + materiaPorLaQueSeReserva);
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    

    public void enviarCorreo(String correoPE, String codReserva, String motivo, EstadoReserva decision){}

    //getters
    public String getCodUnico(){
        return codUnico;
    }
    public String getNumCedula(){
        return numCedula;
    }
    public String getNombres(){
        return nombres;
    }
    public String getApellidos(){
        return apellidos;
    }
    public String getUser(){
        return user;
    }
    public String getPassword(){
        return password;
    }
    public String getCorreo(){
        return correo;
    }

    //setters
    public void setCodUnico(String codUnico){
        this.codUnico = codUnico;
    }
    public void setNumCedula(String numCedula){
        this.numCedula = numCedula;
    }
    public void setNombres(String nombres){
        this.nombres = nombres;
    }
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setCorreo(String correo){
        this.correo = correo;
    }
    

    
    public String toString(){return codUnico + " | " + 
                            numCedula + " | " + nombres + " | " + 
                            apellidos + " | " + user + " | " + 
                            password + " | " + correo + " | " ;}

}
