package com.cinema.models.contenido;

import java.util.Objects;

public class Pelicula extends Contenido{
    //Atributos
    private double duracion; // en horas

    //construtor
    public Pelicula(String titulo, Genero genero, int anio, String director, double duracion) {
        super(titulo, genero, anio, director);
        this.duracion = duracion;
        this.tipo = Tipo.PELICULA;
    }



    public Pelicula(){
        super();
        this.tipo = Tipo.PELICULA;
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
                "id=" + id + '\n' +
                "duracion=" + duracion +
                " titulo='" + titulo + '\'' +
                " genero=" + genero +
                " anio=" + anio +
                " director='" + director + '\'' +
                " estado=" + estado + '\n'+
                " tipo=" + tipo +
                " resenias=" + resenias +
                '}'+ '\n' +'\n';
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
