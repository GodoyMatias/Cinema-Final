package com.cinema.controllers;

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

    public static void usuarioPanel(Usuario user) {
        UsuarioService usuarioService = new UsuarioService();
        ContendioService contendioService = new ContendioService();

        mostrarEncabezadoUsuario(user);
        mostrarMenuUsuario();

        Scanner s = new Scanner(System.in);
        int opcion = leerEntero(s);

        switch (opcion) {
            case 1 -> mostrarPerfil(user);
            case 2 -> editarPerfil(s, user, usuarioService);
            case 3 -> eliminarPerfil(user);
            case 4 -> opcionesDeContenido(s, contendioService, user.getId());
            case 5 -> verPlaylists();
            default -> System.out.println("Opción inválida");
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
                5- Ver Playlists
                Seleccione una opción:
                """);
    }

    private static void mostrarMenuContenido() {
        System.out.println("""
                Opciones de Contenido:
                1- Reproducir
                2- Agregar a Playlist
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

    private static void eliminarPerfil(Usuario user) {
        System.out.println("Eliminar Perfil seleccionado");
        user.setEstado(false);
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

        System.out.println("Ingrese la nueva contraseña:");
        String nuevaContrasena = s.nextLine();

        Usuario usuarioActualizado = new Usuario(nuevoNombre, nuevaContrasena, nuevoEmail, user.getRol());
        usuarioActualizado.setId(user.getId());

        usuarioService.actualizar(usuarioActualizado);
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
        int contenidoId = leerEntero(s);

        ConsoleUtils.fakeClear();

        Contenido contenido = servicio.leer(contenidoId);
        System.out.println(contenido);

        return contenido;
    }

    private static void opcionesDeContenido(Scanner s, ContendioService servicio, int idUsuario) {
        Contenido contenido = seleccionarContenido(s, servicio);
        ReseniaService reseniaService = new ReseniaService(contenido.getId());

        mostrarMenuContenido();
        int op = leerEntero(s);

        switch (op) {

            case 1 -> reproducir(contenido);

            case 2 -> agregarAPlaylist();

            case 3 -> agregarResenia(s, idUsuario, contenido);

            case 4 -> editarResenia(s, idUsuario, reseniaService);

            case 5 -> System.out.println("Eliminar reseña seleccionado");

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

    private static void agregarResenia(Scanner s, int idUsuario, Contenido contenido) {
        System.out.println("Agregar reseña seleccionado");

        System.out.println("Ingrese la cantidad de estrellas:");
        int estrellas = leerEntero(s);

        System.out.println("Ingrese su comentario:");
        String comentario = s.nextLine();

        Resenia resenia = new Resenia(idUsuario, contenido.getId(), estrellas, new StringBuilder(comentario));
        ReseniaService service = new ReseniaService(contenido.getId());

        service.crear(resenia);
    }

    private static void editarResenia(Scanner s, int idUsuario, ReseniaService reseniaService) {
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

        boolean actualizado = reseniaService.actualizar(reseniaExistente);

        if (actualizado) {
            System.out.println("Reseña actualizada correctamente.");
        } else {
            System.err.println("No se pudo actualizar la reseña.");
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
