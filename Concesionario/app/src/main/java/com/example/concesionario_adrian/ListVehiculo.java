package com.example.concesionario_adrian;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListVehiculo extends AppCompatActivity implements Adaptador.ItemClickListener {

    private Adaptador adaptador;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    private List<Vehiculo> vehiculos;
    private Vehiculo vehiculo;
    private JSonSerializable miJSon;
    private Animation miAnimacion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycler);
        leerDatos();
        linearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayout);
        adaptador = new Adaptador(vehiculos, this);
        recyclerView.setAdapter(adaptador);

    }

    private void leerDatos() {
        miJSon = new JSonSerializable("miConcesionario.json", ListVehiculo.this.getApplicationContext());
        try {
            vehiculos = miJSon.load();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void guardarDatos(){
        try {
            miJSon.save(vehiculos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarDatos();
    }

    @Override
    protected void onResume(){
        super.onResume();
        miAnimacion = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animacion);
        miAnimacion.setDuration(5000);
        adaptador.envioSharedPreferences(miAnimacion);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return false;
    }


    public void itemClick(int position){
        vehiculo = vehiculos.get(position);
        borrarVehiculo(vehiculo);
    }
    
    public void borrarVehiculo(Vehiculo v) {
        avisoBorrarVehiculo(v).show();
    }

    public AlertDialog avisoBorrarVehiculo(Vehiculo v) {
        AlertDialog.Builder aviso = new AlertDialog.Builder(this);
        aviso.setMessage("Desea borrar este Vehiculo?")
                .setTitle("Confirmar Borrado del Vehiculo")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        vehiculo = v;
                        adaptador.removeVehiculo(v);
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


    @Override
    public void onItemClick(int position) {
        itemClick(position);
    }
}
