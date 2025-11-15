package com.cinema.data;

import com.cinema.Rol;
import com.cinema.persistence.OperacionesLectoEscritura;
import com.cinema.models.usuarios.Usuario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collection;

public class gestorUsuariosJson {

    private static final String DEFAULT_FILE = "usuarios.json";

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
            jsonObject.put("rol", u.getRol() != null ? u.getRol().name() : JSONObject.NULL);
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

    public ArrayList<Usuario> archivoALista() {
        JSONTokener tokener = OperacionesLectoEscritura.leer(DEFAULT_FILE);
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            if (tokener == null) return lista; // archivo no existe -> lista vacía
            lista = deserializarLista(new JSONArray(tokener));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        // Sincronizar el contador estático de Usuario con el máximo id en el archivo
//        int maxId = 0;
//        for (Usuario u : lista) {
//            if (u.getId() > maxId) maxId = u.getId();
//        }
//        // Mantener el mayor entre el contador actual y el maxId del archivo
//        Usuario.setContador(Math.max(Usuario.getContador(), maxId));

        return lista;
    }

    public Usuario deserializar (JSONObject jsonObject) {
        Usuario u = new Usuario();
        try {
            if (jsonObject.has("id") && !jsonObject.isNull("id")) u.setId(jsonObject.getInt("id"));
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public ArrayList<Usuario> deserializarLista (JSONArray jsonArray) {
        ArrayList<Usuario> lista = new ArrayList<>();
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

    // Nuevos métodos: comprobar existencia por email, registrar y listar

    public boolean existePorEmail(String email) {
        if (email == null) return false;
        String target = email.trim().toLowerCase();
        ArrayList<Usuario> lista = archivoALista();
        for (Usuario u : lista) {
            if (u.getEmail() != null && u.getEmail().trim().toLowerCase().equals(target)) return true;
        }
        return false;
    }

    public boolean registrar(Usuario u) {
        if (u == null || u.getEmail() == null) return false;
        ArrayList<Usuario> lista = archivoALista();
        String target = u.getEmail().trim().toLowerCase();
        for (Usuario x : lista) {
            if (x.getEmail() != null && x.getEmail().trim().toLowerCase().equals(target)) {
                return false; // ya existe
            }
        }
        // Añadir y persistir
        lista.add(u);
        // Asegurar contador coherente
        //Usuario.setContador(Math.max(Usuario.getContador(), u.getId()));
        listaToArchivo(lista);
        return true;
    }

    public ArrayList<Usuario> listarUsuarios() {
        return archivoALista();
    }

    public Usuario login(String email, String password) {
        if (email == null || password == null) return null;
        String targetEmail = email.trim().toLowerCase();
        String targetPassword = password; // considerar hashing en producción
        ArrayList<Usuario> lista = archivoALista();
        for (Usuario u : lista) {
            if (u.getEmail() != null && u.getEmail().trim().toLowerCase().equals(targetEmail)
                    && u.getPassword() != null && u.getPassword().equals(targetPassword)) {
                return u; // encontrado
            }
        }
        return null; // no encontrado
    }
}
