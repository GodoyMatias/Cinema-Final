package com.cinema.models.usuarios;
import com.cinema.service.UsuarioService;

import java.util.Set;

public class Administrador extends Usuario  {
    UsuarioService usuarioService = new UsuarioService();
    public Administrador() {
    }

    public Administrador(String nombre, String password, String email, Rol rol) {
        super(nombre, password, email, rol);
    }

    public boolean crearUsuario(Usuario u){
        return usuarioService.crear(u);
    }
    public Usuario leerUsuario(int id){
        return usuarioService.leer(id);
    }
    public boolean actualizarUsuario(Usuario u){
         return usuarioService.actualizar(u);
    }
    public boolean eliminarUsuario(int id){
        return usuarioService.eliminar(id);
    }
    public Set<Usuario> listarUsuarios() {
        return usuarioService.listar();
    }
}