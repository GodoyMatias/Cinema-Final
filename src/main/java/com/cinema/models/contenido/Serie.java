package com.cinema.models.contenido;

import com.cinema.utils.Colores;

import java.awt.*;
import java.util.Objects;

public class Serie extends Contenido{
    private int temporadas;
    private int episodios;

    // constructor
    public Serie(String titulo, Genero genero, int anio, String director, int temporadas, int episodios) {
        super(titulo, genero, anio, director);
        this.temporadas = temporadas;
        this.episodios = episodios;
        this.tipo = Tipo.SERIE;
    }

    public Serie(){
        super();
        this.tipo = Tipo.SERIE;
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
        return
                "--------------------------------" + '\n' +
                "    id=  " + id + '\n' +
                "    tipo= " + tipo +
                "    titulo= " + titulo + '\'' +
                "    temporadas= " + temporadas +
                "    episodios= " + episodios +
                "    genero= " + genero +
                "    anio= " + anio +
                "    director= " + director + '\'' +
                Colores.AZUL +  "    estado= " + estado + Colores.RESET + '\n' +
                "    resenias= " + resenias +'\n' +
                "--------------------------------" + '\n';
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
