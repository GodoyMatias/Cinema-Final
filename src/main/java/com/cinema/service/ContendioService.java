package com.cinema.service;

import com.cinema.data.GestorContenidosJSON;
import com.cinema.exceptions.ContenidoNoEncontradoException;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.contenido.Contenido;

import java.util.Collection;
import java.util.Map;

public class ContendioService implements ABMCL<Contenido> {

    private final Map<String, Contenido> contenidos;
    private final GestorContenidosJSON gestorContenidosJSON = new GestorContenidosJSON();

    public ContendioService() {
        this.contenidos = gestorContenidosJSON.archivoALista();
    }

    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public boolean alta(Contenido contenido) {
        if (contenidos.containsKey(contenido.getId())) {
            return false;
        }

        contenidos.put(contenido.getId(), contenido);
        guardarCambios();

        return true;
    }

    // ============================================================
    // LEER
    // ============================================================

    @Override
    public Contenido consulta(String id) throws ContenidoNoEncontradoException {
        validarExistencia(id);
        return contenidos.get(id);
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================

    @Override
    public boolean modificar(Contenido contenido) throws ContenidoNoEncontradoException {
        validarExistencia(contenido.getId());

        contenidos.put(contenido.getId(), contenido);
        guardarCambios();

        return true;
    }

    // ============================================================
    // ELIMINAR (lógico: cambia estado a false)
    // ============================================================

    @Override
    public boolean baja(String id) throws ContenidoNoEncontradoException {
        validarExistencia(id);

        Contenido contenido = contenidos.get(id);
        contenido.setEstado(false);

        contenidos.put(id, contenido);
        guardarCambios();

        // Se mantiene el "return false" porque así estaba en el código original
        return false;
    }

    // ============================================================
    // LISTAR
    // ============================================================

    @Override
    public Collection<Contenido> listar() {
        /// /TODO: mostrar solo los activos
        return contenidos.values();
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================

    private void validarExistencia(String id) throws ContenidoNoEncontradoException {
        if (!contenidos.containsKey(id)) {
            throw new ContenidoNoEncontradoException("El contenido con ID " + id + " no existe.");
        }
    }

    private void guardarCambios() {
        gestorContenidosJSON.listaToArchivo(contenidos);
    }

    @Override
    public String toString() {
        return "ContendioService{" +
                "contenidos=" + contenidos +
                '}';
    }
}
