package com.cinema.data;
import com.cinema.models.playlist.Playlist;
import com.cinema.persistence.OperacionesLectoEscritura;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.HashSet;


public class GestorPlaylistJSON {
    static GestorContenidoJSON gestorContenidoJSON = new GestorContenidoJSON();
    private static final String DEFAULT_FILE_PLAYLIST = "playlists.json";

    public static JSONObject serializar(Playlist playlist) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", playlist.getId());
            jsonObject.put("idUsuario", playlist.getIdUsuario());
            jsonObject.put("nombre", playlist.getNombre());
            jsonObject.put("estado", playlist.isEstado());
            jsonObject.put("contenidos", gestorContenidoJSON.serializarLista(playlist.getContenidos()));
            // Note: contenidos are not serialized here
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarLista(HashSet<Playlist> playlists) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Playlist p : playlists) {
                jsonArray.put(serializar(p));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void listaToArchivo(HashSet<Playlist> playlists) {
        JSONArray jsonArray = serializarLista(playlists);
        OperacionesLectoEscritura.grabar(DEFAULT_FILE_PLAYLIST, jsonArray);
    }

    public Playlist deserializar(JSONObject jsonObject) {
        Playlist playlist = null;
        try {
            playlist = new Playlist();
            playlist.setId(jsonObject.getString("id"));
            playlist.setIdUsuario(jsonObject.getString("idUsuario"));
            playlist.setNombre(jsonObject.getString("nombre"));
            playlist.setEstado(jsonObject.getBoolean("estado"));
            playlist.setContenidos(gestorContenidoJSON.deserializarLista(jsonObject.getJSONArray("contenidos")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    public HashSet<Playlist> deserializarLista(JSONArray jsonArray) {
        HashSet<Playlist> playlists = new HashSet<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Playlist p = deserializar(jsonArray.getJSONObject(i));
                playlists.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public HashSet<Playlist> archivoToLista() {
        JSONTokener jsonTokener = OperacionesLectoEscritura.leer(DEFAULT_FILE_PLAYLIST);
        HashSet<Playlist> playlists = new HashSet<>();
        try {
            if (jsonTokener != null) {
                JSONArray jsonArray = new JSONArray(jsonTokener);
                playlists = deserializarLista(jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playlists;
    }
}
