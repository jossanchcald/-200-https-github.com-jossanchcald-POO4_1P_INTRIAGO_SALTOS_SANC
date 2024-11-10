package com.proyectopoo1;

//import java.time.LocalDate;
import java.util.Date;

public class Reserva {

    // Variables de instancia
    String codUnico;
    // private Usuario user; // Falta clase Usuario
    private Date fechaReserva; // Se recomienda el uso de LocalDate de java.time, ya que Date es obsoleto
    // private Espacio espacio; // Falta clase Espacio
    private EstadoReserva estadoReserva;
    private String motivoReserva;
    public static int contador;


    // Constructores
    public Reserva(String codUnico, String codUnicoUser, String numCedulaUser, Date fechaReserva, String codUnicoEspacio, String tipoEspacioReservado, EstadoReserva estadoReserva, String motivoReserva){
        this.codUnico = codUnico;
        this.fechaReserva = fechaReserva;
        this.estadoReserva = estadoReserva;
        this.motivoReserva = motivoReserva;
        
        // this.user.codUnico = codUnicoUser; // Falta clase Usuario
        // this.espacio.codUnico = codUnicoEspacio; // Falta clase Espacio
        // this.espacio.tipoEspacio = TipoEspacio.valueOf(tipoEspacioReservado.toUpperCase()); // Falta clase Espacio
    }


    // Métodos adicionales



    // Getters
    public String getCodUnico() {
        return codUnico;
    }

    // public Usuario getUser() {
    //     return user;
    // }

    // Faltan clases Usuario y Espacio

    // public Espacio getEspacio() {
    //     return espacio;
    // }

    public Date getFechaReserva(){
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

    // public void setUser(Usuario user) {
    //     this.user = user;
    // }

    // Faltan clases Usuario y Espacio

    // public void setEspacio(Espacio espacio) {
    //     this.espacio = espacio;
    // }

    public void setFechaReserva(Date fechaReserva) {
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
