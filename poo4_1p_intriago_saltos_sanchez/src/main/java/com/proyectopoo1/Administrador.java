package com.proyectopoo1;

public class Administrador extends Usuario{

    // Variables de instancia
    private Cargo cargo;

    // Constructores
    public Administrador(Cargo cargo, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.cargo = cargo;
    }

    // Getters
    public Cargo getCargo(){
        return cargo;
    }

    // Setters
    public void serCargo(Cargo cargo){
        this.cargo = cargo;
    }
}
