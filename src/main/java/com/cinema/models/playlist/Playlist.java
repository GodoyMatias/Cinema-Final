package com.cinema.models.playlist;

import com.cinema.models.contenido.Contenido;

import java.util.*;

public class Playlist {

    private String id;
    private String nombre;
    private Map<String, Contenido> contenidos;
    private boolean estado;

    public Playlist(String nombre) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.contenidos = new HashMap<>();
        this.estado = true;
    }

    public Playlist() {
        this.id = UUID.randomUUID().toString();
        this.contenidos = new HashMap<>();
        this.estado = true;
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

    public Map<String, Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(Map<String, Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    @Override
    public String toString() {
        return "Playlist{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contenidos=" + contenidos.size() +
                ", estado=" + estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return estado == playlist.estado && Objects.equals(id, playlist.id) && Objects.equals(nombre, playlist.nombre) && Objects.equals(contenidos, playlist.contenidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, contenidos, estado);
    }
}

