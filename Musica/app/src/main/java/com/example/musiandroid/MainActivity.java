package com.example.musiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Librería SoundPool
    SoundPool sp;
    int nowPlaying = -1;    //Sonido reproducido
    int idFX1 = 1;  //Identificar el sonido
    int idFX2 = 1;  //Identificar el sonido
    int idFX3 = 1;  //Identificar el sonido
    //Control de parámetros
    float volumen = .1f; //Control volumen seekbar
    int repeats = 2; //Número repeticiones spinner


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)    //Uso (efecto)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)  //Tipo de contenido
                        .build();

        sp = new SoundPool.Builder()
                .setMaxStreams(5)   //veces que se reproducirá
                .setAudioAttributes(attr)
                .build();

        //Cargar sonido en memoria
        idFX1 = sp.load(this, R.raw.jump, 1);
        idFX2 = sp.load(this, R.raw.mutation, 1);
        idFX3 = sp.load(this, R.raw.randomize, 1);

        //Botones
        Button btSonido1 = findViewById(R.id.sonido1);
        Button btSonido2 = findViewById(R.id.sonido2);
        Button btSonido3 = findViewById(R.id.sonido3);
        Button btStop = findViewById(R.id.stop);

        btSonido1.setOnClickListener(this); //En caso de implementar View.OnClickListener
        btSonido2.setOnClickListener(this); //En caso de implementar View.OnClickListener
        btSonido3.setOnClickListener(this); //En caso de implementar View.OnClickListener
        btStop.setOnClickListener(this); //En caso de implementar View.OnClickListener

        //Controlar el volumen del sonido
        SeekBar seekBar = findViewById(R.id.soundBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //Para el movimiento del seekbar
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                volumen = (float) (i/10.0); //La seekbar va de 0 a 10
                sp.setVolume(nowPlaying, volumen, volumen); //Se asingna el volumen
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Controlar el número de repeticiones
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //Cuando se selecciona coge la posición del item
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = String.valueOf(spinner.getSelectedItemId());
                repeats = Integer.valueOf(temp); //Pasar a Integer
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        /*
        //Si no se implementa View.OnClickListener
        btSonido1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                nowPlaying = sp.play(idFX1, 1, 1, 0, 0, 1);
            }
        });
         */

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sonido1:
                sp.stop(nowPlaying);
                nowPlaying = sp.play(idFX1, volumen, volumen, 0, repeats, 1);
                break;
            case R.id.sonido2:
                sp.stop(nowPlaying);
                nowPlaying = sp.play(idFX2, volumen, volumen, 0, repeats, 1);
                break;
            case R.id.sonido3:
                sp.stop(nowPlaying);
                nowPlaying = sp.play(idFX3, volumen, volumen, 0, repeats, 1);
                break;
            case R.id.stop:
                sp.stop(nowPlaying);
                break;
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        sp.release();
        sp = null;
    }


}