package com.example.apppersonal_adrian.list_tareas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apppersonal_adrian.R;

import java.util.ArrayList;

public class Tareas extends AppCompatActivity implements TareaItemClickListener {

    Tarea miTarea;
    ArrayList<Tarea> lisTarea;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    TareaRecyclerAdapter recyclerAdapter;
    private JsonSerializar jsonSerial;
    private Animation miAnimacionFlash;
    private Animation miAnimacionDesplazar;
    private Animation miAnimacionTransicion;
    private Animation miAnimacionAparece;
    private Animation miAnimacionDesaparece;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        Toast.makeText(this, "APLICACIÓN CON TAREAS PENDIENTES", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recycler);
        leerDatosFichero();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new TareaRecyclerAdapter(lisTarea, this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    private void leerDatosFichero() {

        jsonSerial = new JsonSerializar("LisTareas.json", Tareas.this.getApplicationContext());

        try {
            lisTarea = jsonSerial.load();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void salvarDatosFichero(){
        try {
            jsonSerial.save(lisTarea);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        salvarDatosFichero();
    }

    public void crearNuevaTarea(Tarea newTarea) {
         miTarea = newTarea;
        recyclerAdapter.addTarea(newTarea);
    }

    public void borrarTarea(Tarea newTarea) {
        avisoBorrarTarea(newTarea).show();
    }

    public AlertDialog avisoBorrarTarea(Tarea newTarea) {
        AlertDialog.Builder aviso = new AlertDialog.Builder(this);
        aviso.setMessage("Desea borrar esta Tarea?")
                .setTitle("Confirmar Borrado de Tarea")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        miTarea = newTarea;
                        recyclerAdapter.removeTarea(newTarea);
                    }
                })
                .setNegativeButton("No Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return aviso.create();
    }

    public void verificarTarea(){
        Toast.makeText(this, "Toda tarea deberá de tener almenos un TITULO", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        miAnimacionFlash = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.flash);
        miAnimacionTransicion = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.transicion);
        miAnimacionDesplazar = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.desplazar);
        miAnimacionAparece = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.aparece);
        miAnimacionDesaparece = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.desaparece);

        miAnimacionFlash.setDuration(2500);
        miAnimacionTransicion.setDuration(5000);
        miAnimacionDesplazar.setDuration(5000);
        miAnimacionAparece.setDuration(5000);
        miAnimacionDesaparece.setDuration(5000);
        recyclerAdapter.enviarSharePreferences(
                miAnimacionTransicion, miAnimacionDesplazar, miAnimacionFlash,
                miAnimacionAparece, miAnimacionDesaparece);

        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add){
            NuevaTarea tarea = new NuevaTarea();
            tarea.show(getSupportFragmentManager(),"tarea_crear");
        }

        return false;
    }

    @Override
    public void onTareaItemClick(int position) {

        MostrarTarea dialogo = new MostrarTarea();
        miTarea = lisTarea.get(position);
        dialogo.mostrarTareaSeleccionada(miTarea);
        dialogo.show(getSupportFragmentManager(),"tarea_mostrar");

    }

}
