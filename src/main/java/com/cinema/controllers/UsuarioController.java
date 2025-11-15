package com.cinema.controllers;

import com.cinema.models.usuarios.Usuario;
import com.cinema.service.UsuarioService;

import java.sql.SQLOutput;
import java.util.Scanner;

public class UsuarioController {


    public static void usuarioPanel(Usuario user) {
        UsuarioService usuarioService = new UsuarioService();
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
                System.out.println("Ver Contenido Disponible seleccionado");
                /// /// IMPLEMENTAR FUNCIONALIDAD
                System.out.println("Mostrando contenido disponible... (funcionalidad en desarrollo)");
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
}
