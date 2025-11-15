package com.cinema.models.usuarios;

public class Usuario {
    private static int contador = 0; ////////////////////////////////////////////////
    private int id;
    private String nombre;
    private Boolean estado;
    private String password;
    private String email;
    private Rol rol;

    public Usuario() {
        contador ++;
        this.id = contador;
        this.estado = true; //activo por defecto
    }

    public Usuario(String nombre, String password, String email, Rol rol) {
        this.id = ++contador;
        this.nombre = nombre;
        this.estado = true;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Usuario.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "{" + '\n' +
                " id=" + id + '\n' +
                " nombre='" + nombre + '\n' +
                " estado=" + estado + '\n' +
                " password='" + password + '\n' +
                " email='" + email + '\n' +
                " rol=" + rol + '\n' +
                '}' + '\n';
    }
}
