package com.proyectopoo1.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.proyectopoo1.Sistema;
import com.proyectopoo1.enums.DisponibilidadEsp;
import com.proyectopoo1.enums.EstadoReserva;
import com.proyectopoo1.enums.TipoEspacio;

/**
 * Representa a un profesor dentro del sistema de reservas. Permite gestionar reservas de espacios de manera inmediata 
 * y consultar las reservas creadas. Los profesores pueden seleccionar espacios según disponibilidad y asignar 
 * la reserva a una materia específica dentro de su lista de materias dictadas.
 * 
 */
public class Profesor extends Usuario {

    // Variables de instancia
    private String facultad;
    private ArrayList<String> materiasDict;

    /**
     * Constructor que inicializa a profesor con todos sus atributos especificados
     * @param facultad facultad a la que pertenece el profesor
     * @param materiasDict lista de materias que dicta el profesor
     * @param codUnico código único que identifica al profesor
     * @param numCedula
     * @param nombres
     * @param apellidos
     * @param user
     * @param password
     * @param correo
     */
    public Profesor(String facultad, ArrayList<String> materiasDict, String codUnico, String numCedula, String nombres,
            String apellidos, String user, String password, String correo) {
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.facultad = facultad;
        this.materiasDict = materiasDict;
    }

    // Getters
    public String getFacultad() {
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

    @Override
    /**
     * Metodo que crea una reserva con aprobacion inmediata
     * @param espacios Lista de espacios necesaria para mostrar cuales son los que estan disponibles
     * @param reservas Lista de reservas necesaria para adjuntar la nueva reserva a esta lista
     * @param sc Scanner para ingresar datos e informacion via teclado
     */
    public void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas, Scanner sc) {
        LocalDate fecha = validarFecha(sc);
        
        // valida que solo pueda crear una reserva para un mismo día 
        boolean valida = verificarFecha(reservas, fecha);
        if (valida == false) {
            System.out.println("\nYa tiene una reserva para la fecha" + fecha);
        } else {
            // mostrar las canchas o aulas disponibles
            System.out.println("\n1. AULA");
            System.out.println("2. LABORATORIO");
            System.out.println("3. AUDITORIO");
            int opcionTipoElegido = elegirOpcion(1, 3, sc);

            // selecciona solo los espacios disponibles para el estudiante del archivo espacio.txt
            ArrayList<Espacio> espaciosDisponibles = new ArrayList<>();
            switch (opcionTipoElegido) {
                case 1:
                    for (Espacio espacio : espacios) {
                        if (espacio.getTipoEspacio() == TipoEspacio.AULA
                                && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE) {
                            espaciosDisponibles.add(espacio);
                        }
                    }
                    break;
                case 2:
                    for (Espacio espacio : espacios) {
                        if (espacio.getTipoEspacio() == TipoEspacio.LABORATORIO
                                && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE) {
                            espaciosDisponibles.add(espacio);
                        }
                    }
                    break;
                case 3:
                    for (Espacio espacio : espacios) {
                        if (espacio.getTipoEspacio() == TipoEspacio.AUDITORIO
                                && espacio.getEstadoEsp() == DisponibilidadEsp.DISPONIBLE) {
                            espaciosDisponibles.add(espacio);
                        }
                    }
                    break;
                default:
                    break;
            }

            // elemina aquellos espacios que esten disponibles en el archivo de espacios, 
            // pero que tengan una reserva en la fecha ingresada
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

            // muestra aquellos espacios que esten disponibles y que no tengan reservas agendadas para la fecha ingresada
            if (espaciosDisponibles.isEmpty()) {
                System.out.println("\nNo hay espacios para esa fecha :(");
            } else {
                System.out.println("");
                for (int i = 0; i < espaciosDisponibles.size(); i++) {
                    System.out.println((i + 1) + ". " + espaciosDisponibles.get(i).toString());
                }
                int ops = elegirOpcion(1, espaciosDisponibles.size(), sc);
                // guarda el espacio seleccionado en una variable
                Espacio esp = espaciosDisponibles.get(ops - 1);

                System.out.println("\nElija la materia para la cual es la reserva");
                System.out.println("");
                for (int i = 0; i < materiasDict.size(); i++) {
                    System.out.println((i + 1) + ".- " + materiasDict.get(i));
                }
                int opcionMateriaDictada = elegirOpcion(1, materiasDict.size(), sc);
                String motivoDeReserva = materiasDict.get(opcionMateriaDictada - 1);

                System.out.println("\n¿Desea crear la reserva: ");
                System.out.println("\n1. SI");
                System.out.println("2. NO");
                int opcionCrearReserva = elegirOpcion(1, 2, sc);

                if (opcionCrearReserva == 1) {
                    // crea una nueva reserva y la carga al archivo reserva.txt
                    Reserva reserva = new Reserva(this, fecha, esp, EstadoReserva.APROBADO, motivoDeReserva);
                    reserva.cargarReserva();
                    // despues de generar la reserva se le debe notificar al administrador
                    enviarCorreo(reserva, motivoDeReserva);
                } else {
                    // para regresar el programa al menu principal
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
