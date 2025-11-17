package com.cinema.controllers;

import com.cinema.exceptions.EmailNoValidoException;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Resenia;
import com.cinema.models.usuarios.Usuario;
import com.cinema.service.ContendioService;
import com.cinema.service.ReseniaService;
import com.cinema.service.UsuarioService;
import com.cinema.utils.ConsoleUtils;

import java.util.Scanner;

public class UsuarioController {

    // ============================================================
    // MÉTODO PRINCIPAL DEL PANEL DE USUARIO
    // ============================================================

//    public static void usuarioPanel(Usuario user) {
//        UsuarioService usuarioService = new UsuarioService();
//        ContendioService contendioService = new ContendioService();
//
//        mostrarEncabezadoUsuario(user);
//        mostrarMenuUsuario();
//
//        Scanner s = new Scanner(System.in);
//        int opcion = leerEntero(s);
//
//        switch (opcion) {
//            case 1 -> mostrarPerfil(user);
//            case 2 -> editarPerfil(s, user, usuarioService);
//            case 3 -> eliminarPerfil(user, usuarioService);
//            case 4 -> opcionesDeContenido(s, contendioService, user.getId());
//            case 5 -> verPlaylists();
//            default -> System.out.println("Opción inválida");
//        }
//    }

    public static void usuarioPanel(Usuario user) {
        UsuarioService usuarioService = new UsuarioService();
        ContendioService contendioService = new ContendioService();

        Scanner s = new Scanner(System.in);

        while (true) {
            mostrarEncabezadoUsuario(user);
            mostrarMenuUsuario();

            int opcion = leerEntero(s);

            switch (opcion) {
                case 1 -> mostrarPerfil(user);
                case 2 -> editarPerfil(s, user, usuarioService);
                case 3 -> {
                    eliminarPerfil(user, usuarioService);
                    return; // si borró el perfil, salimos del panel
                }
                case 4 -> opcionesDeContenido(s, contendioService, user.getId());
                case 5 -> verPlaylists();
                case 0 -> {
                    System.out.println("Saliendo del panel...");
                    return; // rompe el while y vuelve al menú anterior
                }
                default -> System.out.println("Opción inválida");
            }

            System.out.println("\nPresione Enter para continuar...");
            s.nextLine(); // pausa para que vea el resultado
        }
    }

    // ============================================================
    // MENÚS Y SECCIONES
    // ============================================================

    private static void mostrarEncabezadoUsuario(Usuario user) {
        System.out.println("Bienvenido al panel de usuario, " + user.getNombre());
        System.out.println("Tu rol es: " + user.getRol());
    }

    private static void mostrarMenuUsuario() {
        System.out.println("""
                1- Ver Perfil
                2- Editar Perfil
                3- Eliminar Perfil
                4- Ver Contenido Disponible
                5- Ver Playlists -----------------------(Funcionalidad a implementar)------------------------------
                0- Salir
                Seleccione una opción:
                """);
    }

    private static void mostrarMenuContenido() {
        System.out.println("""
                Opciones de Contenido:
                1- Reproducir
                2- Agregar a Playlist -----------------------(Funcionalidad a implementar)------------------------------
                3- Agregar reseña
                4- Editar reseña
                5- Eliminar reseña
                Seleccione una opción:
                """);
    }

    // ============================================================
    // ACCIONES DEL PANEL
    // ============================================================

    private static void mostrarPerfil(Usuario user) {
        System.out.println("Ver Perfil seleccionado");
        System.out.println(user);
    }

    private static void eliminarPerfil(Usuario user, UsuarioService usuarioService) {
        System.out.println("Eliminar Perfil seleccionado");
        usuarioService.baja(user.getId());
        System.out.println("Perfil eliminado. Lo sentimos verte ir, " + user.getNombre());
    }

    private static void verPlaylists() {
        System.out.println("Ver Playlists seleccionado");
        System.out.println("Mostrando tus playlists... (funcionalidad en desarrollo)");
    }

    // ============================================================
    // EDITAR PERFIL
    // ============================================================

    private static void editarPerfil(Scanner s, Usuario user, UsuarioService usuarioService) {
        System.out.println("Editar Perfil seleccionado");

        System.out.println("Ingrese el nuevo nombre:");
        String nuevoNombre = s.nextLine();

        System.out.println("Ingrese el nuevo email:");
        String nuevoEmail = s.nextLine();
        try {
            usuarioService.verificarEmail(nuevoEmail);
        }
        catch (EmailNoValidoException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Ingrese la nueva contraseña:");
        String nuevaContrasena = s.nextLine();

        Usuario usuarioActualizado = new Usuario(nuevoNombre, nuevaContrasena, nuevoEmail, user.getRol());
        usuarioActualizado.setId(user.getId());

        usuarioService.modificar(usuarioActualizado);
    }

    // ============================================================
    // CONTENIDO
    // ============================================================

    private static void verContenidoDisponible(ContendioService servicio) {
        System.out.println("Contenido Disponible:");
        servicio.listar().stream()
                .filter(Contenido::isEstado)
                .forEach(System.out::println);
    }

    private static Contenido seleccionarContenido(Scanner s, ContendioService servicio) {
        verContenidoDisponible(servicio);

        System.out.println("Ingrese el ID del contenido que desea ver:");
        String contenidoId = s.nextLine();

        ConsoleUtils.fakeClear();

        Contenido contenido = servicio.consulta(contenidoId);
        System.out.println(contenido);

        return contenido;
    }

    private static void opcionesDeContenido(Scanner s, ContendioService servicio, String idUsuario) {
        Contenido contenido = seleccionarContenido(s, servicio);
        if (contenido == null) {
            System.out.println("Contenido no encontrado.");
            return;
        }
        ReseniaService reseniaService = new ReseniaService(contenido.getId());

        mostrarMenuContenido();
        int op = leerEntero(s);

        switch (op) {

            case 1 -> reproducir(contenido);

            case 2 -> agregarAPlaylist();

            case 3 -> agregarResenia(s, idUsuario, contenido);

            case 4 -> editarResenia(s, idUsuario, reseniaService);

            case 5 -> eliminarResenia(idUsuario, reseniaService);

            default -> System.out.println("Opción inválida");
        }
    }

    private static void reproducir(Contenido contenido) {
        System.out.println("Reproduciendo " + contenido.getTitulo() + " ▶");
    }

    private static void agregarAPlaylist() {
        System.out.println("Agregar a Playlist seleccionado (funcionalidad en desarrollo)");
    }

    // ============================================================
    // RESEÑAS
    // ============================================================

    private static void agregarResenia(Scanner s, String idUsuario, Contenido contenido) {
        System.out.println("Agregar reseña seleccionado");

        System.out.println("Ingrese la cantidad de estrellas:");
        int estrellas = leerEntero(s);

        System.out.println("Ingrese su comentario:");
        String comentario = s.nextLine();

        Resenia resenia = new Resenia(idUsuario, contenido.getId(), estrellas, new StringBuilder(comentario));
        ReseniaService service = new ReseniaService(contenido.getId());

        service.alta(resenia);
    }

    private static void editarResenia(Scanner s, String idUsuario, ReseniaService reseniaService) {
        System.out.println("Editar reseña seleccionado");

        Resenia reseniaExistente = reseniaService.buscarPorUsuario(idUsuario);

        if (reseniaExistente == null) {
            System.err.println("No puedes editar una reseña que no has creado.");
            return;
        }

        System.out.println("Ingrese la nueva cantidad de estrellas:");
        int estrellas = leerEntero(s);

        System.out.println("Ingrese su nuevo comentario:");
        String comentario = s.nextLine();

        reseniaExistente.setEstrellas(estrellas);
        reseniaExistente.setComentario(new StringBuilder(comentario));

        boolean actualizado = reseniaService.modificar(reseniaExistente);

        if (actualizado) {
            System.out.println("Reseña actualizada correctamente.");
        } else {
            System.err.println("No se pudo modificar la reseña.");
        }
    }

    public static void eliminarResenia(String idUsuario, ReseniaService reseniaService) {
        System.out.println("Eliminar reseña seleccionado");

        Resenia reseniaExistente = reseniaService.buscarPorUsuario(idUsuario);

        if (reseniaExistente == null) {
            System.err.println("No puedes baja una reseña que no has creado.");
            return;
        }

        boolean eliminado = reseniaService.baja(reseniaExistente.getId());

        if (eliminado) {
            System.out.println("Reseña eliminada correctamente.");
        } else {
            System.err.println("No se pudo baja la reseña.");
        }
    }

    // ============================================================
    // UTILIDADES
    // ============================================================

    private static int leerEntero(Scanner s) {
        while (!s.hasNextInt()) {
            System.out.println("Entrada inválida. Ingrese un número:");
            s.nextLine();
        }
        int numero = s.nextInt();
        s.nextLine();
        return numero;
    }
}
