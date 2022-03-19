package com.example.concesionario_adrian;

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
    private String fileName;
    private Context contexto;

    JSonSerializable(String fileName, Context contexto) {
        this.fileName = fileName;
        this.contexto = contexto;
    }

    public void save(List<Vehiculo> v)throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(v);
        Writer writer = null;

        try{
            OutputStream out = contexto.openFileOutput(fileName,contexto.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(json);

        }finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    public List<Vehiculo> load() throws IOException{
        List<Vehiculo> misVehiculos;

        BufferedReader reader = null;

        try{
            reader= new BufferedReader(new InputStreamReader(contexto.openFileInput(fileName)));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Vehiculo>>(){}.getType();
            misVehiculos= gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            misVehiculos = new ArrayList<Vehiculo>();
        }finally{
            if(reader != null){
                reader.close();
                }
            }
        return misVehiculos;
    }
}