package com.example.apppersonal_adrian.list_tareas;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonSerializar {


    ArrayList<Tarea> lisTarea;

    private String mFilename;
    private Context mContext;
    private Gson gson;

    public JsonSerializar(String mFilename, Context mContext) {
        this.mFilename = mFilename;
        this.mContext = mContext;
    }

    public  void save (List<Tarea> lisTarea)  throws IOException {

        gson = new Gson();

        String json = gson.toJson(lisTarea);

        Writer writer = null;

        try{

            OutputStream out = mContext.openFileOutput(mFilename, mContext.MODE_PRIVATE);

            writer = new OutputStreamWriter(out);

            writer.write(json);

        } finally {

            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public ArrayList<Tarea> load () throws IOException{

        BufferedReader reader = null;
        try {

            InputStream in = mContext.openFileInput(mFilename);

            reader = new BufferedReader(new InputStreamReader(in));

            gson = new Gson();

            Type type = new TypeToken<ArrayList<Tarea>>(){} .getType();

            lisTarea = gson.fromJson(reader,type);

        } catch (FileNotFoundException e){

            lisTarea = new ArrayList<Tarea>();
        } finally {

            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  lisTarea;
    }

}
