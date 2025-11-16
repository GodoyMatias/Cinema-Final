package com.cinema.controllers;

import com.cinema.data.GestorUsuariosJson;
import com.cinema.exceptions.EmailNoValidoException;
import com.cinema.models.usuarios.Rol;
import com.cinema.models.usuarios.Usuario;
import com.cinema.service.UsuarioService;
import com.cinema.utils.Colores;
import com.cinema.utils.ConsoleUtils;

import java.util.HashSet;
import java.util.Scanner;

public class RegisterController {
    private static final UsuarioService usuarioService = new UsuarioService();

    public static void menuRegistrar() {
        Scanner s = new Scanner(System.in);
        ConsoleUtils.fakeClear();

        System.out.println(Colores.CYAN + "PANEL REGISTRAR USUARIO" + Colores.RESET);
        System.out.println("Register Menu");
        System.out.println("Please enter your details to menuRegistrar");
        System.out.println("Name: ");
        String nombre = s.nextLine();
        System.out.println("Email: ");
        String email = s.nextLine();
        try {
            usuarioService.verificarEmail(email);
        } catch (EmailNoValidoException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Password: ");
        String password = s.nextLine();

        Usuario newUser = new Usuario(nombre, password, email, Rol.BASE);
        boolean registrado = usuarioService.alta(newUser);

        ConsoleUtils.fakeClear();
        if (registrado) {
            System.out.println("User registered successfully with Email: " + newUser.getEmail());
        } else {
            System.out.println("Registration failed: a user with that email already exists.");
        }
    }

    /*
    public static boolean registrar(Usuario u, GestorUsuariosJson g) {

        if (u == null || u.getEmail() == null) return false;
        HashSet<Usuario> lista = g.archivoALista();
        String target = u.getEmail().trim().toLowerCase();
        for (Usuario x : lista) {
            if (x.getEmail() != null && x.getEmail().trim().toLowerCase().equals(target)) {
                return false; // ya existe
            }
        }
        // Añadir y persistir

        return usuarioService.alta(u);
    }
    */
}
