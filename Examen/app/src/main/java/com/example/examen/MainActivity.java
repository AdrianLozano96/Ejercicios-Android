package com.example.examen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText notaA;
    private EditText notaB;
    private EditText notaC;
    private Button add;
    private Button listar;

    //private Adaptador adaptador;
    private JSonSerializable miJSon;
    private List<Alumno> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leerDatos();
        nombre = findViewById(R.id.am_nombre);
        notaA = findViewById(R.id.am_notaA);
        notaB = findViewById(R.id.am_notaB);
        notaC = findViewById(R.id.am_notaC);
        add = findViewById(R.id.am_btnAdd);
        listar = findViewById(R.id.am_btnLista);
        //CheckBox checkBoxIdea = dialogView.findViewById(R.id.checkBox_idea);

        add.setOnClickListener(a -> {
            Alumno alumno = new Alumno();
            if (!nombre.getText().toString().isEmpty()) {
                alumno.setNombre(nombre.getText().toString());
            } else alumno.setNombre("Nombre desconocido");
            if (!notaA.getText().toString().isEmpty()) {
                alumno.setNota1(Integer.parseInt(notaA.getText().toString()));
            } else alumno.setNota1(0);
            if (!notaB.getText().toString().isEmpty()) {
                alumno.setNota2(Integer.parseInt(notaB.getText().toString()));
            } else alumno.setNota2(0);
            if (!notaC.getText().toString().isEmpty()) {
                alumno.setNota3(Integer.parseInt(notaC.getText().toString()));
            } else alumno.setNota3(0);
            //alumno.setIdea(checkBoxIdea.isChecked()); //Variable booleana
            //adaptador.addNewAlumno(alumno);
            alumnos.add(alumno);
            guardarDatos();
        });

        listar.setOnClickListener(a -> {
           Intent intent = new Intent(this, ListActivity.class);
           startActivity(intent);
        });
        /*
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
         */


    }

    //Inflar el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Indicar lo que hay que realizar cuando se pulsa la opcion del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.listado) { //corresponde al item del menu.
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        }
        return false;
    }



    //leer datos desde el fichero
    private void leerDatos() {
        miJSon = new JSonSerializable("miLista.json", MainActivity.this.getApplicationContext());
        try {
            alumnos = miJSon.load();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //grabar los datos serializable
    private  void guardarDatos(){
        try {
            miJSon.save(alumnos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}