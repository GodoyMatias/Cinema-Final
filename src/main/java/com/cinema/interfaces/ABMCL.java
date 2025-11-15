package com.cinema.interfaces;

import java.util.Collection;
import java.util.List;

public interface ABMCL<T> {
    boolean crear(T c);
    T leer(int id);
    boolean actualizar(T c);
    boolean eliminar(int id);
    Collection<T> listar();
}
