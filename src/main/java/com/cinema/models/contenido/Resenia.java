package com.cinema.models.contenido;

import java.util.Objects;

public class Resenia {
    //Atributos
    private static int contador = 0;
    private int id;
    private int idUsuario;
    private int idContenido;
    private int estrellas;
    private StringBuilder comentario;
    private boolean estado;

    // Constructor vacío
    public Resenia() {
        contador++;
        this.id = contador;
        this.estado = true;
    }

    // Constructor con comentario
    public Resenia(int idUsuario,int idContenido, int estrellas, StringBuilder comentario) {
        contador ++;
        this.id = contador;
        this.idUsuario = idUsuario;
        this.idContenido = idContenido;
        this.estrellas = estrellas;
        this.comentario = comentario;
        this.estado = true;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Resenia.contador = contador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdContenido() { //Agregue get y set idContenido
        return idContenido;
    }

    public void setIdContenido(int idContenido) {
        this.idContenido = idContenido;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public StringBuilder getComentario() {
        return comentario;
    }

    public void setComentario(StringBuilder comentario) {
        this.comentario = comentario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resenia resenia = (Resenia) o;
        return id == resenia.id && idUsuario == resenia.idUsuario && idContenido == resenia.idContenido && estrellas == resenia.estrellas && estado == resenia.estado && Objects.equals(comentario, resenia.comentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idContenido, estrellas, comentario, estado);
    }

    @Override
    public String toString() {
        return "Resenia{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idContenido=" + idContenido +
                ", estrellas=" + estrellas +
                ", comentario=" + comentario +
                ", estado=" + estado +
                '}';
    }
}
