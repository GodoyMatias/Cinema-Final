package com.cinema.models.contenido;

import java.util.Objects;

public class Serie extends Contenido{
    private int temporadas;
    private int episodios;

    // constructor
    public Serie(String titulo, Genero genero, int anio, String director, int temporadas, int episodios) {
        super(titulo, genero, anio, director);
        this.temporadas = temporadas;
        this.episodios = episodios;
    }

    public Serie(){
        super();
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }

    public int getEpisodios() {
        return episodios;
    }

    public void setEpisodios(int episodios) {
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "temporadas=" + temporadas +
                ", episodios=" + episodios +
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
        Serie serie = (Serie) o;
        return temporadas == serie.temporadas && episodios == serie.episodios;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), temporadas, episodios);
    }
}
