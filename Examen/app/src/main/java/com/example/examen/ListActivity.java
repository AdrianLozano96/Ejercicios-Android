package com.example.examen;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Adaptador adaptador;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    private List<Alumno> alumnos;
    private JSonSerializable miJSon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycler);
        leerDatos();
        linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        adaptador = new Adaptador(alumnos);
        recyclerView.setAdapter(adaptador);
    }



    //leer datos desde el fichero
    private void leerDatos() {
        miJSon = new JSonSerializable("miLista.json", ListActivity.this.getApplicationContext());
        try {
            alumnos = miJSon.load();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
