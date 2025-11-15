package com.cinema.service;

import com.cinema.data.GestorUsuariosJson;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.usuarios.Usuario;

import java.util.Set;

public class UsuarioService implements ABMCL<Usuario> {
    private Set<Usuario> usuarios;
    private GestorUsuariosJson gestorUsuariosJson = new GestorUsuariosJson();
    public UsuarioService() {
        usuarios = gestorUsuariosJson.archivoALista();
    }

    @Override
    public boolean crear(Usuario c) {
        GestorUsuariosJson g = new GestorUsuariosJson();
        boolean registrado = g.registrar(c);
        if(registrado){
            System.out.println("Usuario creado: " + c.getNombre());
            return true;
        } else {
            System.out.println("Error al crear usuario: " + c.getNombre());
            return false;
        }
    }

    @Override
    public Usuario leer(int id) {
        System.out.println("Leer usuario con ID: " + id);
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(Usuario c) { // a verificar
        for (Usuario u : usuarios) {
            if (u.getId() == c.getId()) {
                usuarios.remove(u);
                usuarios.add(c);
                gestorUsuariosJson.listaToArchivo(usuarios);
                System.out.println("Usuario actualizado: " + c.getNombre());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                u.setEstado(false);
                gestorUsuariosJson.listaToArchivo(usuarios);
                System.out.println("Usuario eliminado con ID: " + id);
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Usuario> listar() {
        return usuarios;
    }
}
