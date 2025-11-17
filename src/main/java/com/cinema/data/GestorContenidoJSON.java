package com.cinema.data;

import com.cinema.models.contenido.*;
import com.cinema.persistence.OperacionesLectoEscritura;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class GestorContenidoJSON {

    private static final String DEFAULT_FILE_CONTENIDO = "contenidos.json";

    // ============================================================
    // SERIALIZACIÓN PELÍCULA
    // ============================================================

    public JSONObject serializar(Pelicula p) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", p.getId());
            json.put("titulo", p.getTitulo());
            json.put("genero", p.getGenero() != null ? p.getGenero().name() : JSONObject.NULL);
            json.put("anio", p.getAnio());
            json.put("director", p.getDirector());
            json.put("duracion", p.getDuracion());
            json.put("estado", p.isEstado());
            json.put("resenias", GestorReseniaJSON.serializarLista(p.getResenias()));
            json.put("tipo", p.getTipo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    // ============================================================
    // SERIALIZACIÓN SERIE
    // ============================================================

    public JSONObject serializar(Serie s) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", s.getId());
            json.put("titulo", s.getTitulo());
            json.put("genero", s.getGenero() != null ? s.getGenero().name() : JSONObject.NULL);
            json.put("anio", s.getAnio());
            json.put("director", s.getDirector());
            json.put("episodios", s.getEpisodios());
            json.put("temporadas", s.getTemporadas());
            json.put("estado", s.isEstado());
            json.put("resenias", GestorReseniaJSON.serializarLista(s.getResenias()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    // ============================================================
    // LISTA → JSON
    // ============================================================

    public JSONArray serializarLista(Map<String, Contenido> contenidoMap) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (Contenido contenido : contenidoMap.values()) {

                if (contenido.getTipo() == Tipo.PELICULA) {
                    jsonArray.put(serializar((Pelicula) contenido));
                } else if (contenido.getTipo() == Tipo.SERIE) {
                    jsonArray.put(serializar((Serie) contenido));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    // ============================================================
    // GUARDAR ARCHIVO
    // ============================================================

    public void listaToArchivo(Map<String, Contenido> contenidoMap) {
        JSONArray array = serializarLista(contenidoMap);
        OperacionesLectoEscritura.grabar(DEFAULT_FILE_CONTENIDO, array);
    }

    // ============================================================
    // JSON → OBJETO
    // ============================================================

    public Contenido deserializar(JSONObject json) {
        Contenido contenido = null;

        try {
            boolean esPelicula = json.has("duracion");

            contenido = esPelicula ? new Pelicula() : new Serie();

            contenido.setId(json.getString("id"));
            contenido.setTitulo(json.getString("titulo"));
            contenido.setGenero(Genero.valueOf(json.getString("genero")));
            contenido.setAnio(json.getInt("anio"));
            contenido.setDirector(json.getString("director"));
            contenido.setEstado(json.getBoolean("estado"));
            contenido.setResenias(GestorReseniaJSON.deserializarLista(json.getJSONArray("resenias")));

            if (esPelicula) {
                ((Pelicula) contenido).setDuracion(json.getInt("duracion"));
            } else {
                ((Serie) contenido).setEpisodios(json.getInt("episodios"));
                ((Serie) contenido).setTemporadas(json.getInt("temporadas"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contenido;
    }

    // ============================================================
    // JSON → MAP
    // ============================================================

    public Map<String, Contenido> deserializarLista(JSONArray array) {
        Map<String, Contenido> contenidos = new HashMap<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject json = array.getJSONObject(i);
                Contenido contenido = deserializar(json);
                contenidos.put(contenido.getId(), contenido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contenidos;
    }

    // ============================================================
    // ARCHIVO → MAP
    // ============================================================

    public Map<String, Contenido> archivoALista() {
        JSONTokener tokener = OperacionesLectoEscritura.leer(DEFAULT_FILE_CONTENIDO);
        Map<String, Contenido> contenidos = new HashMap<>();

        try {
            if (tokener == null) {
                return contenidos;
            }

            JSONArray array = new JSONArray(tokener);
            contenidos = deserializarLista(array);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contenidos;
    }
}
