package com.proyectopoo1.entidades;

import com.proyectopoo1.enums.DisponibilidadEsp;
import com.proyectopoo1.enums.TipoEspacio;
import com.proyectopoo1.enums.UsuarioPermitido;

/**
 * Esta clase representa los espacios disponibles para ser reservados en un sistema. 
 * Contiene atributos como el tipo de espacio, la capacidad máxima, el estado de disponibilidad 
 * y los permisos asociados para diferentes tipos de usuarios.
 * 
 */
public class Espacio {

    // Variables de instancia
    private String codUnico; 
    private TipoEspacio tipoEspacio;
    private String nombreEsp;
    private int capacidadEsp;
    private DisponibilidadEsp estadoEsp;
    private UsuarioPermitido permiso;

    /**
     * Constructor que inicializa a reserva con todos sus atributos especificados
     * @param codUnico código único que identifica al espacio
     * @param tipoEspacio
     * @param nombreEsp
     * @param capacidadEsp capacidad maxima de personas de cada espacio
     * @param estadoEsp estado actual del espacio, puede estar disponible o reservado
     * @param permiso usuarios permitidos a reservar el espacio
     */
    public Espacio(String codUnico, TipoEspacio tipoEspacio,String nombreEsp ,int capacidadEsp ,DisponibilidadEsp estadoEsp ,UsuarioPermitido permiso){
        this.codUnico = codUnico;
        this.tipoEspacio = tipoEspacio;
        this.nombreEsp = nombreEsp;
        this.capacidadEsp = capacidadEsp;
        this.estadoEsp = estadoEsp;
        this.permiso = permiso;

    }

    @Override
    public String toString(){
        return codUnico + " | " + tipoEspacio + " | " + nombreEsp + " | " + capacidadEsp + " | " + estadoEsp + " | " + permiso;
    }

    // getters 
    public String getCodUnico(){
        return codUnico;
    }

    public TipoEspacio getTipoEspacio(){
        return tipoEspacio;
    }

    public String getNombreEsp() {
        return nombreEsp;
    }
    
    public int getCapacidadEsp() {
        return capacidadEsp;
    }
    
    public DisponibilidadEsp getEstadoEsp() {
        return estadoEsp;
    }
    
    public UsuarioPermitido getPermiso() {
        return permiso;
    }

    // setters
    public void setCodUnico(String codUnico) {
        this.codUnico = codUnico;
    }
    
    public void setTipoEspacio(TipoEspacio tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }
    
    public void setNombreEsp(String nombreEsp) {
        this.nombreEsp = nombreEsp;
    }
    
    public void setCapacidadEsp(int capacidadEsp) {
        this.capacidadEsp = capacidadEsp;
    }
    
    public void setEstadoEsp(DisponibilidadEsp estadoEsp) {
        this.estadoEsp = estadoEsp;
    }
    
    public void setPermiso(UsuarioPermitido permiso) {
        this.permiso = permiso;
    }


}
