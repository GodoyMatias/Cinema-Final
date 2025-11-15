package com.cinema.interfaces;

import java.util.List;

public interface ABMCL<T> {
    void crear(T c);
    T leer(int id);
    void actualizar(T c);
    void eliminar(int id);
    List<T> listar();
}
