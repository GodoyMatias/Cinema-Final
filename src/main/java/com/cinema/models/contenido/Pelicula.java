package com.cinema.models.contenido;

import java.util.Objects;

public class Pelicula extends Contenido{
    //Atributos
    private double duracion; // en horas

    //construtor
    public Pelicula(String titulo, Genero genero, int anio, String director, double duracion) {
        super(titulo, genero, anio, director);
        this.duracion = duracion;
    }

    public Pelicula(){
        super();
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "duracion=" + duracion +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", anio=" + anio +
                ", director='" + director + '\'' +
                ", estado=" + estado +
                ", resenias=" + resenias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pelicula pelicula = (Pelicula) o;
        return Double.compare(duracion, pelicula.duracion) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duracion);
    }
}
