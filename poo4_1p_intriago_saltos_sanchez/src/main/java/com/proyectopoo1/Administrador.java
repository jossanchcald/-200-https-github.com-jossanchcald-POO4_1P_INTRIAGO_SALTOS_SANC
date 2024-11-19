package com.proyectopoo1;

public class Administrador extends Usuario{

    // Variables de instancia
    private String cargo;

    // Constructores
    public Administrador(String cargo, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.cargo = cargo;
    }

    // Getters
    public String getCargo(){
        return cargo;
    }

    // Setters
    public void serCargo(String cargo){
        this.cargo = cargo;
    }

  
}
