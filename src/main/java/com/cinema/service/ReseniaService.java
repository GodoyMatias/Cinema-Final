package com.cinema.service;

import com.cinema.data.GestorContenidosJSON;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Resenia;

import java.util.*;

public class ReseniaService implements ABMCL<Resenia> {

    private final List<Resenia> resenias;
    private final Map<String, Contenido> contenidos;
    private final GestorContenidosJSON gestorContenidosJSON = new GestorContenidosJSON();


    public ReseniaService() {
        this.contenidos = gestorContenidosJSON.archivoALista();
        this.resenias = new LinkedList<>();
    }

    public ReseniaService(String idContenido) {
        this.contenidos = gestorContenidosJSON.archivoALista();
        this.resenias = contenidos.get(idContenido).getResenias();
    }

    // ============================================================
    // PERSISTENCIA
    // ============================================================

    private void persistencia(Resenia resenia) {
        Contenido contenido = contenidos.get(resenia.getIdContenido());
        contenido.setResenias(resenias);
        contenidos.put(contenido.getId(), contenido);
        gestorContenidosJSON.listaToArchivo(contenidos);
    }

    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public boolean alta(Resenia resenia) {
        resenias.add(resenia);
        persistencia(resenia);
        return true;
    }

    // ============================================================
    // BUSCAR POR USUARIO
    // ============================================================

    public Resenia buscarPorUsuario(String idUsuario) {
        for (Resenia r : resenias) {
            if (r.getIdUsuario().equals(idUsuario)) {
                if (r.isEstado()){
                    return r;
                }
            }
        }
        return new Resenia(false);
    }

    // ============================================================
    // LEER
    // ============================================================

    @Override
    public Resenia consulta(String id) {
        for (Resenia r : resenias) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================

    @Override
    public boolean modificar(Resenia nueva) {
        for (Resenia existente : resenias) {
            if (Objects.equals(existente.getId(), nueva.getId())) {

                existente.setEstrellas(nueva.getEstrellas());
                existente.setComentario(nueva.getComentario());

                persistencia(existente);
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // ELIMINAR (borrado lógico)
    // ============================================================

    @Override
    public boolean baja(String id) {
        for (Resenia r : resenias) {
            if (r.getId().equals(id)) {
                resenias.remove(r);
                r.setEstado(false);
                resenias.add(r);
                persistencia(r);
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // LISTAR
    // ============================================================

    @Override
    public Collection<Resenia> listar() {
        return resenias;
    }

    // ============================================================
    // AUXILIARES
    // ============================================================

    public void validarEstrellas(int estrellas) throws IllegalArgumentException {
        if (estrellas < 1 || estrellas > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5 estrellas.");
        }
    }
}