package com.proyectopoo1;

import java.util.Date;

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
    public void gestionarReserva(){}

    public void consultarReserva(Date fecha){}

    public void enviarCorreo(){}

    public void enviarCorreo(String materia){}

    @Override
    public String toString(){return codUnico + " | " + 
                            numCedula + " | " + nombres + " | " + 
                            apellidos + " | " + user + " | " + 
                            password + " | " + correo + " | " ;}


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

}
