package com.cinema.models.contenido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Contenido {
    private static int contador = 0;
    private int id;
    protected String titulo;
    protected Genero genero;
    protected int anio;
    protected String director;
    protected boolean estado;
    protected List<Resenia> resenias;
    protected Tipo tipo;

    public Contenido() {
        contador++;
        this.id = contador;
        this.estado = true;
    }

    public Contenido(String titulo, Genero genero, int anio, String director) {
        contador++;
        this.id = contador;
        this.titulo = titulo;
        this.genero = genero;
        this.anio = anio;
        this.director = director;
        this.estado = true;
        resenias = new LinkedList<>();
    }

    // setters y getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Contenido.contador = contador;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    // metodos
    public void agregarResenia(Resenia resenia) {
        this.resenias.add(resenia);
    }

    public double promedioResenas() {
        if (resenias.isEmpty()) return 0;
        double total = 0;
        for (Resenia c : resenias) {
            total += c.getEstrellas();
        }
        return total / resenias.size();
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
        if (o == null || getClass() != o.getClass()) return false;
        Contenido contenido = (Contenido) o;
        return id == contenido.id && anio == contenido.anio && estado == contenido.estado && Objects.equals(titulo, contenido.titulo) && genero == contenido.genero && Objects.equals(director, contenido.director) && Objects.equals(resenias, contenido.resenias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, genero, anio, director, estado, resenias);
    }
}
