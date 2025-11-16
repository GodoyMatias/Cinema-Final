package com.cinema.models.contenido;

import com.cinema.utils.Colores;

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
        return  "    id=  " + id + '\n' +
                "    tipo= " + tipo +
                "    titulo= " + titulo + '\'' +
                "    genero= " + genero +
                "    director= " + director + '\'' +
                "    duracion= " + duracion +
                "    anio= " + anio +
                Colores.AZUL +  "    estado= " + estado + Colores.RESET + '\n' +
                "    resenias= " + resenias + '\n' +
                "--------------------------------" + '\n';
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
