package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

import com.proyectopoo1.enums.EstadoReserva;
import com.proyectopoo1.utilidades.ManejoArchivo;

public class Reserva {

    // Variables de instancia
    private String codUnico;
    private Usuario user; 
    private LocalDate fechaReserva;
    private Espacio espacio; 
    private EstadoReserva estadoReserva;
    private String motivoReserva;
    public static int contador;


    // Constructores
    public Reserva(Usuario user, LocalDate fechaReserva, Espacio espacio, EstadoReserva estadoReserva, String motivoReserva){
        this.codUnico = generarCodUnico();
        this.user = user;
        this.fechaReserva = fechaReserva;
        this.espacio = espacio;
        this.estadoReserva = estadoReserva;
        this.motivoReserva = motivoReserva;
        contador++;

    } 
    public Reserva(String codUnico, Usuario user, LocalDate fechaReserva, Espacio espacio, EstadoReserva estadoReserva, String motivoReserva){
        this.codUnico = codUnico;
        this.user = user;
        this.fechaReserva = fechaReserva;
        this.espacio = espacio;
        this.estadoReserva = estadoReserva;
        this.motivoReserva = motivoReserva;
        contador++;

    }

    // Métodos adicionales
    public void cargarReserva(){
        
        ArrayList<String> datosAEscr = ManejoArchivo.leerArchivo("reservas.txt");
        datosAEscr.add(this.toString());
        ManejoArchivo.escribirArchivo("reservas.txt", datosAEscr);
    }

    private String generarCodUnico(){
        ArrayList<String> lista = ManejoArchivo.leerArchivo("reservas.txt");
        String[] datos = lista.get(lista.size()-1).split(" \\| ");

        int ultimoCodUnico = Integer.parseInt(datos[0]);
        return "" + (ultimoCodUnico+1);
    }

    @Override
    public String toString(){
        return codUnico + " | " + user.codUnico + " | " + user.getNumCedula() + " | " + fechaReserva + " | " + espacio.getCodUnico() + " | " + espacio.getTipoEspacio() + " | " + estadoReserva + " | " + motivoReserva;
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

    public LocalDate getFechaReserva(){
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
    public void setCodUnico(String codUnico){
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
