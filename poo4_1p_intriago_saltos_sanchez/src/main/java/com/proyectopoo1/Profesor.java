package com.proyectopoo1;

import java.util.ArrayList;

public class Profesor extends Usuario{

    // Variables de instancia
    private String facultad;
    private ArrayList<String> materiasDict;


    // Constructores
    public Profesor(String facultad, ArrayList<String> materiasDict, Cargo cargo, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.facultad = facultad;
        this.materiasDict = materiasDict;
    }


    // Getters
    public String getFacultad(){
        return facultad;
    }

    public ArrayList<String> getMateriasDict() {
        return materiasDict;
    }
    
    
    // Setters
    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public void setMateriasDict(ArrayList<String> materiasDict) {
        this.materiasDict = materiasDict;
    }








}
