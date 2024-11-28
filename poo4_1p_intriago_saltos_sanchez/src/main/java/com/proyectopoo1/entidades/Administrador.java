package com.proyectopoo1.entidades;

import java.util.ArrayList;
import java.util.Scanner;
import com.proyectopoo1.enums.DisponibilidadEsp;
import com.proyectopoo1.enums.EstadoReserva;

/**
 * Representa a un administrador dentro del sistema de reservas. Permite gestionar las solicitudes de reserva realizadas 
 * por estudiantes y profesores, aprobándolas o rechazándolas según sea necesario. Además, el administrador puede consultar 
 * todas las reservas existentes y notificar a los usuarios mediante correo electrónico sobre el estado de sus solicitudes.
 * 
 */
public class Administrador extends Usuario {

    // Variable de instancia
    private String cargo;

    /**
     * Constructor que inicializa a administrador con todos sus atributos especificados
     * @param cargo
     * @param codUnico código único que identifica al administrador
     * @param numCedula
     * @param nombres
     * @param apellidos
     * @param user
     * @param password
     * @param correo
     */
    public Administrador(String cargo, String codUnico, String numCedula, String nombres, String apellidos, String user,
            String password, String correo) {
        super(codUnico, numCedula, nombres, apellidos, user, password, correo);
        this.cargo = cargo;
    }

    // Getters
    public String getCargo() {
        return cargo;
    }

    // Setters
    public void serCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    /**
     * Metodo que permite aprobar o rechazar una reserva pendiente
     * @param espacios Lista de espacios no usada en administrador
     * @param reservas Lista de reservas necesaria para buscar las reservas pendientes y
     * que el administrador las acepte o rechace
     * @param sc Scanner necesario para ingresar datos e informacion via teclado
     */
    public void gestionarReserva(ArrayList<Espacio> espacios, ArrayList<Reserva> reservas, Scanner sc) {
        boolean codValido = false;
        String input;
        Reserva reservaSol = null;
        int index = 0;

        // Valida y retorna el codigo ingresado que pertenece a alguna reserva pendiente
        // hecha
        while (!codValido) {
            System.out.print("\nIngresa el código de la reserva: ");
            input = sc.nextLine();

            for (Reserva reserv : reservas) {
                if (reserv.getCodUnico().equals(input) && reserv.getEstadoReserva().equals(EstadoReserva.PENDIENTE)) {
                    codValido = true;
                    reservaSol = reserv;
                    index = reservas.indexOf(reserv);

                }
            }

            if (!codValido) {
                System.out.println("No existe una reserva pendiente con ese código. Intente nuevamente\n");
            }
        }

        System.out.println("\nCod. | Fecha Res. | Tipo Esp. | Nombre esp.  |  Capacidad esp.  | Nombres | Apellidos");
        System.out.println("-".repeat(80));
        System.out.println(reservaSol.getCodUnico() + " | " + reservaSol.getFechaReserva() + " | "
                + reservaSol.getEspacio().getTipoEspacio() + " | " + reservaSol.getEspacio().getNombreEsp() + " | "
                + reservaSol.getEspacio().getCapacidadEsp() + " | " + reservaSol.getUser().getNombres() + " | "
                + reservaSol.getUser().getApellidos());

        System.out.println("\nOpciones:");
        System.out.println("1. Aprobar");
        System.out.println("2. Rechazar");
        int decs = elegirOpcion(1, 2, sc);

        switch (decs) {
            case 1:
                this.enviarCorreo(reservaSol, null, EstadoReserva.APROBADO);
                reservaSol.setEstadoReserva(EstadoReserva.APROBADO);
                reservaSol.getEspacio().setEstadoEsp(DisponibilidadEsp.RESERVADO);
                reservas.set(index, reservaSol);
                Reserva.cargarReservas(reservas);
                System.out.println("\nSolicitud de reserva APROBADA correctamente.");
                break;

            case 2:
                System.out.print("Indique el motivo del rechazo: ");
                String motivo = sc.nextLine();
                this.enviarCorreo(reservaSol, motivo, EstadoReserva.RECHAZADO);
                reservaSol.setEstadoReserva(EstadoReserva.RECHAZADO);
                reservaSol.setMotivoReserva(motivo);
                reservas.set(index, reservaSol);
                Reserva.cargarReservas(reservas);
                System.out.println("\nSolicitud de reserva RECHAZADA correctamente.");
                break;

        }
    }

    @Override
    /**
     * Metodo que consulta todas las reservas creadas por profesores y estudiantes
     * @param reservas Lista de reservas necesaria para mostrar todas las reservas realizadas por 
     * Profesores y Estudiantes
     * @param sc Scanner necesario para ingresar datos e informacion via teclado
     */
    public void consultarReserva(ArrayList<Reserva> reservas, Scanner sc) {
        System.out.println("\nNúmero de reservas creadas: " + Reserva.getContador() + "\n");
        for (Reserva reserv : reservas) {
            if (reserv.getUser() instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) reserv.getUser();
                System.out.println(
                        reserv.getCodUnico() + " - " + reserv.getEstadoReserva() + " - " + reserv.getFechaReserva() +
                                " - " + estudiante.getNombres() + " " + estudiante.getApellidos() + " - "
                                + estudiante.getNumMatricula());
            } else if (reserv.getUser() instanceof Profesor) {
                System.out.println(
                        reserv.getCodUnico() + " - " + reserv.getEstadoReserva() + " - " + reserv.getFechaReserva() +
                                " - " + reserv.getUser().getNombres() + " " + reserv.getUser().getApellidos() + " - "
                                + reserv.getMotivoReserva());
            }
        }

        System.out.println("\nTodas las reservas correctamente cargadas");
    }

}
