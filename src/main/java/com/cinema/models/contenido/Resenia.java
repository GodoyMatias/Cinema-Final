package com.cinema.models.contenido;

import com.cinema.utils.Colores;

import java.util.Objects;
import java.util.UUID;

public class Resenia {
    //Atributos
    private String id;
    private String idUsuario;
    private String idContenido;
    private int estrellas;
    private StringBuilder comentario;
    private boolean estado;

    // Constructor vacío

    public Resenia(boolean estado) {
        this.estado = estado;
    }

    public Resenia() {
        this.id = UUID.randomUUID().toString();
        this.estado = true;
    }

    // Constructor con comentario
    public Resenia(String idUsuario,String idContenido, int estrellas, StringBuilder comentario) {
        this.id = UUID.randomUUID().toString();
        this.idUsuario = idUsuario;
        this.idContenido = idContenido;
        this.estrellas = estrellas;
        this.comentario = comentario;
        this.estado = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdContenido() { //Agregue get y set idContenido
        return idContenido;
    }

    public void setIdContenido(String idContenido) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resenia resenia = (Resenia) o;
        return Objects.equals(id, resenia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return
                "--------------------------------" + '\n' +
                        "    id= " + id + "\n" +
                        "    Usuario=" + idUsuario +
                        "    estrellas=" + estrellas +
                        "    comentario=" + comentario +
                        "    estado=" + (estado ? Colores.VERDE + true + Colores.RESET : Colores.ROJO + false + Colores.RESET) +
                        "--------------------------------" + '\n';
    }
}
