package com.cinema.service;

import com.cinema.controllers.RegisterController;
import com.cinema.data.GestorUsuariosJson;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.usuarios.Usuario;

import java.util.HashSet;
import java.util.Set;

public class UsuarioService implements ABMCL<Usuario> {

    private HashSet<Usuario> usuarios;
    private final GestorUsuariosJson gestoraUsuariosJson = new GestorUsuariosJson();

    public UsuarioService() {
        this.usuarios = gestoraUsuariosJson.archivoALista();
    }

    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public boolean alta(Usuario usuario) {
        boolean registrado = RegisterController.registrar(usuario, gestoraUsuariosJson);

        if (registrado) {
            System.out.println("Usuario creado: " + usuario.getNombre());
        } else {
            System.out.println("Error al alta usuario: " + usuario.getNombre());
        }

        return registrado;
    }

    // ============================================================
    // LEER
    // ============================================================

    @Override
    public Usuario consulta(String id) {
        usuarios = gestoraUsuariosJson.archivoALista();
        System.out.println("Leer usuario con ID: " + id);
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================

    @Override
    public boolean modificar(Usuario usuarioActualizado) {
        usuarios = gestoraUsuariosJson.archivoALista();
        Usuario usuarioExistente = buscarPorId(usuarioActualizado.getId());

        if (usuarioExistente == null) {
            return false;
        }

        usuarios.remove(usuarioExistente);
        usuarios.add(usuarioActualizado);

        gestoraUsuariosJson.listaToArchivo(usuarios);
        System.out.println("Usuario actualizado: " + usuarioActualizado.getNombre());

        return true;
    }

    // ============================================================
    // ELIMINAR (lógico, no físico)
    // ============================================================

    @Override
    public boolean baja(String id) {
        usuarios = gestoraUsuariosJson.archivoALista();
        Usuario usuario = buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        usuario.setEstado(false);
        gestoraUsuariosJson.listaToArchivo(usuarios);

        System.out.println("Usuario eliminado con ID: " + id);
        return true;
    }

    // ============================================================
    // LISTAR
    // ============================================================

    @Override
    public Set<Usuario> listar() {
        usuarios = gestoraUsuariosJson.archivoALista();
        return usuarios;
    }

    // ============================================================
    // MÉTODO AUXILIAR
    // ============================================================

    private Usuario buscarPorId(String id) {
        usuarios = gestoraUsuariosJson.archivoALista();
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public HashSet<Usuario> actualizarUsuarios() {
        return gestoraUsuariosJson.archivoALista();
    }
}