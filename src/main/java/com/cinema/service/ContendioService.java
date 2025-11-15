package com.cinema.service;

import com.cinema.data.GestorContenidosJSON;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.contenido.Contenido;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ContendioService implements ABMCL<Contenido> {
    private Map<Integer, Contenido> contenidos;
    private GestorContenidosJSON gestorContenidosJSON = new GestorContenidosJSON();
    public ContendioService() {
        contenidos = gestorContenidosJSON.archivoALista();
    }

    @Override
    public boolean crear(Contenido c) {
        return false;
    }

    @Override
    public Contenido leer(int id) {
        return null;
    }

    @Override
    public boolean actualizar(Contenido c) {
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public Collection<Contenido> listar() {
        return List.of();
    }
}
