package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.proyectopoo1.Sistema;
import com.proyectopoo1.enums.DisponibilidadEsp;
import com.proyectopoo1.enums.EstadoReserva;
import com.proyectopoo1.enums.TipoEspacio;

/**
 * Representa a un estudiante dentro del sistema de reservas. Permite gestionar las reservas de espacios, 
 * consultarlas y realizar solicitudes de reserva con estado pendiente hasta su aprobación por parte de un administrador. 
 * Los estudiantes pueden seleccionar espacios según disponibilidad y especificar el motivo de la reserva.
 * 
 */
public class Estudiante extends Usuario {

    // Variables de instancia
    private String numMatricula;
    private String carrera;

    /**
     * Constructor que inicializa a profesor con todos sus atributos especificados
     * @param numMatricula
     * @param carrera
     * @param codUnico código único que identifica al estudiante
     * @param numCedula
     * @param nombres
     * @param apellidos
     * @param user
     * @param password
     * @param correo
     */
    public Estudiante(String numMatricula, String carrera, String codUnico, String numCedula, String nombres,
            String apellidos, String user, String password, String correo) {
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
    /**
     * Metodo que realiza una reserva con estado pendiente hasta la decision de un administrador
     * @param espacios Lista de espacios necesaria para mostrar cuales son los que estan disponibles
     * @param reservas Lista de reservas necesaria para adjuntar la nueva reserva a esta lista
     * @param sc Scanner para ingresar datos e informacion via teclado
     */
    public void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas, Scanner sc) {
        LocalDate fecha = validarFecha(sc);

        // valida que solo pueda crear una reserva para un mismo día 
        boolean valida = verificarFecha(reservas, fecha);
        if (valida == false) {
            System.out.println("\nYa tiene una reserva para la fecha " + fecha);
        } else {

            // mostrar las canchas o aulas disponibles
            System.out.println("\n1. AULA");
            System.out.println("2. CANCHA");
            int op = elegirOpcion(1, 2, sc);

            // selecciona solo los espacios disponibles para el estudiante del archivo espacio.txt
            ArrayList<Espacio> espaciosDis = new ArrayList<>();
            if (op == 1) {
                for (Espacio espacio : espacios) {
                    if (espacio.getTipoEspacio() == TipoEspacio.AULA
                            && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE) {
                        espaciosDis.add(espacio);
                    }
                }

            } else if (op == 2) {
                for (Espacio espacio : espacios) {
                    if (espacio.getTipoEspacio() == TipoEspacio.CANCHA
                            && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE) {
                        espaciosDis.add(espacio);
                    }
                }
            }

            // elemina aquellos espacios que esten disponibles en el archivo de espacios, 
            // pero que tengan una reserva en la fecha ingresada
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

            // muestra aquellos espacios que esten disponibles y que no tengan reservas agendadas para la fecha ingresada
            if (espaciosDis.isEmpty()) {
                System.out.println("\nNo hay espacios para esa fecha :(");
            } else {

                System.out.println("");
                for (int i = 0; i < espaciosDis.size(); i++) {
                    System.out.println((i + 1) + ". " + espaciosDis.get(i).toString());
                }

                int ops = elegirOpcion(1, espaciosDis.size(), sc);
                // guarda el espacio seleccionado en una variable
                Espacio esp = espaciosDis.get(ops - 1);

                System.out.println("¡BIEN!");
                System.out.print("\nAhora ingresa el motivo de tu reserva: ");
                String motivo = sc.nextLine();

                System.out.println("\n¿Desea crear la reserva?: ");
                System.out.println("\n1. SI");
                System.out.println("2. NO");
                int option = elegirOpcion(1, 2, sc);

                if (option == 1) {
                    // crea una nueva reserva y la carga al archivo reserva.txt
                    Reserva reserva = new Reserva(this, fecha, esp, EstadoReserva.PENDIENTE, motivo);
                    reserva.cargarReserva();
                    // despues de generar la reserva se le debe notificar al administrador
                    enviarCorreo(reserva);
                } else {
                    // regresa al menu si selecciona NO
                    Sistema.main(null);
                }
            }
        }
    }

    @Override
    /**
     * Metodo que consulta una reserva creada por el mismo usuario
     * @param reservas Lista de reservas necesaria para poder recorrer y encontrar la deseada
     * @param sc Scanner necesario para ingresar datos e informacion via teclado
     */
    public void consultarReserva(ArrayList<Reserva> reservas, Scanner sc) {

        boolean fechaReservaDeUser = false;
        Reserva reservaUser = null;
        do {
            LocalDate fecha = validarFecha(sc);

            for (Reserva reserv : reservas) {
                if (reserv.getFechaReserva().equals(fecha) && reserv.getUser() == this) {
                    reservaUser = reserv;
                    fechaReservaDeUser = true;
                    break;
                }
            }
            if (!fechaReservaDeUser) {
                System.out.println("\nDicha fecha no pertenece a ninguna reserva realizada por usted.");
                System.out.println("Intente nuevamente.");
            } else {
                System.out.println("\nDatos reserva: \n");
                System.out.println(
                        reservaUser.getCodUnico() + " - " + fecha + " - " + reservaUser.getEspacio().getTipoEspacio()
                                + " - " + reservaUser.getEspacio().getNombreEsp() + " - " +
                                reservaUser.getEspacio().getCapacidadEsp() + " - " + reservaUser.getUser().getNombres()
                                + " " + reservaUser.getUser().getApellidos() + " - " + reservaUser.getEstadoReserva());
            }
        } while (!fechaReservaDeUser);
    }
}
