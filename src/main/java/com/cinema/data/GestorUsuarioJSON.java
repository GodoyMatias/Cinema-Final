package com.cinema.data;

import com.cinema.models.usuarios.Rol;
import com.cinema.persistence.OperacionesLectoEscritura;
import com.cinema.models.usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class GestorUsuarioJSON {

    private static final String DEFAULT_FILE = "usuarios.json";

    // Instancia del gestor de playlists para (de)serializar listas de playlists dentro del Usuario
    private static GestorPlaylistJSON gestorPlaylistJSON = new GestorPlaylistJSON();

    // Método genérico para aceptar cualquier colección de usuarios
    public <C extends Collection<Usuario>> void listaToArchivo(C lista) {
        ArrayList<Usuario> arrayListUsuarios = new ArrayList<>(lista);
        OperacionesLectoEscritura.grabar(DEFAULT_FILE, serializarLista(arrayListUsuarios));
    }

    public JSONObject serializar(Usuario u) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", u.getId());
            jsonObject.put("nombre", u.getNombre());
            jsonObject.put("email", u.getEmail());
            jsonObject.put("contrasena", u.getPassword());
            jsonObject.put("estado", u.getEstado());
            // serializar el rol como nombre (String) para que Rol.valueOf funcione al deserializar
            jsonObject.put("rol", u.getRol() != null ? u.getRol().name() : JSONObject.NULL);
            // Serializar playlists del usuario (si existen)
            jsonObject.put("playlists", u.getPlaylists() != null ? gestorPlaylistJSON.serializarLista(u.getPlaylists()) : JSONObject.NULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public JSONArray serializarLista(ArrayList<Usuario> lista) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray();
            for (Usuario u: lista) {
                jsonArray.put(serializar(u)); // agrega el JSONObject al JSONArray
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    // DESERIALIZAR

    public HashSet<Usuario> archivoALista() {
        JSONTokener tokener = OperacionesLectoEscritura.leer(DEFAULT_FILE);
        HashSet<Usuario> lista = new HashSet<>();
        try {
            if (tokener == null) return lista; // archivo no existe -> lista vacía
            lista = deserializarLista(new JSONArray(tokener));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Usuario deserializar (JSONObject jsonObject) {
        Usuario u = new Usuario();
        try {
            if (jsonObject.has("id") && !jsonObject.isNull("id")) u.setId(jsonObject.getString("id"));
            if (jsonObject.has("nombre") && !jsonObject.isNull("nombre")) u.setNombre(jsonObject.getString("nombre"));
            if (jsonObject.has("email") && !jsonObject.isNull("email")) u.setEmail(jsonObject.getString("email"));
            if (jsonObject.has("contrasena") && !jsonObject.isNull("contrasena")) u.setPassword(jsonObject.getString("contrasena"));
            if (jsonObject.has("estado") && !jsonObject.isNull("estado")) u.setEstado(jsonObject.getBoolean("estado"));
            if (jsonObject.has("rol") && !jsonObject.isNull("rol")) {
                try {
                    u.setRol(Rol.valueOf(jsonObject.getString("rol")));
                } catch (IllegalArgumentException e) {
                    // rol desconocido, dejar null
                }
            }

            // Deserializar playlists si están presentes
            if (jsonObject.has("playlists") && !jsonObject.isNull("playlists")) {
                try {
                    u.setPlaylists(gestorPlaylistJSON.deserializarLista(jsonObject.getJSONArray("playlists")));
                } catch (Exception e) {
                    // si hay un error al deserializar playlists, dejar como vacía
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public HashSet<Usuario> deserializarLista (JSONArray jsonArray) {
        HashSet<Usuario> lista = new HashSet<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Usuario u = deserializar(jsonArray.getJSONObject(i));
                lista.add(u);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
