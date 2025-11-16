package com.cinema.service;

import com.cinema.data.GestorContenidosJSON;
import com.cinema.interfaces.ABMCL;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Resenia;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ReseniaService implements ABMCL<Resenia>{
    private List<Resenia> resenias;
    private Map<Integer, Contenido> contenidos;
    private GestorContenidosJSON gestorContenidosJSON = new GestorContenidosJSON();

    public ReseniaService(int idContenido) {
        this.contenidos = gestorContenidosJSON.archivoALista();
        this.resenias = contenidos.get(idContenido).getResenias();
    }

    public void persistencia(Resenia c){
        Contenido contenido = contenidos.get(c.getIdContenido());
        contenido.setResenias(resenias);
        contenidos.put(contenido.getId(), contenido);
        gestorContenidosJSON.listaToArchivo(contenidos);
    }

    @Override
    public boolean crear(Resenia c) {
        resenias.add(c);
        persistencia(c);
        return true;
    }

    /// este fue gemini, yo no se :)
    public Resenia buscarPorUsuario(int idUsuario) {
        for (Resenia r : resenias) {
            // Asumiendo que tu clase Resenia tiene un getter para el id del usuario
            if (r.getIdUsuario() == idUsuario) {
                return r;
            }
        }
        return null; // No se encontró
    }

    @Override
    public Resenia leer(int id) {
        for (Resenia r : resenias) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

//    @Override
//    public boolean actualizar(Resenia c) {
//        for (Resenia r : resenias) {
//            if (r.getId() == c.getId()) {
//                resenias.remove(r);
//                r.setEstrellas(c.getEstrellas());
//                r.setComentario(c.getComentario());
//                resenias.add(r);
//                persistencia(r);
//                return true;
//            }
//        }
//        return false;
//    }
@Override
public boolean actualizar(Resenia c) {
    // 'c' es el objeto que viene del controlador (reseniaExistente en nuestra solución)
    for (Resenia r : resenias) {
        // 'r' es el objeto que vive en la lista 'resenias'

        // Compara por el ID único de la reseña
        if (r.getId() == c.getId()) {

            // Actualiza el objeto 'r' (en la lista) con los datos de 'c'
            r.setEstrellas(c.getEstrellas());
            r.setComentario(c.getComentario());

            // Llama a persistencia con el objeto 'r' actualizado
            persistencia(r);
            return true; // ¡Éxito!
        }
    }
    return false; // No se encontró el objeto para actualizar
}

    @Override
    public boolean eliminar(int id) {
        for (Resenia r : resenias) {
            if (r.getId() == id) {
                resenias.remove(r);
                r.setEstado(false);
                resenias.add(r);
                persistencia(r);
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Resenia> listar() {
        return resenias;
    }
}
