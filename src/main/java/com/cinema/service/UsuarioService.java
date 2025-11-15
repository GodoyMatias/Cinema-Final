package com.cinema.service;

import com.cinema.interfaces.ABMCL;
import com.cinema.models.usuarios.Usuario;

import java.util.List;

public class UsuarioService implements ABMCL<Usuario> {

    @Override
    public void crear(Usuario c) {
        System.out.println("Usuario creado: " + c.getNombre());
    }

    @Override
    public Usuario leer(int id) {
        System.out.println("Leer usuario con ID: " + id);
        return null;
    }

    @Override
    public void actualizar(Usuario c) {
        System.out.println("Usuario actualizado: " + c.getNombre());
    }

    @Override
    public void eliminar(int id) {
        System.out.println("Usuario eliminado con ID: " + id);

    }

    @Override
    public List<Usuario> listar() {
        System.out.println("Listar usuarios");
        return List.of();
    }
}
