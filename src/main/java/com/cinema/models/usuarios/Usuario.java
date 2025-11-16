package com.cinema.models.usuarios;

import java.util.Objects;
import java.util.UUID;

public class Usuario {
    private String id;
    private String nombre;
    private Boolean estado;
    private String password;
    private String email;
    private Rol rol;

    public Usuario() {
        this.id = UUID.randomUUID().toString();
        this.estado = true; //activo por defecto
    }

    public Usuario(String nombre, String password, String email, Rol rol) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.estado = true;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
