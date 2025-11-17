package com.cinema;
import com.cinema.controllers.LoginController;
import com.cinema.controllers.RegisterController;
import com.cinema.utils.Colores;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        while (true) {

            String ascii = """
                 ██████ ██ ███    ██ ███████ ███    ███  █████        ██    ██ ████████ ███    ██ 
                ██      ██ ████   ██ ██      ████  ████ ██   ██       ██    ██    ██    ████   ██ 
                ██      ██ ██ ██  ██ █████   ██ ████ ██ ███████ █████ ██    ██    ██    ██ ██  ██ 
                ██      ██ ██  ██ ██ ██      ██  ██  ██ ██   ██       ██    ██    ██    ██  ██ ██ 
                 ██████ ██ ██   ████ ███████ ██      ██ ██   ██        ██████     ██    ██   ████ 

                                                                                                  """;

            System.out.println(ascii);
            System.out.println("1- Iniciar Sesión");
            System.out.println("2- Registrar Usuario");
            System.out.println("3- Salir");

            String opcion = s.nextLine();

            switch (opcion) {
                case "1":
                    LoginController.loginAuth();
                    break;

                case "2":
                    RegisterController.menuRegistrar();
                    break;

                case "3":
                    System.out.println("Gracias por aprobarnos :) !");
                    return; // Sale del programa sin romper nada

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }

            System.out.println(); // solo para mejorar la lectura del menú
        }
    }
}