package com.cinema;

import com.cinema.data.GestorContenidosJSON;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Pelicula;
import com.cinema.utils.Login;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("UTN Cinema");
        System.out.println("1-  Login");
        System.out.println("2-  Register");
        System.out.println("3-  Exit");
        Scanner s = new Scanner(System.in);
        switch (s.nextLine()) {
            case "1":
                System.out.println("Login selected");
                Login.auth();
                break;
                case "2":
                System.out.println("Register selected");
                Login.register();
                break;
            case "3":
                System.out.println("Gracias por aprobarnos :) !");
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }
}