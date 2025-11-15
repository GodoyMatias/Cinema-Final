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

    public boolean crearContenido(Contenido c){
        return contendioService.crear(c);
    }

    public Contenido leerContenido(int id){
        try {
            return contendioService.leer(id);
        } catch (ContenidoNoEncontradoException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarContenido(Contenido c){
        try {
            return contendioService.actualizar(c);
        } catch (ContenidoNoEncontradoException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarContenido(int id){
        try {
            return contendioService.eliminar(id);
        } catch (ContenidoNoEncontradoException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Collection<Contenido> listarContenidos() {
        return contendioService.listar();
    }
}