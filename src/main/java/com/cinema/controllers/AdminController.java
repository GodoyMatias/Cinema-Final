package com.cinema.controllers;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Genero;
import com.cinema.models.contenido.Pelicula;
import com.cinema.models.contenido.Serie;
import com.cinema.models.usuarios.Administrador;
import com.cinema.models.usuarios.Rol;
import com.cinema.models.usuarios.Usuario;

import java.util.Scanner;

public class AdminController {
    public static void adminPanel() {
        Administrador administrador = new Administrador();
        System.out.println("1- Gestion Contenido");
        System.out.println("2- Gestion Usuarios");
        System.out.println("3- Crear Administrador:");
        Scanner s = new Scanner(System.in);
        int op = s.nextInt();
        s.nextLine();
        switch (op){
            case 1:
                System.out.println("Gestion Contenido seleccionado");
                System.out.println("1- Crear Contenido");
                System.out.println("2- Modificar Contenido");
                System.out.println("3- Eliminar Contenido");
                System.out.println("4- Listar Contenidos");
                System.out.println("5- Buscar Contenido");
                int opContenido = s.nextInt();
                s.nextLine();
                switch (opContenido){
                    case 1:
                        crearContenido(s, administrador);
                        break;
                    case 2:
                        modificarContenido(s, administrador);
                        break;
                    case 3:
                        eliminarContenido(s, administrador);
                        break;
                    case 4:
                        System.out.println("Lista de contenidos seleccionado");
                        System.out.println(administrador.listarContenidos());
                        break;
                    case 5:
                        buscarContenido(s, administrador);
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
                break;
            case 3:
                crearAdminitrador(s, administrador);
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    private static void crearAdminitrador(Scanner s, Administrador administrador) {
        System.out.println("Crear Administrador seleccionado");
        System.out.println("Please enter details to register new administrator");
        System.out.println("Name: ");
        String nombre = s.nextLine();
        System.out.println("Email: ");
        String email = s.nextLine();
        System.out.println("Password: ");
        String password = s.nextLine();
        Usuario newAdmin = new Administrador(nombre, password, email, Rol.ADMINISTRADOR);
        administrador.crearUsuario(newAdmin);
    }

    // ABMCL Usuario

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

    // ABMCL Contenido

    public static void crearContenido(Scanner s, Administrador administrador){
        System.out.println("Crear Contenido seleccionado");
        System.out.println("1- Crear Pelicula");
        System.out.println("2- Crear Serie");
        int opContenido = s.nextInt();
        s.nextLine();
        System.out.println("Ingrese los datos:");
        System.out.println("Titulo: ");
        String titulo = s.nextLine();
        System.out.println("Año: ");
        int anio = s.nextInt();
        s.nextLine();
        System.out.println("Director: ");
        String director = s.nextLine();
        Genero genero = pedirGenero(s);
        System.out.println();
        switch (opContenido) {
            case 1:
                double duracion = pedirDuracion(s);
                Contenido pelicula = new Pelicula(titulo, genero, anio, director, duracion);
                administrador.crearContenido(pelicula);
                break;
            case 2:
                System.out.println("Temporadas: ");
                int temporadas = pedirAtributosSerie(s);
                System.out.println("Episodios: ");
                int episodios = pedirAtributosSerie(s);
                Contenido serie = new Serie(titulo, genero, anio, director, temporadas, episodios);
                administrador.crearContenido(serie);
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    public static double pedirDuracion(Scanner s){
        System.out.println("Duracion: ");
        return s.nextDouble();
    }

    public static Genero pedirGenero(Scanner sc) {
        Genero[] generos = Genero.values();

        while (true) {
            System.out.println("Seleccione un género:");
            for (int i = 0; i < generos.length; i++) {
                System.out.println((i + 1) + ") " + generos[i]);
            }

            try {
                int opcion = Integer.parseInt(sc.nextLine());
                if (opcion >= 1 && opcion <= generos.length) {
                    return generos[opcion - 1];
                }
            } catch (NumberFormatException ignored) {}

            System.out.println("Opción inválida. Intente nuevamente.");
        }
    }

    public static int pedirAtributosSerie(Scanner s){
        int atributo = s.nextInt();
        s.nextLine();
        return atributo;
    }

    public static void modificarContenido(Scanner s, Administrador administrador){
        System.out.println("Modificar Contenido seleccionado");
        System.out.println("Ingrese el id del contenido a modificar:");
        int id = s.nextInt();
        s.nextLine();
        System.out.println("Ingrese los nuevos datos:");
        System.out.println("Titulo: ");
        String titulo = s.nextLine();
        System.out.println("Año: ");
        int anio = s.nextInt();
        s.nextLine();
        System.out.println("Director: ");
        String director = s.nextLine();
        Genero genero = pedirGenero(s);
        System.out.println();
        Contenido contenidoExistente = administrador.leerContenido(id);
        if (contenidoExistente instanceof Pelicula) {
            double duracion = pedirDuracion(s);
            Contenido peliculaModificada = new Pelicula(titulo, genero, anio, director, duracion);
            peliculaModificada.setId(id);
            administrador.actualizarContenido(peliculaModificada);
        } else if (contenidoExistente instanceof Serie) {
            System.out.println("Temporadas: ");
            int temporadas = pedirAtributosSerie(s);
            System.out.println("Episodios: ");
            int episodios = pedirAtributosSerie(s);
            Contenido serieModificada = new Serie(titulo, genero, anio, director, temporadas, episodios);
            serieModificada.setId(id);
            administrador.actualizarContenido(serieModificada);
        } else {
            System.out.println("Tipo de contenido no reconocido.");
        }
    }

    public static void eliminarContenido(Scanner s, Administrador administrador){
        System.out.println("Eliminar Contenido seleccionado");
        System.out.println("Ingrese el id del contenido a eliminar:");
        int id = s.nextInt();
        s.nextLine();
        administrador.eliminarContenido(id);
    }

    public static void buscarContenido(Scanner s, Administrador administrador){
        System.out.println("Buscar Contenido seleccionado");
        System.out.println("Ingrese el id del contenido a buscar:");
        int id = s.nextInt();
        s.nextLine();
        Contenido contenidoBuscado = administrador.leerContenido(id);
        System.out.println(contenidoBuscado.toString());
    }
}
