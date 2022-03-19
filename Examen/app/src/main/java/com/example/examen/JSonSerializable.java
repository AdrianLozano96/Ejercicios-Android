package com.example.examen;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSonSerializable {
    private String fileName; // nombre del fichero JSON que se va a guardar la lista
    private Context contexto; // contexto de donde se van a guardar el fichero JSON

    //crear el constructor con el nombre del fichero y el contexto
    JSonSerializable(String fileName, Context contexto) {
        this.fileName = fileName;
        this.contexto = contexto;
    }

    /**
     * Método para grabar los datos al disco
     * escribe en un JSon en memeoria un backup de las notas para poder recuperarlas
     * @param notas a guardar
     */
    public void save(List<Alumno> notas)throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(notas);
        Writer writer = null;

        try{
            // output Stream abre el fichero donde guardaremos el JSON
            OutputStream out = contexto.openFileOutput(fileName,contexto.MODE_PRIVATE);
            // el escritor ya sabe donde escribir su contenido (fichero JSON)
            writer = new OutputStreamWriter(out);
            //el escritor escribe en el disco el array pasado a JSON (escribe un string)
            writer.write(json);

        }finally {
            //esta accion se realiza siempre
            // si el writer ha podido abrir el fichero, es importante que lo cierre para que no se corronpa
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Método para leer los datos al disco
     * recupera las notas guardadas en el JSon en la sesion anterior
     * @return la lista de notas almacenadas en el JSon
     */
    public List<Alumno> load() throws IOException{
        List<Alumno> misAlumnos;

        BufferedReader reader = null;   //Buffered reader para leer los datos JSON

        try{
            //input Stream abre el fichero JSON que vamos a leer y procesar
            // el lector, ya sabe de donde leer los datos, de que fichero JSON
            reader= new BufferedReader(new InputStreamReader(contexto.openFileInput(fileName)));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Alumno>>(){}.getType(); //indicamos el tipo de dato a convertir
            misAlumnos= gson.fromJson(reader, type);    // convertir
        } catch (FileNotFoundException e) {
            // la primera  vez nos va a dar error, por que el fichero de notas no existe
            // vamos a crear la lista para evitar el error en el adaptador
            misAlumnos = new ArrayList<Alumno>();
        }finally{
            // cerrar el fichero
            if(reader != null){
                reader.close();
                }
            }
        // el metodo retorna la lista
        return misAlumnos;
    }
}