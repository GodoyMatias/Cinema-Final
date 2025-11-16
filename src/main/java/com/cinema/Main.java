package com.cinema;
import com.cinema.utils.Login;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("UTN Cinema");
            System.out.println("1- Login");
            System.out.println("2- Register");
            System.out.println("3- Exit");

            String opcion = s.nextLine();

            switch (opcion) {
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
                    return; // Sale del programa sin romper nada

                default:
                    System.out.println("Invalid option");
                    break;
            }

            System.out.println(); // solo para mejorar la lectura del menú
        }
    }
}