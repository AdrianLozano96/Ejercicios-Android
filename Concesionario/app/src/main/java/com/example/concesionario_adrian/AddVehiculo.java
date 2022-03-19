package com.example.concesionario_adrian;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddVehiculo extends AppCompatActivity {

    private EditText marca, modelo, matricula;
    private RadioButton coche, moto, camion;
    private Button add, list;
    private JSonSerializable miJSon;
    private List<Vehiculo> vehiculos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        leerDatos();
        marca = findViewById(R.id.aa_et_marca);
        modelo = findViewById(R.id.aa_et_modelo);
        matricula = findViewById(R.id.aa_et_matricula);
        coche = findViewById(R.id.aa_rb_coche);
        moto = findViewById(R.id.aa_rb_moto);
        camion = findViewById(R.id.aa_rb_camion);
        add = findViewById(R.id.aa_btn_add);
        list = findViewById(R.id.aa_btn_list);

        add.setOnClickListener(a ->{
            Vehiculo v = new Vehiculo();
            v.setMarca(marca.getText().toString());
            v.setModelo(modelo.getText().toString());
            v.setMatricula(Integer.parseInt(matricula.getText().toString()));
            v.setCoche(coche.isChecked());
            v.setMoto(moto.isChecked());
            v.setCamion(camion.isChecked());
            for(Vehiculo ve: vehiculos){
                if(String.valueOf(v.getMatricula()).equals(String.valueOf(ve.getMatricula()))){
                    Toast.makeText(this, "Ya existe esta matricula", Toast.LENGTH_SHORT).show();
                    vehiculos.remove(ve);
                }
            }
            vehiculos.add(v);
            guardarDatos();
        });

        list.setOnClickListener(a->{
           Intent actividadLista = new Intent(this, ListVehiculo.class);
           startActivity(actividadLista);
        });

    }

    private void leerDatos() {
        miJSon = new JSonSerializable("miConcesionario.json", AddVehiculo.this.getApplicationContext());
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

}
