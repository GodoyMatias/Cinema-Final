package com.cinema.models.usuarios;
import com.cinema.exceptions.ContenidoNoEncontradoException;
import com.cinema.models.contenido.Contenido;
import com.cinema.service.ContendioService;
import com.cinema.service.UsuarioService;

import java.util.Collection;
import java.util.Set;

public class Administrador extends Usuario  {
    UsuarioService usuarioService = new UsuarioService();
    ContendioService contendioService = new ContendioService();
    public Administrador() {
    }

    public Administrador(String nombre, String password, String email, Rol rol) {
        super(nombre, password, email, rol);
    }

    public boolean crearUsuario(Usuario u){
        return usuarioService.alta(u);
    }
    public Usuario leerUsuario(String id){
        return usuarioService.consulta(id);
    }
    public boolean actualizarUsuario(Usuario u){
         return usuarioService.modificar(u);
    }
    public boolean eliminarUsuario(String id){
        return usuarioService.baja(id);
    }
    public Set<Usuario> listarUsuarios() {
        return usuarioService.listar();
    }

    public boolean crearContenido(Contenido c){
        return contendioService.alta(c);
    }

    public Contenido leerContenido(String id){
        try {
            return contendioService.consulta(id);
        } catch (ContenidoNoEncontradoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarContenido(Contenido c){
        try {
            return contendioService.modificar(c);
        } catch (ContenidoNoEncontradoException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarContenido(String id){
        try {
            return contendioService.baja(id);
        } catch (ContenidoNoEncontradoException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Collection<Contenido> listarContenidos() {
        return contendioService.listar();
    }
}