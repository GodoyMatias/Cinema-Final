package com.cinema.data;
import com.cinema.Main;
import com.cinema.models.contenido.Contenido;
import com.cinema.models.contenido.Genero;
import com.cinema.models.contenido.Pelicula;
import com.cinema.models.contenido.Serie;
import com.cinema.persistence.OperacionesLectoEscritura;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorContenidosJSON {
    private static final String DEFAULT_FILE_CONTENIDO= "contenidos.json";

    public JSONObject serializar(Pelicula p) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", p.getId());
            jsonObject.put("titulo", p.getTitulo());
            jsonObject.put("genero", p.getGenero() != null ? p.getGenero().name() : JSONObject.NULL);
            jsonObject.put("anio", p.getAnio());
            jsonObject.put("director", p.getDirector());
            jsonObject.put("duracion", p.getDuracion());
            jsonObject.put("estado", p.isEstado());
            jsonObject.put("resenias", GestorReseniaJSON.serializarLista(p.getResenias()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray serializarLista(Map<Integer, Contenido> contenido) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Map.Entry<Integer, Contenido> entry : contenido.entrySet()) {
                Contenido c = entry.getValue();
                if(c instanceof Pelicula){
                jsonArray.put(serializar((Pelicula) c));
                }else if (c instanceof Serie){
                    jsonArray.put(serializar((Serie) c));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void listaToArchivo(Map<Integer, Contenido> contenido){
        OperacionesLectoEscritura.grabar(DEFAULT_FILE_CONTENIDO, serializarLista(contenido));
    }

    public Contenido deserializar(JSONObject jsonObject) {
        Contenido contenido = null;
        try {
            if (jsonObject.has("duracion")) {
                contenido = new Pelicula();
                ((Pelicula)contenido).setDuracion(jsonObject.getInt("duracion"));
            } else {
                contenido = new Serie();
                ((Serie)contenido).setEpisodios(jsonObject.getInt("episodios"));
                ((Serie)contenido).setTemporadas(jsonObject.getInt("temporadas"));
            }

            contenido.setId(jsonObject.getInt("id"));
            contenido.setTitulo(jsonObject.getString("titulo"));
            contenido.setGenero(Genero.valueOf(jsonObject.getString("genero")));
            contenido.setAnio(jsonObject.getInt("anio"));
            contenido.setDirector(jsonObject.getString("director"));
            contenido.setEstado(jsonObject.getBoolean("estado"));
            contenido.setResenias(GestorReseniaJSON.deserializarLista(jsonObject.getJSONArray("resenias")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contenido;
    }

    public Map<Integer, Contenido> deserializarLista(JSONArray jsonArray) {
        Map<Integer, Contenido> contenidos = new HashMap<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Contenido p = deserializar(jsonArray.getJSONObject(i));
                contenidos.put(p.getId(), p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contenidos;
    }

    public Map<Integer, Contenido> archivoALista() {
        JSONTokener tokener = OperacionesLectoEscritura.leer(DEFAULT_FILE_CONTENIDO);
        Map<Integer, Contenido> lista = new HashMap<>();
        try {
            if (tokener == null) return lista; // archivo no existe -> lista vacía
            lista = deserializarLista(new JSONArray(tokener));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public JSONObject serializar(Serie p) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", p.getId());
            jsonObject.put("titulo", p.getTitulo());
            jsonObject.put("genero", p.getGenero() != null ? p.getGenero().name() : JSONObject.NULL);
            jsonObject.put("anio", p.getAnio());
            jsonObject.put("director", p.getDirector());
            jsonObject.put("episodios", p.getEpisodios());
            jsonObject.put("temporadas", p.getTemporadas());
            jsonObject.put("estado", p.isEstado());
            jsonObject.put("resenias", GestorReseniaJSON.serializarLista(p.getResenias()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
