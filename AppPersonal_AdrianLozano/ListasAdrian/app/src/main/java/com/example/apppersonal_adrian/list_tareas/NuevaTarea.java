package com.example.apppersonal_adrian.list_tareas;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.apppersonal_adrian.R;

public class NuevaTarea extends DialogFragment {

    private Tareas tareas;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.nueva_tarea,null);

        EditText editTitulo = dialogView.findViewById(R.id.nt_titulo);
        EditText editDescripcion = dialogView.findViewById(R.id.nt_descripcion);

        CheckBox checkBoxFavoritos = dialogView.findViewById(R.id.nt_favoritos);

        Button btnCancelar = dialogView.findViewById(R.id.nt_cancelar);
        Button btnOk = dialogView.findViewById(R.id.nt_aceptar);

        builder.setView(dialogView)
                .setMessage("Añadir una nueva Tarea");

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tareas = (Tareas) getActivity();

                Tarea newTarea = new Tarea();

                newTarea.setTitulo(editTitulo.getText().toString());
                newTarea.setDescripcion(editDescripcion.getText().toString());
                newTarea.setFavoritos(checkBoxFavoritos.isChecked());

                if(editTitulo.getText().toString().equals("")){
                    tareas.verificarTarea();
                    dismiss();
                }else{
                    Tareas llamarTareas = (Tareas) getActivity();
                    llamarTareas.crearNuevaTarea(newTarea);
                    dismiss();
                }
            }
        });

        return builder.create();
    }

}






