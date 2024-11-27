package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.proyectopoo1.Sistema;
import com.proyectopoo1.enums.DisponibilidadEsp;
import com.proyectopoo1.enums.EstadoReserva;
import com.proyectopoo1.enums.TipoEspacio;


public class Estudiante extends Usuario{

    // Variables de instancia
    private String numMatricula;
    private String carrera;


    // Constructores
    public Estudiante(String numMatricula, String carrera, String codUnico, String numCedula, String nombres, String apellidos, String user, String password, String correo){
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.numMatricula = numMatricula;
        this.carrera = carrera;
    }


    // Getters
    public String getNumMatricula() {
        return numMatricula;
    }

    public String getCarrera() {
        return carrera;
    }


    // Setters
    public void setNumMatricula(String numMatricula) {
        this.numMatricula = numMatricula;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    //gestionarReserva() para estudiante
    public void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas){

        Scanner scanner = new Scanner(System.in);
        LocalDate fecha = validarFecha();

        boolean valida = verificarFecha(reservas, fecha);
        if(valida==false){
            System.out.println("Ya tiene una reserva para la fecha" + fecha);

        }else{
            
            //mostrar las canchas o aulas disponibles
            System.out.println("1. AULA");
            System.out.println("2. CANCHA");
            int op = elegirOpcion(1, 2);

            ArrayList<Espacio> espaciosDis = new ArrayList<>();
            if(op==1){
                for(Espacio espacio : espacios){
                    if(espacio.getTipoEspacio() == TipoEspacio.AULA && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                        espaciosDis.add(espacio);
                    }
                }

            } else if(op==2){
                for(Espacio espacio : espacios){
                    if(espacio.getTipoEspacio() == TipoEspacio.CANCHA && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE){
                        espaciosDis.add(espacio);
                    }
                }
            }

            ArrayList<Espacio> espaciosParaEliminar = new ArrayList<>();
            for (Espacio espacio : espaciosDis) {
                for (Reserva r : reservas) {
                    if (r.getFechaReserva().equals(fecha) && r.getEspacio() == espacio) {
                        espaciosParaEliminar.add(espacio);
                        break; 
                    }
                }
            }
            
            espaciosDis.removeAll(espaciosParaEliminar);

           if(espaciosDis.isEmpty()){
            System.out.println("No hay espacios para esa fecha :(");
           }
           else{

                for(int i=0; i<espaciosDis.size();i++){
                    System.out.println((i+1)+". " + espaciosDis.get(i).toString());
                }

                int ops = elegirOpcion(1, espaciosDis.size());
                Espacio esp = espaciosDis.get(ops-1);

                System.out.println("BIEN!");
                System.out.print("Ahora ingresa el motivo de tu reserva: ");
                String motivo = scanner.nextLine();

                System.out.println("¿Desea crear la reserva: ");
                System.out.println("1. SI");
                System.out.println("2. NO");
                int option = elegirOpcion(1, 2);
       

                if(option==1){
                    Reserva reserva = new Reserva(this,fecha,esp,EstadoReserva.PENDIENTE,motivo);
                    reserva.cargarReserva();
                    //despues de generar la reserva se le debe notificar al administrador
                    enviarCorreo(reserva);
                }
                else{
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
