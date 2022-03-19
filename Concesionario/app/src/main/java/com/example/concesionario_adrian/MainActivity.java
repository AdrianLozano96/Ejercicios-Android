package com.example.concesionario_adrian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private Button list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.am_btn_add);
        list = findViewById(R.id.am_btn_list);

        add.setOnClickListener(a ->{
            Intent actividadAdd = new Intent(this, AddVehiculo.class);
            startActivity(actividadAdd);
        });

        list.setOnClickListener(a ->{
            Intent actividadList = new Intent(this, ListVehiculo.class);
            startActivity(actividadList);
        });
    }
}