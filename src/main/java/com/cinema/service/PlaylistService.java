package com.cinema.service;

import com.cinema.data.GestorUsuarioJSON;
import com.cinema.exceptions.ContenidoNoEncontradoException;
import com.cinema.exceptions.PlaylistNoEncontradaException;
import com.cinema.exceptions.UsuarioNoEncontradoException;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.playlist.Playlist;
import com.cinema.models.usuarios.Usuario;
import com.cinema.utils.Colores;

import java.util.*;

public class PlaylistService implements ABMCL<Playlist> {

    private final HashSet<Usuario> usuarios;
    private final HashSet<Playlist> playlists;
    private final GestorUsuarioJSON gestorUsuarioJSON = new GestorUsuarioJSON();

    private Usuario usuario; // usuario dueño de las playlists

    // ============================================================
    // CONSTRUCTORES
    // ============================================================

    public PlaylistService() {
        this.usuarios = gestorUsuarioJSON.archivoALista();
        this.playlists = new HashSet<>();
    }

    public PlaylistService(String idUsuario) throws UsuarioNoEncontradoException {
        this.usuarios = gestorUsuarioJSON.archivoALista();
        this.usuario = buscarUsuarioPorId(idUsuario);

        if (this.usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario con ID " + idUsuario + " no encontrado.");
        }

        this.playlists = usuario.getPlaylists() != null
                ? usuario.getPlaylists()
                : new HashSet<>();
    }

    // ============================================================
    // PERSISTENCIA
    // ============================================================

    private void persistencia() {
        usuario.setPlaylists(playlists);
        gestorUsuarioJSON.listaToArchivo(usuarios);
    }

    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public boolean alta(Playlist playlist) {
        boolean added = playlists.add(playlist);
        if (added) persistencia();
        return added;
    }

    // ============================================================
    // LEER
    // ============================================================

    @Override
    public Playlist consulta(String id) {
        try{
            return playlists.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    // ============================================================
    // ACTUALIZAR
    // ============================================================

    @Override
    public boolean modificar(Playlist nueva) {
        for (Playlist existente : playlists) {
            if (existente.getId().equals(nueva.getId())) {

                existente.setNombre(nueva.getNombre());
                existente.setContenidos(nueva.getContenidos());
                existente.setEstado(nueva.isEstado());

                persistencia();
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // ELIMINAR (borrado lógico)
    // ============================================================

    @Override
    public boolean baja(String id) {
        for (Playlist p : playlists) {
            if (p.getId().equals(id)) {
                p.setEstado(false);
                persistencia();
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // LISTAR
    // ============================================================

    @Override
    public Collection<Playlist> listar() {
        return playlists;
    }

    // ============================================================
    // AUXILIARES
    // ============================================================

    private Usuario buscarUsuarioPorId(String idUsuario) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(idUsuario)) {
                return u;
            }
        }
        return null;
    }

    public void validarPlaylistActivas(Usuario usuario) throws PlaylistNoEncontradaException {

        Collection<Playlist> playlistsUsuario = usuario.getPlaylists();
        if (playlistsUsuario == null || playlistsUsuario.isEmpty() || playlistsUsuario.stream().noneMatch(Playlist::isEstado)) {
            throw new PlaylistNoEncontradaException("No tienes playlists activas. No hay nada que mostrar.");
        }
    }


    public void validarExistenciaPlaylist(String idPlaylist, Usuario usuario) throws PlaylistNoEncontradaException {
        HashSet<Playlist> playlists = usuario.getPlaylists();
        boolean existe = playlists.stream()
                .anyMatch(p -> p.getId().equals(idPlaylist) && p.isEstado());

        if (!existe) {
            throw new PlaylistNoEncontradaException("La playlist con ID " + idPlaylist + " no existe o está desactivada.");
        }
    }

    public boolean validarContenidoEnPlaylist(Playlist playlist, Contenido contenido) {
        try {
            return playlist.getContenidos().containsKey(contenido.getId());
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return false;
    }

}
