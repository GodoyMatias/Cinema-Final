package com.cinema.controllers;

import com.cinema.exceptions.ContenidoNoEncontradoException;
import com.cinema.exceptions.EmailNoValidoException;
import com.cinema.exceptions.PlaylistNoEncontradaException;
import com.cinema.exceptions.UsuarioNoEncontradoException;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Resenia;
import com.cinema.models.playlist.Playlist;
import com.cinema.models.usuarios.Usuario;
import com.cinema.service.ContenidoService;
import com.cinema.service.PlaylistService;
import com.cinema.service.ReseniaService;
import com.cinema.service.UsuarioService;
import com.cinema.utils.Colores;
import com.cinema.utils.ConsoleUtils;

import java.util.Scanner;

public class UsuarioController {
    private static final UsuarioService usuarioService = new UsuarioService();
    private static final ContenidoService contenidoService = new ContenidoService();
    private static final ReseniaService reseniaService = new ReseniaService();
    private  static final PlaylistService playlistService = new PlaylistService();

    // ============================================================
    // MÉTODO PRINCIPAL DEL PANEL DE USUARIO
    // ============================================================

    public static void usuarioPanel(Usuario user) {
        UsuarioService usuarioService = new UsuarioService();
        ContenidoService contendioService = new ContenidoService();


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
                case 5 -> crearPlaylist(s, user);
                case 6 -> eliminarPlaylist(s, user);
                case 7 -> editarPlaylist(s, user);
                case 8 -> verPlaylists(user);
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
                5- Crear Playlist
                6- Eliminar Playlist
                7- Editar Playlist
                8- Ver mis Playlists
                0- Salir
                Seleccione una opción:
                """);
    }

    private static void mostrarMenuContenido() {
        System.out.println("""
                Opciones de Contenido:
                1- Reproducir
                2- Agregar a Playlist
                3- Eliminar de Playlist
                4- Agregar reseña
                5- Editar reseña
                6- Eliminar reseña
                7- Ver mi reseña
                8- Ver todas las reseñas
                0- Volver al panel de usuario
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

    private static void crearPlaylist(Scanner s, Usuario usuario) {
        System.out.println("Crear Playlist seleccionado");

        System.out.println("Ingrese el nombre de la nueva playlist:");
        String nombrePlaylist = s.nextLine();
        Playlist nuevaPlaylist = new Playlist(nombrePlaylist, usuario);
        // agregar la playlist al usuario activo
        usuario.getPlaylists().add(nuevaPlaylist);
        usuarioService.modificar(usuario);

        System.out.println("¿Desea agregar contenidos a la playlist? (s/n):");
        String respuesta = s.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            do {
                System.out.println("Ingrese el ID del contenido a agregar:");
                String idContenido = s.nextLine();
                try {
                    contenidoService.validarExistencia(idContenido);
                } catch (ContenidoNoEncontradoException e) {
                    System.err.println(e.getMessage());
                    return;
                }
                agregarAPlaylist(s, usuario.getId(), contenidoService.consulta(idContenido));

                System.out.println("¿Desea agregar otro contenido? (s/n):");
                respuesta = s.nextLine();
            } while (respuesta.equalsIgnoreCase("s"));

        }
    }

    private static void eliminarPlaylist(Scanner s, Usuario usuario) {
        try {
            playlistService.validarPlaylistActivas(usuario);
        } catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Eliminar Playlist seleccionado");
        System.out.println("Ingrese el ID de la playlist a eliminar:");
        String idPlaylist = s.nextLine();
        try {
            playlistService.validarExistenciaPlaylist(idPlaylist);
        }
        catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }
        playlistService.baja(idPlaylist);

    }

    private static void editarPlaylist(Scanner s, Usuario usuario) {
        try {
            playlistService.validarPlaylistActivas(usuario);
        } catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Editar Playlist seleccionado");
        System.out.println("Ingrese el ID de la playlist a editar:");
        String idPlaylist = s.nextLine();
        try {
            playlistService.validarExistenciaPlaylist(idPlaylist);
        } catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Ingrese el nuevo nombre de la playlist:");
        String nuevoNombre = s.nextLine();

        System.out.println("¿Desea agregar contenidos a la playlist? (s/n):");
        String respuesta = s.nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            do {
                System.out.println("Ingrese el ID del contenido a agregar:");
                String idContenido = s.nextLine();
                try {
                    contenidoService.validarExistencia(idContenido);
                } catch (ContenidoNoEncontradoException e) {
                    System.err.println(e.getMessage());
                    return;
                }
                agregarAPlaylist(s, usuario.getId(), contenidoService.consulta(idContenido));

                System.out.println("¿Desea agregar otro contenido? (s/n):");
                respuesta = s.nextLine();
            } while (respuesta.equalsIgnoreCase("s"));

        }
        System.out.println("¿Desea eliminar contenidos de la playlist? (s/n):");
        respuesta = s.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            do {
                System.out.println("Ingrese el ID del contenido a eliminar:");
                String idContenido = s.nextLine();
                try {
                    contenidoService.validarExistencia(idContenido);
                } catch (ContenidoNoEncontradoException e) {
                    System.err.println(e.getMessage());
                    return;
                }
                eliminarDePlaylist(s, usuario.getId(), contenidoService.consulta(idContenido));

                System.out.println("¿Desea eliminar otro contenido? (s/n):");
                respuesta = s.nextLine();
            } while (respuesta.equalsIgnoreCase("s"));

        }

    }

    private static void verPlaylists(Usuario user) throws PlaylistNoEncontradaException {
        System.out.println("Playlists del usuario");
        // Verificamos si el usuario tiene playlist activas
        try {
            playlistService.validarPlaylistActivas(user);
        } catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        user.getPlaylists().forEach(playlist -> System.out.println(Colores.AZUL + playlist + Colores.RESET));

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

    private static void verContenidoDisponible(ContenidoService servicio) {
        System.out.println("Contenido Disponible:");
        servicio.listar().stream()
                .filter(Contenido::isEstado)
                .forEach(System.out::println);
    }

    private static Contenido seleccionarContenido(Scanner s, ContenidoService servicio) {
        verContenidoDisponible(servicio);

        System.out.println("Ingrese el ID del contenido que desea ver:");
        String id = s.nextLine();
        try {
            contenidoService.validarExistencia(id);
        }
        catch (ContenidoNoEncontradoException e) {
            System.err.println(e.getMessage());
            return null;
        }

        ConsoleUtils.fakeClear();

        Contenido contenido = servicio.consulta(id);
        System.out.println(contenido);

        return contenido;
    }

    private static void opcionesDeContenido(Scanner s, ContenidoService servicio, String idUsuario) {
            Contenido contenido = seleccionarContenido(s, servicio);
        while (true) {
            if (contenido == null) {
                System.out.println("Contenido no encontrado.");
                return;
            }

            ReseniaService reseniaService = new ReseniaService(contenido.getId());

            mostrarMenuContenido();
            int op = leerEntero(s);

            switch (op) {
                case 1 -> reproducir(contenido);
                case 2 -> agregarAPlaylist(s, idUsuario, contenido);
                case 3 -> eliminarDePlaylist(s, idUsuario, contenido);
                case 4 -> agregarResenia(s, idUsuario, contenido, reseniaService);
                case 5 -> editarResenia(s, idUsuario, reseniaService);
                case 6 -> eliminarResenia(idUsuario, reseniaService);
                case 7 -> verReseniaUsuario(reseniaService, idUsuario);
                case 8 -> verReseniasDeContenido(contenido);
                case 0 -> { // ← AGREGO OPCIÓN PARA SALIR DEL SUBMENÚ
                    System.out.println("Volviendo al panel de usuario...");
                    return;
                }
                default -> System.out.println("Opción inválida");
            }
            System.out.println("\nPresione Enter para continuar...");
            s.nextLine();
        }
    }


    private static void reproducir(Contenido contenido) {
        System.out.println("Reproduciendo " + contenido.getTitulo() + " ▶");
    }

    private static void agregarAPlaylist(Scanner s, String idUsuario, Contenido contenido) {
        Usuario user = usuarioService.consulta(idUsuario);
        // verificar si el usuario tiene playlists activas
        try {
            playlistService.validarPlaylistActivas(user);
        }
        catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        verPlaylists(user);
        System.out.println("Ingrese el ID de la playlist a la que desea agregar el contenido:");
        String idPlaylist = s.nextLine();
        // verificar si la playlist existe y está activa
        try {
            playlistService.validarExistenciaPlaylist(idPlaylist);
        }
        catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        Playlist playlistSeleccionada = playlistService.consulta(idPlaylist);
        if (playlistService.validarContenidoEnPlaylist(playlistSeleccionada, contenido)) {
            System.err.println("El contenido no sse encuentra en la playlist seleccionada.");
            return;
        }

        // agrega el contenido a la playlist
        playlistSeleccionada.getContenidos().put(contenido.getId(), contenido);
        playlistService.modificar(playlistSeleccionada);

        System.out.println("Contenido agregado a la playlist " + playlistSeleccionada.getNombre());
    }

    private static void eliminarDePlaylist(Scanner s, String idUsuario, Contenido contenido) {
        Usuario user = usuarioService.consulta(idUsuario);
        try {
            playlistService.validarPlaylistActivas(user);
        }
        catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Seleccione la playlist de la que desea eliminar el contenido:");
        String idPlaylist = s.nextLine();
        // verificar si la playlist existe y está activa
        try {
            playlistService.validarExistenciaPlaylist(idPlaylist);
        }
        catch (PlaylistNoEncontradaException e) {
            System.err.println(e.getMessage());
            return;
        }

        //verificar si el contenido está en la playlist seleccionada
        Playlist playlistSeleccionada = playlistService.consulta(idPlaylist);
        if (!playlistService.validarContenidoEnPlaylist(playlistSeleccionada, contenido)) {
            System.err.println("El contenido no sse encuentra en la playlist seleccionada.");
            return;
        }

        // elimina el contenido de la playlist con baja lógica
        playlistSeleccionada.getContenidos().get(contenido.getId()).setEstado(false);
        playlistService.modificar(playlistSeleccionada);
    }


    // ============================================================
    // RESEÑAS
    // ============================================================

    private static void agregarResenia(Scanner s, String idUsuario, Contenido contenido, ReseniaService reseniaService) {
        Resenia reseniaActiva = reseniaService.buscarPorUsuario(idUsuario);
        if (reseniaActiva.isEstado()) {
            System.err.println("El usuario ya ha dejado su reseña en este contenido. No puede agregar otra.");
            return;
        }

        System.out.println("Agregar reseña seleccionado");

        System.out.println("Ingrese la cantidad de estrellas:");
        int estrellas = leerEntero(s);
        try {
            reseniaService.validarEstrellas(estrellas);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }
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
        try {
            reseniaService.validarEstrellas(estrellas);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

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

        if (!reseniaExistente.isEstado()) {
            System.err.println("El usuario no ha dejado su reseña en este contenido. No se puede eliminar.");
            return;
        }

        boolean eliminado = reseniaService.baja(reseniaExistente.getId());

        if (eliminado) {
            System.out.println("Reseña eliminada correctamente.");
        } else {
            System.err.println("No se pudo baja la reseña.");
        }
    }

    public static void verReseniaUsuario(ReseniaService reseniaService, String idUsuario) {
        Resenia resenia = reseniaService.buscarPorUsuario(idUsuario);
        if (resenia != null && resenia.isEstado()) {
            System.out.println("Tu reseña:");
            System.out.println(resenia);
        } else {
            System.err.println("No has dejado una reseña para este contenido. No hay nada que mostrar.");
        }
    }

    public static void verReseniasDeContenido(Contenido contenido) {
        System.out.println(Colores.AZUL + "Reseñas para " + contenido.getTitulo() + ":" + Colores.RESET);
        ReseniaService reseniaService = new ReseniaService(contenido.getId());
        for (Resenia r : reseniaService.listar()) {
            System.out.println(r);
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
