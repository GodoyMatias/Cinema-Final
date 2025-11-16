package com.cinema.controllers;

import com.cinema.models.contenido.*;
import com.cinema.models.usuarios.Administrador;
import com.cinema.models.usuarios.Rol;
import com.cinema.models.usuarios.Usuario;
import com.cinema.utils.Colores;

import java.util.Scanner;

public class AdminController {

    // ============================================================
    // PANEL PRINCIPAL
    // ============================================================
    public static void adminPanel() {
        Administrador administrador = new Administrador();
        Scanner s = new Scanner(System.in);

        while (true) { // Bucle para que el menú se repita
            mostrarMenuPrincipal();
            int opcion = leerEntero(s);

            switch (opcion) {
                case 1 -> gestionarContenido(s, administrador);
                case 2 -> gestionarUsuarios(s, administrador);
                case 3 -> crearAdministrador(s, administrador);
                case 0 -> {
                    System.out.println(Colores.VERDE + "Saliendo del panel administrador..." + Colores.RESET);
                    return; // Sale del adminPanel y termina la aplicación
                }
                default -> System.out.println(Colores.ROJO + "Opción inválida" + Colores.RESET);
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println(Colores.CYAN + Colores.NEGRITA + "=== PANEL ADMINISTRADOR ===" + Colores.RESET);
        System.out.println(Colores.AMARILLO + "1- Gestión Contenido" + Colores.RESET);
        System.out.println(Colores.AMARILLO + "2- Gestión Usuarios" + Colores.RESET);
        System.out.println(Colores.AMARILLO + "3- Crear Administrador" + Colores.RESET);
        System.out.println(Colores.AMARILLO + "0- Salir" + Colores.RESET); // Nueva opción
    }


    private static int leerEntero(Scanner s) {
        int valor = s.nextInt();
        s.nextLine();
        return valor;
    }

    // ============================================================
    // GESTIÓN DE CONTENIDOS
    // ============================================================

    private static void gestionarContenido(Scanner s, Administrador administrador) {
        System.out.println(Colores.AZUL + "Gestión Contenido seleccionado" + Colores.RESET);
        mostrarMenuContenido();

        int opcionContenido = leerEntero(s);

        switch (opcionContenido) {
            case 1 -> crearContenido(s, administrador);
            case 2 -> modificarContenido(s, administrador);
            case 3 -> eliminarContenido(s, administrador);
            case 4 -> System.out.println(Colores.VERDE + administrador.listarContenidos() + Colores.RESET);
            case 5 -> buscarContenido(s, administrador);
            default -> System.out.println(Colores.ROJO + "Opción inválida" + Colores.RESET);
        }
    }

    private static void mostrarMenuContenido() {
        System.out.println(Colores.CYAN + "--- Contenido ---" + Colores.RESET);
        System.out.println("1- Crear Contenido");
        System.out.println("2- Modificar Contenido");
        System.out.println("3- Eliminar Contenido");
        System.out.println("4- Listar Contenidos");
        System.out.println("5- Buscar Contenido");
    }

    // ============================================================
    // GESTIÓN DE USUARIOS
    // ============================================================

    private static void gestionarUsuarios(Scanner s, Administrador administrador) {
        System.out.println(Colores.AZUL + "Gestión Usuarios seleccionado" + Colores.RESET);
        mostrarMenuUsuarios();

        int opcionUsuario = leerEntero(s);

        switch (opcionUsuario) {
            case 1 -> crearUsuario(s, administrador);
            case 2 -> modificarUsuario(s, administrador);
            case 3 -> eliminarUsuario(s, administrador);
            case 4 -> System.out.println(Colores.VERDE + administrador.listarUsuarios() + Colores.RESET);
            case 5 -> leerUsuario(s, administrador);
            default -> System.out.println(Colores.ROJO + "Opción inválida" + Colores.RESET);
        }
    }

    private static void mostrarMenuUsuarios() {
        System.out.println(Colores.CYAN + "--- Usuarios ---" + Colores.RESET);
        System.out.println("1- Crear Usuario");
        System.out.println("2- Modificar Usuario");
        System.out.println("3- Eliminar Usuario");
        System.out.println("4- Listar Usuarios");
        System.out.println("5- Buscar Usuario");
        System.out.println("Seleccione una opción:");
    }

    // ============================================================
    // CREAR ADMINISTRADOR
    // ============================================================

    private static void crearAdministrador(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Crear Administrador seleccionado" + Colores.RESET);
        Usuario newAdmin = crearUsuarioDesdeInput(s, Rol.ADMINISTRADOR);
        administrador.crearUsuario(newAdmin);
    }

    // ============================================================
    // ABM USUARIO
    // ============================================================

    public static void crearUsuario(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Crear Usuario seleccionado" + Colores.RESET);
        Usuario newUser = crearUsuarioDesdeInput(s, Rol.BASE);
        administrador.crearUsuario(newUser);
    }

    private static Usuario crearUsuarioDesdeInput(Scanner s, Rol rol) {
        System.out.println("Name: ");
        String nombre = s.nextLine();
        System.out.println("Email: ");
        String email = s.nextLine();
        System.out.println("Password: ");
        String password = s.nextLine();

        return (rol == Rol.ADMINISTRADOR)
                ? new Administrador(nombre, password, email, rol)
                : new Usuario(nombre, password, email, rol);
    }

    public static void modificarUsuario(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Modificar Usuario seleccionado" + Colores.RESET);
        System.out.println("Ingrese el id del usuario a modificar:");
        int id = leerEntero(s);

        Usuario usuarioModificado = crearUsuarioDesdeInput(s, Rol.BASE);
        usuarioModificado.setId(id);

        administrador.actualizarUsuario(usuarioModificado);
    }

    public static void eliminarUsuario(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Eliminar Usuario seleccionado" + Colores.RESET);
        System.out.println("Ingrese el id del usuario a eliminar:");
        int id = leerEntero(s);
        administrador.eliminarUsuario(id);
    }

    public static void leerUsuario(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Buscar Usuario seleccionado" + Colores.RESET);
        System.out.println("Ingrese el id del usuario:");
        int id = leerEntero(s);
        System.out.println(administrador.leerUsuario(id));
    }

    // ============================================================
    // ABM CONTENIDO
    // ============================================================

    public static void crearContenido(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Crear Contenido seleccionado" + Colores.RESET);
        System.out.println("1- Crear Película");
        System.out.println("2- Crear Serie");

        int opcion = leerEntero(s);

        System.out.println("Título:");
        String titulo = s.nextLine();

        System.out.println("Año:");
        int anio = leerEntero(s);

        System.out.println("Director:");
        String director = s.nextLine();

        Genero genero = pedirGenero(s);

        switch (opcion) {
            case 1 -> {
                double duracion = pedirDuracion(s);
                Pelicula nuevaPelicula = new Pelicula(titulo, genero, anio, director, duracion);
                boolean isGuardado = administrador.crearContenido(nuevaPelicula);
                if (isGuardado) {
                    System.out.println(Colores.VERDE + "Película creada exitosamente." + Colores.RESET);
                    System.out.println(nuevaPelicula);
                } else {
                    System.out.println(Colores.ROJO + "Error al crear la película." + Colores.RESET);
                }
            }
            case 2 -> {
                System.out.println("Ingrese temporadas:");
                int temporadas = pedirAtributosSerie(s);
                System.out.println("Ingrese episodios:");
                int episodios = pedirAtributosSerie(s);
                Serie nuevaSerie = new Serie(titulo, genero, anio, director, temporadas, episodios);
                boolean isGuardado = administrador.crearContenido(nuevaSerie);
                if (isGuardado) {
                    System.out.println(Colores.VERDE + "Serie creada exitosamente." + Colores.RESET);
                    System.out.println(nuevaSerie);
                } else {
                    System.out.println(Colores.ROJO + "Error al crear la serie." + Colores.RESET);
                }
            }
            default -> System.out.println(Colores.ROJO + "Opción inválida" + Colores.RESET);
        }
    }

    public static double pedirDuracion(Scanner s) {
        System.out.println("Duración:");
        double duracion = s.nextDouble();
        s.nextLine();
        return duracion;
    }

    public static Genero pedirGenero(Scanner s) {
        Genero[] generos = Genero.values();

        while (true) {
            System.out.println("Seleccione un género:");
            for (int i = 0; i < generos.length; i++) {
                System.out.println((i + 1) + ") " + generos[i]);
            }

            try {
                int opcion = Integer.parseInt(s.nextLine());
                if (opcion >= 1 && opcion <= generos.length) {
                    return generos[opcion - 1];
                }
            } catch (NumberFormatException ignored) {}

            System.out.println(Colores.ROJO + "Opción inválida. Intente nuevamente." + Colores.RESET);
        }
    }

    public static int pedirAtributosSerie(Scanner s) {
        int atributo = s.nextInt();
        s.nextLine();
        return atributo;
    }

    public static void modificarContenido(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Modificar Contenido seleccionado" + Colores.RESET);
        System.out.println(administrador.listarContenidos());
        System.out.println("Ingrese el id del contenido a modificar:");

        int id = leerEntero(s);

        System.out.println("Título:");
        String titulo = s.nextLine();

        System.out.println("Año:");
        int anio = leerEntero(s);

        System.out.println("Director:");
        String director = s.nextLine();

        Genero genero = pedirGenero(s);

        Contenido existente = administrador.leerContenido(id);

        if (existente instanceof Pelicula) {
            double duracion = pedirDuracion(s);
            Pelicula nueva = new Pelicula(titulo, genero, anio, director, duracion);
            nueva.setId(id);
            administrador.actualizarContenido(nueva);

        } else if (existente instanceof Serie) {
            int temporadas = pedirAtributosSerie(s);
            int episodios = pedirAtributosSerie(s);
            Serie nueva = new Serie(titulo, genero, anio, director, temporadas, episodios);
            nueva.setId(id);
            administrador.actualizarContenido(nueva);

        } else {
            System.out.println(Colores.ROJO + "Tipo de contenido no reconocido." + Colores.RESET);
        }
    }


    // ============================================================
    // MÉTODOS AUXILIARES CONTENIDO
    // ============================================================

    public static void eliminarContenido(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Eliminar Contenido seleccionado" + Colores.RESET);
        System.out.println("Ingrese el id del contenido:");
        int id = leerEntero(s);
        administrador.eliminarContenido(id);
    }

    public static void buscarContenido(Scanner s, Administrador administrador) {
        System.out.println(Colores.MAGENTA + "Buscar Contenido seleccionado" + Colores.RESET);
        System.out.println("Ingrese el id:");
        int id = leerEntero(s);
        System.out.println(administrador.leerContenido(id));
    }
}
