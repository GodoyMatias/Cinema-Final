package com.cinema.data;
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
import java.util.List;

public class GestorContenidosJSON {
    private static final String DEFAULT_FILE= "contenidos.json";

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

    public JSONArray serializarLista(List<Contenido> contenido) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Contenido p : contenido) {
                if(p instanceof Pelicula){
                jsonArray.put(serializar((Pelicula) p));
                }else if (p instanceof Serie){
                    jsonArray.put(serializar((Serie) p));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public void listaToArchivo(List<Contenido> contenido){
        OperacionesLectoEscritura.grabar(DEFAULT_FILE, serializarLista(contenido));
    }

    public Contenido deserializar(JSONObject jsonObject) {
        Contenido contenido = null;
        try {
            contenido.setId(jsonObject.getInt("id"));
            contenido.setTitulo(jsonObject.getString("titulo"));
            contenido.setGenero(jsonObject.getEnum(Genero.class, "genero"));
            contenido.setAnio(jsonObject.getInt("anio"));
            contenido.setDirector(jsonObject.getString("director"));
            contenido.setEstado(jsonObject.getBoolean("estado"));
            contenido.setResenias(GestorReseniaJSON.deserializarLista(jsonObject.getJSONArray("resenias")));
            if(contenido instanceof Pelicula){
                ((Pelicula) contenido).setDuracion(jsonObject.getInt("duracion"));
            }else if (contenido instanceof Serie){
                ((Serie) contenido).setEpisodios(jsonObject.getInt("episodios"));
                ((Serie) contenido).setTemporadas(jsonObject.getInt("temporadas"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contenido;
    }

    public List<Contenido> deserializarLista(JSONArray jsonArray) {
        List<Contenido> contenidos = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                Contenido p = deserializar(jsonArray.getJSONObject(i));
                contenidos.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return contenidos;
    }

    public List<Contenido> archivoALista() {
        JSONTokener tokener = OperacionesLectoEscritura.leer(DEFAULT_FILE);
        List<Contenido> lista = new ArrayList<>();
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
