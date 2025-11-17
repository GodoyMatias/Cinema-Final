package com.cinema.controllers;

import com.cinema.data.GestorUsuariosJson;
import com.cinema.models.usuarios.Rol;
import com.cinema.models.usuarios.Usuario;
import com.cinema.utils.Colores;
import com.cinema.utils.ConsoleUtils;

import java.util.HashSet;
import java.util.Scanner;

public class LoginController {
    public static void loginAuth() {
        ConsoleUtils.fakeClear();

        System.out.println(Colores.CYAN + "PANEL INICIAR SESIÓN" + Colores.RESET);
        System.out.println("Login Menu");
        System.out.println("Please enter your credentials");
        Scanner s = new Scanner(System.in);
        System.out.println("Email: ");
        String email = s.nextLine();
        System.out.println("Password: ");
        String password = s.nextLine();
        GestorUsuariosJson g = new GestorUsuariosJson();
        Usuario user = login(email, password, g);


        ConsoleUtils.fakeClear();

        if (user != null && user.getEstado() == true) {
            if(user.getRol() == Rol.ADMINISTRADOR){
                AdminController.adminPanel();
            }else if(user.getRol() == Rol.BASE){
                UsuarioController.usuarioPanel(user);
            }

        } else {
            System.err.println("Login failed: invalid email or password.");
            }
    }

    public static Usuario login(String email, String password, GestorUsuariosJson g) {
        if (email == null || password == null) return null;
        String targetEmail = email.trim().toLowerCase();
        String targetPassword = password; // considerar hashing en producción
        HashSet<Usuario> lista = g.archivoALista();
        for (Usuario u : lista) {
            if (u.getEmail() != null && u.getEmail().trim().toLowerCase().equals(targetEmail)
                    && u.getPassword() != null && u.getPassword().equals(targetPassword)) {
                return u; // encontrado
            }
        }
        return null; // no encontrado
    }


}
