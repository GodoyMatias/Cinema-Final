package com.cinema.persistence;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import java.io.*;

    public class OperacionesLectoEscritura {

        // SERIALIZAR

        public static void grabar(String nombreArchivo, JSONArray jsonArray) {
            try {
                File f = new File(nombreArchivo);
                if (f.getParentFile() != null && !f.getParentFile().exists()) {
                    f.getParentFile().mkdirs();
                }
                FileWriter fileWriter = new FileWriter(f);
                fileWriter.write(jsonArray.toString(4));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // DESERIALIZAR

        public static JSONTokener leer(String nombreArchivo) {
            JSONTokener tokener = null;
            try {
                tokener = new JSONTokener(new FileReader(nombreArchivo));
            } catch (FileNotFoundException e) {
                // archivo no existe -> flujo normal, devolver null
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return tokener;
        }
    }