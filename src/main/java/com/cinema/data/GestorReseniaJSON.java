package com.cinema.data;

import com.cinema.models.contenido.Resenia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestorReseniaJSON {
    public static JSONObject serializar(Resenia resenia) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", resenia.getId());
            jsonObject.put("usuarioId", resenia.getIdUsuario());
            jsonObject.put("contenidoId", resenia.getIdContenido());
            jsonObject.put("calificacion", resenia.getEstrellas());
            jsonObject.put("comentario", resenia.getComentario());
            jsonObject.put("estado", resenia.isEstado());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray serializarLista(List<Resenia> resenias) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (Resenia resenia : resenias) {
                jsonArray.put(serializar(resenia));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static Resenia deserializar(JSONObject jsonObject) {
        Resenia resenia = null;
        try {
            resenia = new Resenia();
            resenia.setId(jsonObject.getInt("id"));
            resenia.setIdUsuario(jsonObject.getInt("usuarioId"));
            resenia.setIdContenido(jsonObject.getInt("contenidoId"));
            resenia.setEstrellas(jsonObject.getInt("calificacion"));
            resenia.setComentario(new StringBuilder(jsonObject.getString("comentario")));
            resenia.setEstado(jsonObject.getBoolean("estado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resenia;
    }

    public static List<Resenia> deserializarLista(JSONArray jsonArray){
        List<Resenia> resenias = new ArrayList<>();
        try{
            for(int i = 0; i < jsonArray.length(); i++){
                Resenia r = deserializar(jsonArray.getJSONObject(i));
                resenias.add(r);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return resenias;
    }
}
