package com.cinema.models.contenido;

import java.util.*;

public abstract class Contenido {
    protected String id;
    protected String titulo;
    protected Genero genero;
    protected int anio;
    protected String director;
    protected boolean estado;
    protected List<Resenia> resenias;
    protected Tipo tipo;

    public Contenido() {
        this.id = UUID.randomUUID().toString();
        this.estado = true;
    }

    public Contenido(String titulo, Genero genero, int anio, String director) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.director = director;
        this.estado = true;
        resenias = new LinkedList<>();
    }

    // setters y getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Resenia> getResenias() {
        return resenias;
    }

    public void setResenias(List<Resenia> resenias) {
        this.resenias = resenias;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", anio=" + anio +
                ", director='" + director + '\'' +
                ", estado=" + estado +
                ", resenias=" + resenias +
                ", tipo=" + tipo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contenido contenido = (Contenido) o;
        return Objects.equals(id, contenido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
