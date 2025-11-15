package com.cinema.controllers;
import com.cinema.models.usuarios.Administrador;
import com.cinema.models.usuarios.Rol;
import com.cinema.models.usuarios.Usuario;

import java.util.HashSet;
import java.util.Scanner;

public class AdminController {
    public static void loginAdmin(Usuario u) {
        Administrador administrador = new Administrador();
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
                        crearUsuario(s, administrador);
                        break;
                    case 2:
                        modificarUsuario(s, administrador);
                        break;
                    case 3:
                        eliminarUsuairo(s, administrador);
                        break;
                    case 4:
                        System.out.println("Listar Usuarios seleccionado");
                        System.out.println(administrador.listarUsuarios());
                        break;
                    case 5:
                        leerUsuario(s, administrador);
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
        }
    }

    public static void crearUsuario(Scanner s, Administrador administrador){
        System.out.println("Crear Usuario seleccionado");
        System.out.println("Please enter your details to register");
        System.out.println("Name: ");
        String nombre = s.nextLine();
        System.out.println("Email: ");
        String email = s.nextLine();
        System.out.println("Password: ");
        String password = s.nextLine();
        Usuario newUser = new Usuario(nombre, password, email, Rol.BASE);
        administrador.crearUsuario(newUser);
    }

    public static void modificarUsuario(Scanner s, Administrador administrador) {
        System.out.println("Modificar Usuario seleccionado");
        System.out.println("Ingrese el id del usuario a modificar:");
        int id = s.nextInt();
        s.nextLine();
        System.out.println("Ingrese el nuevo nombre:");
        String nuevoNombre = s.nextLine();
        System.out.println("Ingrese el nuevo email:");
        String nuevoEmail = s.nextLine();
        System.out.println("Ingrese el nuevo password:");
        String nuevoPassword = s.nextLine();
        Usuario usuarioModificado = new Usuario(nuevoNombre, nuevoPassword, nuevoEmail, Rol.BASE);
        usuarioModificado.setId(id);
        administrador.actualizarUsuario(usuarioModificado);
    }

    public static void eliminarUsuairo(Scanner s, Administrador administrador){
        System.out.println("Eliminar Usuario seleccionado");
        System.out.println("Ingrese el id del usuario a eliminar:");
        int id = s.nextInt();
        s.nextLine();
        administrador.eliminarUsuario(id);
    }

    public static void leerUsuario(Scanner s, Administrador administrador){
        System.out.println("Buscar Usuario seleccionado");
        System.out.println("Ingrese el id del usuario a buscar:");
        int id = s.nextInt();
        s.nextLine();
        Usuario usuarioBuscado = administrador.leerUsuario(id);
        System.out.println(usuarioBuscado.toString());
    }
}
