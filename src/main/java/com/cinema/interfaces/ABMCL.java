package com.cinema.interfaces;

import java.util.Collection;

public interface ABMCL<T> {
    boolean alta(T c);
    boolean baja(String id);
    boolean modificar(T c);
    T consulta(String id);
    Collection<T> listar();
}
