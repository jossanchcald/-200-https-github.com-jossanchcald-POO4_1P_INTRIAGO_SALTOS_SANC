package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

import com.proyectopoo1.enums.EstadoReserva;
import com.proyectopoo1.utilidades.ManejoArchivo;

/**
 * Representa una reserva realizada en el sistema. Contiene información como el usuario que realiza la reserva, 
 * la fecha de la misma, el espacio reservado, el estado de la reserva y el motivo asociado. 
 * También incluye funcionalidades para la persistencia de datos en archivos y la generación de códigos únicos 
 * para identificar cada reserva.
 * 
 */
public class Reserva {

    // Variables de instancia
    private String codUnico;
    private Usuario user;
    private LocalDate fechaReserva;
    private Espacio espacio;
    private EstadoReserva estadoReserva;
    private String motivoReserva;
    public static int contador;

    /**
     * Constructor que inicializa a reserva con un Cod. Unico generado
     * Utilizado para cargar las nuevas reservas que se crean
     * @param user
     * @param fechaReserva
     * @param espacio
     * @param estadoReserva
     * @param motivoReserva
     */
    public Reserva(Usuario user, LocalDate fechaReserva, Espacio espacio, EstadoReserva estadoReserva,
            String motivoReserva) {
        this.codUnico = generarCodUnico();
        this.user = user;
        this.fechaReserva = fechaReserva;
        this.espacio = espacio;
        this.estadoReserva = estadoReserva;
        this.motivoReserva = motivoReserva;
        contador++;

    }

    /**
     * Constructor que inicializa a reserva con todos sus atributos especificados
     * Utilizado para cargar las reservas del txt que ya tiene un codUnico definido
     * @param codUnico
     * @param user
     * @param fechaReserva
     * @param espacio
     * @param estadoReserva
     * @param motivoReserva
     */
    public Reserva(String codUnico, Usuario user, LocalDate fechaReserva, Espacio espacio, EstadoReserva estadoReserva,
            String motivoReserva) {
        this.codUnico = codUnico;
        this.user = user;
        this.fechaReserva = fechaReserva;
        this.espacio = espacio;
        this.estadoReserva = estadoReserva;
        this.motivoReserva = motivoReserva;
    }

    /**
     * Metodo que añade un objeto reserva a reservas.txt
     */
    public void cargarReserva() {
        ArrayList<String> datosAEscr = ManejoArchivo.leerArchivo("reservas.txt");
        // El objeto que llama al metodo es escrito en el txt de reserva
        datosAEscr.add(this.toString());
        ManejoArchivo.escribirArchivo("reservas.txt", datosAEscr);
    }

    /**
     * Metodo que reescribe reservas.txt con las reservas cambiadas
     * @param reservas Lista de reservas necesaria para sobreescribir/reescribir reservas.txt
     */
    public static void cargarReservas(ArrayList<Reserva> reservas) {
        // Recibe el arraylist con las reservas ya cambiadas de estado RECHAZADO O APROBADO
        // y lo convierte a un arraylist de String para reescribir el txt en orden
        ArrayList<String> nuevas = new ArrayList<>();
        for (Reserva r : reservas) {
            nuevas.add(r.toString());
        }
        ManejoArchivo.escribirArchivo("reservas.txt", nuevas);

    }

    /**
     * Metodo que genera un Cod. Unico para la reserva de manera creciente tomando como base el
     * ultimo Cod. Unico
     * @return String Devuelve el numero del codigo creado
     */
    private String generarCodUnico() {
        ArrayList<String> lista = ManejoArchivo.leerArchivo("reservas.txt");
        String[] datos = lista.get(lista.size() - 1).split(" \\| ");

        int ultimoCodUnico = Integer.parseInt(datos[0]);
        return "" + (ultimoCodUnico + 1);
    }

    @Override
    public String toString() {
        return codUnico + " | " + user.codUnico + " | " + user.getNumCedula() + " | " + fechaReserva + " | "
                + espacio.getCodUnico() + " | " + espacio.getTipoEspacio() + " | " + estadoReserva + " | "
                + motivoReserva;
    }

    // Getters
    public String getCodUnico() {
        return codUnico;
    }

    public Usuario getUser() {
        return user;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public String getMotivoReserva() {
        return motivoReserva;
    }

    public static int getContador() {
        return contador;
    }

    // Setters
    public void setCodUnico(String codUnico) {
        this.codUnico = codUnico;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public void setMotivoReserva(String motivoReserva) {
        this.motivoReserva = motivoReserva;
    }

    public static void setContador(int contador) {
        Reserva.contador = contador;
    }

}
