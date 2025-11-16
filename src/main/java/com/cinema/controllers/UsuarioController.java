package com.cinema.controllers;

import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Resenia;
import com.cinema.models.usuarios.Usuario;
import com.cinema.service.ContendioService;
import com.cinema.service.ReseniaService;
import com.cinema.service.UsuarioService;
import com.cinema.utils.ConsoleUtils;

import java.sql.SQLOutput;
import java.util.Map;
import java.util.Scanner;

public class UsuarioController {
    public static void usuarioPanel(Usuario user) {
        UsuarioService usuarioService = new UsuarioService();
        ContendioService contendioService = new ContendioService();
        System.out.println("Bienvenido al panel de usuario, " + user.getNombre());
        System.out.println("Tu rol es: " + user.getRol());
        System.out.println("1- Ver Perfil");
        System.out.println("2- Editar Perfil");
        System.out.println("3- Eliminar Perfil");
        System.out.println("4- Ver Contenido Disponible");
        System.out.println("5- Ver Playlists"); /// es esto????????????????
        System.out.println("Seleccione una opcion:");
        Scanner s = new Scanner(System.in);
        int op = s.nextInt();
        s.nextLine();
        switch (op) {
            case 1:
                System.out.println("Ver Perfil seleccionado");
                System.out.println(user);
                break;
            case 2:
                editarPerfil(s, user, usuarioService);
                break;
            case 3:
                System.out.println("Eliminar Perfil seleccionado");
                user.setEstado(false);
                System.out.println("Perfil eliminado. Lo sentimos verte ir, " + user.getNombre());
                break;
            case 4:
                opcionesDeContenido(s, contendioService, user.getId());
                break;
            case 5:
                System.out.println("Ver Playlists seleccionado");
                /// /// IMPLEMENTAR FUNCIONALIDAD
                System.out.println("Mostrando tus playlists... (funcionalidad en desarrollo)");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }

    }

    private static void editarPerfil(Scanner s, Usuario user, UsuarioService usuarioService) {
        System.out.println("Editar Perfil seleccionado");
        System.out.println("Ingrese el nuevo nombre:");
        String nuevoNombre = s.nextLine();
        System.out.println("Ingrese el nuevo email:");
        String nuevoEmail = s.nextLine();
        System.out.println("Ingrese la nueva contrasena:");
        String nuevaContrasena = s.nextLine();

        Usuario usuarioActualizado = new Usuario(nuevoNombre, nuevaContrasena, nuevoEmail, user.getRol());
        usuarioActualizado.setId(user.getId());
        usuarioService.actualizar( usuarioActualizado );
    }

    private static void verContenidoDisponible(ContendioService contendioService) {
        System.out.println("Contenido Disponible:");
        for (Contenido c : contendioService.listar()) {
            if (c.isEstado()) {
                System.out.println(c);
            }
        }
    }

    private static Contenido seleccionarContenido(Scanner s, ContendioService contendioService) {
        verContenidoDisponible(contendioService);
        System.out.println("Ingrese el ID del contenido que desea ver:");
        int contenidoId = s.nextInt();
        s.nextLine();
        ConsoleUtils.fakeClear();
        Contenido contenidoSeleccionado = contendioService.leer(contenidoId);
        System.out.println(contenidoSeleccionado);
        return contenidoSeleccionado;
    }

    private static void opcionesDeContenido(Scanner s, ContendioService contendioService, int idUsuario) {
        Contenido contenido = seleccionarContenido(s, contendioService);
        ReseniaService reseniaService = new ReseniaService(contenido.getId());
        System.out.println("Opciones de Contenido:");
        System.out.println("1- Reproducir");
        System.out.println("2- Agregar a Playlist");
        System.out.println("3- Agregar reseña");
        System.out.println("4- Editar reseña");
        System.out.println("5- Eliminar reseña");
        System.out.println("Seleccione una opcion:");
        int opContenido = s.nextInt();
        s.nextLine();
        switch (opContenido) {
            case 1:
                System.out.println("Reproduciendo " + contenido.getTitulo() + " ▶");
                break;
            case 2:
                System.out.println("Agregar a Playlist seleccionado (funcionalidad en desarrollo)");
                break;
            case 3:
                agregarResenia(s, idUsuario, contenido);
                break;
            case 4:
                System.out.println("Editar reseña seleccionado ");
                System.out.println("Ingrese la nueva cantidad de estrellas: ");
                int estrellas = s.nextInt();
                s.nextLine();
                System.out.println("Ingrese su nuevo comentario: ");
                String comentario = s.nextLine();
                System.out.println("ID Usuario: " + idUsuario);
                System.out.println("ID Contenido: " + contenido.getId());
                System.out.println("Estrellas: " + estrellas);
                System.out.println("Comentario: " + comentario);
                Resenia resenia = new Resenia(idUsuario, contenido.getId(), estrellas, new StringBuilder(comentario));
                resenia.setId(contenido.getId());
                System.err.println(resenia);
                reseniaService.actualizar(resenia);
                break;
            case 5:
                System.out.println("Eliminar reseña seleccionado");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    private static void agregarResenia(Scanner s, int idUsuario, Contenido contenido){
        System.out.println("Agregar reseña seleccionado");
        System.out.println("Ingrese la cantidad de estrellas: ");
        int estrellas = s.nextInt();
        s.nextLine();
        System.out.println("Ingrese su comentario: ");
        String comentario = s.nextLine();
        Resenia resenia = new Resenia(idUsuario, contenido.getId(), estrellas, new StringBuilder(comentario));
        ReseniaService reseniaService = new ReseniaService(contenido.getId());
        reseniaService.crear(resenia);
    }
}
