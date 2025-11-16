package com.cinema.interfaces;

import java.util.Collection;

public interface ABMCL<T> {
    boolean alta(T c);
    boolean baja(int id);
    boolean modificar(T c);
    T consulta(int id);
    Collection<T> listar();
}
