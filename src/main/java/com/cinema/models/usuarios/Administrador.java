package com.cinema.models.usuarios;

import com.cinema.interfaces.ABMCL;
import com.cinema.service.UsuarioService;

import java.util.List;

public class Administrador extends Usuario  {
    UsuarioService usuarioService = new UsuarioService();
    public Administrador() {
    }

    public Administrador(String nombre, String password, String email, Rol rol) {
        super(nombre, password, email, rol);
    }

    public boolean crearUsuario(Usuario u){
        if(u != null){
            usuarioService.crear(u);
            return true;
        }
        return false;
    }
    public Usuario leerUsuario(int id){
        return usuarioService.leer(id);
    }
    public boolean actualizarUsuario(Usuario u){
        if (u != null){
            usuarioService.actualizar(u);
            return true;
        }
        return false;
    }
    public boolean eliminarUsuario(int id){
        usuarioService.eliminar(id);
        return usuarioService.leer(id).getEstado() == false;
    }
    public List<Usuario> listarUsuarios() {
        return usuarioService.listar();
    }
}