package com.cinema.service;

import com.cinema.data.GestorUsuariosJson;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.playlist.Playlist;
import com.cinema.models.usuarios.Usuario;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PlaylistService implements ABMCL<Playlist> {
    private final GestorUsuariosJson gestorUsuariosJson;


    @Override
    public boolean alta(Playlist c) {
        return false;
    }

    @Override
    public boolean baja(String id) {
        return false;
    }

    @Override
    public boolean modificar(Playlist c) {
        return false;
    }

    @Override
    public Playlist consulta(String id) {
        return null;
    }

    @Override
    public Collection<Playlist> listar() {
        return List.of();
    }
}
