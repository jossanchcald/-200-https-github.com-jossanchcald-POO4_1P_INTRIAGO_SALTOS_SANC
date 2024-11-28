package com.proyectopoo1.entidades;

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
import com.proyectopoo1.enums.EstadoReserva;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Representa a los usuarios del sistema de reservas, proporcionando funcionalidades 
 * para gestionar sus reservas, verificar fechas, y enviar correos electrónicos de 
 * notificación. Sirve como clase base para las subclases Estudiante y Profesor con 
 * comportamientos específicos.
 * 
 */
public abstract class Usuario {
    // Variables de instancia protected porque serán heredados a los distintos tipos
    // de usuarios
    protected String codUnico;
    protected String numCedula;
    protected String nombres;
    protected String apellidos;
    protected String user;
    protected String password;
    protected String correo;

    /**
     * Constructor para inicializacion común a las subclases
     * @param codUnico código único que identifica al usuario
     * @param numCedula
     * @param nombres
     * @param apellidos
     * @param user user de la cuenta asociada al usuario
     * @param password contrasenia de la cuenta asociada al usuario
     * @param correo
     */
    public Usuario(String codUnico, String numCedula, String nombres, String apellidos, String user, String password,
            String correo) {
        this.codUnico = codUnico;
        this.numCedula = numCedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.user = user;
        this.password = password;
        this.correo = correo;
    }

    /**
     * Metodo que en caso de Profesor o Estudiante y gestiona reservas pendientes en
     * caso de Administrador
     * @param espacios Lista de espacios necesaria para mostrar cuales son los que estan disponibles
     * @param reservas Lista de reservas necesaria para adjuntar la nueva reserva a esta lista
     * @param sc Scanner para ingresar datos e informacion via teclado
     */
    public abstract void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas, Scanner sc);

    
    /**
     * Metodo que consulta una reserva en caso de Profesor o Estudiante y consulta todas las
     * reservas en caso de Administrador
     * @param reservas Lista de reservas necesaria para poder recorrer y encontrar la deseada en caso de Profeso o Estudiante
     * o poder imprimir todas en caso de Administrador
     * @param sc Scanner necesario para ingresar datos e informacion via teclado
     */
    public abstract void consultarReserva(ArrayList<Reserva> reservas, Scanner sc);

    
    /**
     * Metodo que verifica que la fecha ingresada corresponda a una reserva hecha por el
     * usuario
     * @param r Lista de reservas necesaria para verificar la existencia de una reserva con esa fecha y si el usuario es el que la creó
     * @param fecha Fecha necesaria para verificar si hay alguna reserva creada en esa fecha
     * @return boolean Devuelve el valor booleano de la verificacion
     */
    public Boolean verificarFecha(ArrayList<Reserva> r, LocalDate fecha) {
        for (Reserva reserva : r) {
            if (reserva.getUser() == this && reserva.getFechaReserva().equals(fecha)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo que pide una fecha y verifica que cumpla con el formato establecido
     * @param sc Scanner necesario para ingresar datos e informacion via teclado
     * @return LocalDate Devuelve la fecha que cumpla con el formato
     */
    public static LocalDate validarFecha(Scanner sc) {
        LocalDate fecha = null;
        boolean fechaValida = false;
        String entrada;

        // valida si la fecha ingresada es valida en el formato yyyy-MM-dd
        while (!fechaValida) {
            System.out.print("\nIngresa la fecha (yyyy-MM-dd): ");
            entrada = sc.nextLine();

            try {
                fecha = LocalDate.parse(entrada);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("\nFormato de fecha inválido. Por favor, intenta de nuevo.");
            }
        }
        return fecha;
    }

    /**
     * Metodo que pide ingresar una opcion entre los dos valores y valida si es un dato válido
     * @param num1 Numero necesario para indicar desde donde inician las opciones
     * @param num2 Numero necesario para indicar donde terminan las opciones
     * @param sc Scanner para ingresar datos e informacion via teclado
     * @return int Devuelve la opcion elegida
     */
    public static int elegirOpcion(int num1, int num2, Scanner sc) {
        System.out.print("Ingrese el numero de la opcion que desea escoger: ");
        int op = sc.nextInt();
        sc.nextLine();

        while (op < num1 || op > num2) {
            System.out.print("Opcion invalida. Ingresa una opcion valida: ");
            op = sc.nextInt();
            sc.nextLine();
        }
        return op;
    }

    /**
     * Metodo para enviar correo como Estudiante
     * @param reserva Reserva necesaria para obtener sus datos
     */
    public void enviarCorreo(Reserva reserva) {

        // obtener el administrador al que solicitamos la reserva

        Dotenv dot = Dotenv.load();

        String host = dot.get("MAIL_HOST");
        String port = dot.get("MAIL_PORT");
        String user = dot.get("MAIL_USER");
        String pass = dot.get("MAIL_PASS");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);

        Session sesion = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(user, "APP DE RESERVAS"));
            // el atributo de la clase que va a enviar el correo debe recibir correo
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kelvintr@espol.edu.ec"));
            message.setSubject("Reserva realizada");
            message.setText("El estudiante " + this.nombres + " y " + this.apellidos
                    + "ha realizado una reserva con código " + reserva.getCodUnico() + "para la fecha "
                    + reserva.getFechaReserva() + " en " + reserva.getEspacio().getTipoEspacio());
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo para enviar correo como Profesor
     * @param reserva Reserva necesaria para obtener sus datos
     * @param materiaPorLaQueSeReserva Materia necesaria que cuenta como motivo para la creacion de la reserva
     */
    public void enviarCorreo(Reserva reserva, String materiaPorLaQueSeReserva) {

        // obtener el administrador al que solicitamos la reserva

        Dotenv dot = Dotenv.load();

        String host = dot.get("MAIL_HOST");
        String port = dot.get("MAIL_PORT");
        String user = dot.get("MAIL_USER");
        String pass = dot.get("MAIL_PASS");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);

        Session sesion = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(user, "APP DE RESERVAS"));
            // el atributo de la clase que va a enviar el correo debe recibir correo
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kelvintr@espol.edu.ec"));
            message.setSubject("Reserva realizada");
            message.setText("Se le notifica que el profesor " + this.nombres + " " + this.apellidos
                    + "ha realizado una reserva con código " + reserva.getCodUnico() + "para la fecha "
                    + reserva.getFechaReserva() + " en " + reserva.getEspacio().getTipoEspacio() + " para la materia "
                    + materiaPorLaQueSeReserva);
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que envia correo a estudiantes rechazando o aceptando su solicitud de reserva
     * @param reserva Reserva necesaria para obtener sus datos
     * @param motivo Motivo necesario para añadir si la reserva fue rechazada
     * @param decision Enum EstadoReserva necesario para definir si se aprobó o rechazó la reserva
     */
    public void enviarCorreo(Reserva reserva, String motivo, EstadoReserva decision) {

        Dotenv dot = Dotenv.load();

        String host = dot.get("MAIL_HOST");
        String port = dot.get("MAIL_PORT");
        String user = dot.get("MAIL_USER");
        String pass = dot.get("MAIL_PASS");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);

        Session sesion = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(user, "APP DE RESERVAS"));
            // el atributo de la clase que va a enviar el correo debe recibir correo
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kelvintr@espol.edu.ec"));
            if (decision == EstadoReserva.APROBADO) {
                message.setSubject("Reserva aprobada");
                message.setText("Se ha aprobado su reserva con código " + reserva.getCodUnico() + "."
                        + "\n\nAtentamente,"
                        + "\nDepartamento Administrativo");
                Transport.send(message);
            } else if (decision == EstadoReserva.RECHAZADO) {
                message.setSubject("Reserva rechazada");
                message.setText(
                        "Se ha rechazado su reserva con código " + reserva.getCodUnico() + " por el siguiente motivo: " + motivo
                                + "\n\nAtentamente,"
                                + "\nDepartamento Administrativo");
                Transport.send(message);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // getters
    public String getCodUnico() {
        return codUnico;
    }

    public String getNumCedula() {
        return numCedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getCorreo() {
        return correo;
    }

    // setters
    public void setCodUnico(String codUnico) {
        this.codUnico = codUnico;
    }

    public void setNumCedula(String numCedula) {
        this.numCedula = numCedula;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
