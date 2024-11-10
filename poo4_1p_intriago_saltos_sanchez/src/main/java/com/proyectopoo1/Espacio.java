package com.proyectopoo1;

public class Espacio {

    private String codUnico;
    private TipoEspacio tipoEspacio;
    private String nombreEsp;
    private int capacidadEsp;
    private DisponibilidadEsp estadoEsp;
    private UsuarioPermitido permiso;

    // constructor
    public Espacio(String codUnico, TipoEspacio tipoEspacio,String nombreEsp ,int capacidadEsp ,DisponibilidadEsp estadoEsp ,UsuarioPermitido permiso){
        this.codUnico = codUnico;
        this.tipoEspacio = tipoEspacio;
        this.nombreEsp = nombreEsp;
        this.capacidadEsp = capacidadEsp;
        this.estadoEsp = estadoEsp;
        this.permiso = permiso;

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
