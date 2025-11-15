package com.cinema.controllers;
import com.cinema.models.usuarios.Usuario;

import java.util.Scanner;

public class AdminController {
    public static void loginAdmin(Usuario u) {
        System.out.println("1- Gestion Contenido");
        System.out.println("2- Gestion Usuarios");
        System.out.println("3- Logout");
        Scanner s = new Scanner(System.in);
        int op = s.nextInt();
        s.nextLine();
        switch (op){
            case 1:
                System.out.println("Gestion Contenido seleccionado");
                System.out.println("1- Crear Contenido");
                System.out.println("2- Modificar Contenido");
                System.out.println("3- Eliminar Contenido");
                int opContenido = s.nextInt();
                s.nextLine();
                switch (opContenido){
                    case 1:
                        System.out.println("Crear Contenido seleccionado");

                }
        }
    }
}
