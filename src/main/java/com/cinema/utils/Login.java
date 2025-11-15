package com.cinema.utils;

import com.cinema.Rol;
import com.cinema.data.gestorUsuariosJson;
import com.cinema.models.usuarios.Usuario;

import java.util.Scanner;

public class Login {
    public static void auth() {
        ConsoleUtils.fakeClear();
        System.out.println("Login Menu");
        System.out.println("Please enter your credentials");
        Scanner s = new Scanner(System.in);
        System.out.println("Email: ");
        String email = s.nextLine();
        System.out.println("Password: ");
        String password = s.nextLine();
        gestorUsuariosJson g = new gestorUsuariosJson();
        Usuario user = g.login(email, password);
        ConsoleUtils.fakeClear();
        if (user != null) {
            System.out.println("Login successful! Welcome " + user.getNombre());
        } else {
            System.out.println("Login failed: invalid email or password.");
            }
    }

    public static void register() {
        Scanner s = new Scanner(System.in);
        ConsoleUtils.fakeClear();
        System.out.println("Register Menu");
        System.out.println("Please enter your details to register");
        System.out.println("Name: ");
        String nombre = s.nextLine();
        System.out.println("Email: ");
        String email = s.nextLine();
        System.out.println("Password: ");
        String password = s.nextLine();

        Usuario newUser = new Usuario(nombre, password, email, Rol.BASE);

        gestorUsuariosJson g = new gestorUsuariosJson();
        boolean registrado = g.registrar(newUser);

        ConsoleUtils.fakeClear();
        if (registrado) {
            System.out.println("User registered successfully with Email: " + newUser.getEmail());
        } else {
            System.out.println("Registration failed: a user with that email already exists.");
        }
    }

}
