package com.cinema.service;

import com.cinema.data.GestorUsuariosJson;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.usuarios.Usuario;

import java.util.Set;

public class UsuarioService implements ABMCL<Usuario> {

    private final Set<Usuario> usuarios;
    private final GestorUsuariosJson gestorUsuariosJson = new GestorUsuariosJson();

    public UsuarioService() {
        this.usuarios = gestorUsuariosJson.archivoALista();
    }

    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public boolean crear(Usuario usuario) {
        GestorUsuariosJson gestor = new GestorUsuariosJson();
        boolean registrado = gestor.registrar(usuario);

        if (registrado) {
            System.out.println("Usuario creado: " + usuario.getNombre());
        } else {
            System.out.println("Error al crear usuario: " + usuario.getNombre());
        }

        return registrado;
    }

    // ============================================================
    // LEER
    // ============================================================

    @Override
    public Usuario leer(int id) {
        System.out.println("Leer usuario con ID: " + id);
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================

    @Override
    public boolean actualizar(Usuario usuarioActualizado) {
        Usuario usuarioExistente = buscarPorId(usuarioActualizado.getId());

        if (usuarioExistente == null) {
            return false;
        }

        usuarios.remove(usuarioExistente);
        usuarios.add(usuarioActualizado);

        gestorUsuariosJson.listaToArchivo(usuarios);
        System.out.println("Usuario actualizado: " + usuarioActualizado.getNombre());

        return true;
    }

    // ============================================================
    // ELIMINAR (lógico, no físico)
    // ============================================================

    @Override
    public boolean eliminar(int id) {
        Usuario usuario = buscarPorId(id);

        if (usuario == null) {
            return false;
        }

        usuario.setEstado(false);
        gestorUsuariosJson.listaToArchivo(usuarios);

        System.out.println("Usuario eliminado con ID: " + id);
        return true;
    }

    // ============================================================
    // LISTAR
    // ============================================================

    @Override
    public Set<Usuario> listar() {
        return usuarios;
    }

    // ============================================================
    // MÉTODO AUXILIAR
    // ============================================================

    private Usuario buscarPorId(int id) {
        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
