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

                        break;
                    case 2:
                        System.out.println("Modificar Contenido seleccionado");
                        break;
                    case 3:
                        System.out.println("Eliminar Contenido seleccionado");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
                break;
            case 2:
                System.out.println("Gestion Usuarios seleccionado");
                System.out.println("1- Crear Usuario");
                System.out.println("2- Modificar Usuario");
                System.out.println("3- Eliminar Usuario");
                System.out.println("4- Listar Usuarios");
                System.out.println("5- Buscar Usuario");
                System.out.println("Seleccione una opcion:");
                int opUsuario = s.nextInt();
                s.nextLine();
                switch (opUsuario) {
                    case 1:
                        System.out.println("Crear Usuario seleccionado");

                        break;
                    case 2:
                        System.out.println("Modificar Usuario seleccionado");
                        break;
                    case 3:
                        System.out.println("Eliminar Usuario seleccionado");
                        break;
                    case 4:
                        System.out.println("Listar Usuarios seleccionado");
                        break;
                    case 5:
                        System.out.println("Buscar Usuario seleccionado");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
        }
    }
}
