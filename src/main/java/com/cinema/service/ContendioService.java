package com.cinema.service;

import com.cinema.data.GestorContenidosJSON;
import com.cinema.exceptions.ContenidoNoEncontradoException;
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
        if (!contenidos.containsKey(c.getId())){
            contenidos.put(c.getId(), c);
            gestorContenidosJSON.listaToArchivo(contenidos);
            return true;
        }
        return false;
    }

    @Override
    public Contenido leer(int id) throws ContenidoNoEncontradoException {
        if(!contenidos.containsKey(id)){
            throw new ContenidoNoEncontradoException("El contenido con ID " + id + " no existe.");
        }
        return contenidos.get(id);
    }

    @Override
    public boolean actualizar(Contenido c) throws ContenidoNoEncontradoException {
        if(!contenidos.containsKey(c.getId())){
            throw new ContenidoNoEncontradoException("El contenido no existe.");
        }
        contenidos.put(c.getId(), c);
        gestorContenidosJSON.listaToArchivo(contenidos);
        return true;
    }

    @Override
    public boolean eliminar(int id) throws ContenidoNoEncontradoException {
        if(!contenidos.containsKey(id)){
            throw new ContenidoNoEncontradoException("El contenido con ID " + id + " no existe.");
        }
        Contenido contenido = contenidos.get(id);
        contenido.setEstado(false);
        contenidos.put(id, contenido);
        gestorContenidosJSON.listaToArchivo(contenidos);
        return false;
    }

    @Override
    public Collection<Contenido> listar() {
        return contenidos.values();
    }
}
