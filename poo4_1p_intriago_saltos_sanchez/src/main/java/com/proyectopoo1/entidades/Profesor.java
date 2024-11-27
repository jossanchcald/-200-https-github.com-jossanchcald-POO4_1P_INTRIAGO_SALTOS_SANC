package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import com.proyectopoo1.Sistema;
import com.proyectopoo1.enums.DisponibilidadEsp;
import com.proyectopoo1.enums.EstadoReserva;
import com.proyectopoo1.enums.TipoEspacio;


public class Profesor extends Usuario{

    // Variables de instancia
    private String facultad;
    private ArrayList<String> materiasDict;


    // Constructores
    public Profesor(String facultad, ArrayList<String> materiasDict, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
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
    



    //gestionarReserva() para profesor
    @Override
    public void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas) {
        Scanner scanner = new Scanner(System.in);
        LocalDate fecha = validarFecha();
        
        boolean valida = verificarFecha(reservas, fecha);
        if(valida==false){
            System.out.println("Ya tiene una reserva para la fecha" + fecha);
        }else{ 
        //mostrar las canchas o aulas disponibles
            System.out.println("1. AULA");
            System.out.println("2. LABORATORIO");
            System.out.println("3. AUDITORIO");
            int opcionTipoElegido = elegirOpcion(1, 3);
           
            ArrayList<Espacio> espaciosDisponibles = new ArrayList<>(); 
            
            switch (opcionTipoElegido) {
                case 1:
                    for(Espacio espacio : espacios){
                        if(espacio.getTipoEspacio() == TipoEspacio.AULA && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                            espaciosDisponibles.add(espacio);
                        }
                    }   break;
                case 2:
                    for(Espacio espacio : espacios){
                        if(espacio.getTipoEspacio() == TipoEspacio.LABORATORIO && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                            espaciosDisponibles.add(espacio);
                        }
                    }   break;
                case 3:
                    for(Espacio espacio : espacios){
                        if(espacio.getTipoEspacio() == TipoEspacio.AUDITORIO && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                            espaciosDisponibles.add(espacio);
                        }
                    }   break;
                default:
                    break;
            }

            ArrayList<Espacio> espaciosParaEliminar = new ArrayList<>();
            for (Espacio espacio : espaciosDisponibles) {
                for (Reserva r : reservas) {
                    if (r.getFechaReserva().equals(fecha) && r.getEspacio() == espacio) {
                        espaciosParaEliminar.add(espacio);
                        break; 
                    }
                }
            }
            
            espaciosDisponibles.removeAll(espaciosParaEliminar);

           if(espaciosDisponibles.isEmpty()){
            System.out.println("No hay espacios para esa fecha :(");
           }
           else{
                for(int i=0; i<espaciosDisponibles.size();i++){
                    System.out.println((i+1)+". " + espaciosDisponibles.get(i).toString());
                }
                int ops = elegirOpcion(1, espaciosDisponibles.size());
                Espacio esp = espaciosDisponibles.get(ops-1);

                System.out.println("Elija la materia para la cual es la reserva");
                for(int i=0;i<materiasDict.size();i++){
                    System.out.println((i+1) + ".- " + materiasDict.get(i));
                }
                int opcionMateriaDictada = elegirOpcion(1, materiasDict.size());
                String motivoDeReserva = materiasDict.get(opcionMateriaDictada - 1);
                
                System.out.println("¿Desea crear la reserva: ");
                System.out.println("1. SI");
                System.out.println("2. NO");
                System.out.print("Ingrese la opción: ");
                int opcionCrearReserva = elegirOpcion(1, 2);


                if(opcionCrearReserva == 1){
                    Reserva reserva = new Reserva(this,fecha,esp,EstadoReserva.APROBADO,motivoDeReserva);
                    reserva.cargarReserva();
                    //despues de generar la reserva se le debe notificar al administrador
                    enviarCorreo(reserva,motivoDeReserva);
                }
                else{
                    //para regresar el programa al menu principal
                    Sistema.main(null);
                    }  
            }
       }
    }





    @Override
    public void consultarReserva(ArrayList<Reserva> reservas) {
        Scanner sc = new Scanner(System.in);
        LocalDate fecha = validarFecha();

        for (Reserva reserv : reservas) {
            if (reserv.getFechaReserva().equals(fecha) && reserv.getUser() == this) {
                System.out.println("\nDatos reserva: \n");
                System.out.println(reserv.getCodUnico() + " - " + fecha + " - " + reserv.getEspacio().getTipoEspacio() + " - " + reserv.getEspacio().getNombreEsp() + " - " + 
                reserv.getEspacio().getCapacidadEsp() + " - " + reserv.getUser().getNombres() + " " + reserv.getUser().getApellidos() + " - " + reserv.getEstadoReserva());
            }
        }
    }
}
