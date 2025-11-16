package com.cinema.service;

import com.cinema.data.GestorUsuariosJson;
import com.cinema.exceptions.EmailNoValidoException;
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

    public void verificarEmail(String email) throws EmailNoValidoException {
        // verificar que el email no exista en la lista de usuarios
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail() != null && usuario.getEmail().equalsIgnoreCase(email)) {
                throw  new EmailNoValidoException("El email ya existe: " + email);
            }
        }

        // verificar que el email tenga un formato válido (básico)
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new EmailNoValidoException("El formato del email es inválido: " + email);
        }
    }

    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public boolean alta(Usuario usuario) {
        HashSet<Usuario> lista = gestoraUsuariosJson.archivoALista();
        lista.add(usuario);
        gestoraUsuariosJson.listaToArchivo(lista);
        return true;
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

        usuarioExistente.setNombre(usuarioActualizado.getNombre());
        usuarioExistente.setPassword(usuarioActualizado.getPassword());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setRol(usuarioActualizado.getRol());
        usuarioExistente.setEstado(usuarioActualizado.getEstado());

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

}