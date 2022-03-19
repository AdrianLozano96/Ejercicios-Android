package com.example.examen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    //Declaración de variables
    List<Alumno> alumnos;

    public Adaptador(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    //Variables sharedPreferences y animaciones

    //Métodos

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false); //con false se evita que se destruya
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        //Se personaliza la visualización
        //Alamono miAlumno = alumnos.get(position);

        holder.nombre.setText(alumnos.get(position).getNombre());
        //holder.nombre.setText(miAlumno.getNombre());
        //holder.imagen.setImageResource(R.drawable.nombreImagen);
        holder.notaMedia.setText(calcularMedia(alumnos.get(position)));
        /*
        int nota1 = alumnos.get(position).getNota1();
        int nota2 = alumnos.get(position).getNota2();
        int nota3 = alumnos.get(position).getNota3();
        int media = (nota1+nota2+nota3)/3;
        holder.nombre.setText(alumnos.get(position).getNombre());

        if(nota1<5 && nota2<5 && nota3<5){
            holder.media.setText(String.valueOf(media));
        }else{
            holder.media.setText(R.string.suspenso);
        }
         */
        /*
        //mostrar las imagenes
        if (! miAlumno.isImportante()){
            holder.imageViewImportante.setVisibility(View.GONE);
        }
         */
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }
/*
    public void addNewAlumno(Alumno alumno) {
        alumnos.add(alumno);
    }

 */

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, notaMedia; //Se corresponde con los elementos del layout.
        //ImageView image
        //Muestra elementos
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.il_nombre);
            notaMedia = itemView.findViewById(R.id.il_media);
            //imageViewIdea = itemView.findViewById(R.id.imageView_idea);
        }
    }
/*
    private String calcularMedia(Alumno alumno) {
        if (!(alumno.getNota1() > 0 && alumno.getNota1() <= 10)) {
            alumno.setNota1(0);
        }
        if (!(alumno.getNota2() > 0 && alumno.getNota2() <= 10)) {
            alumno.setNota2(0);
        }
        if (!(alumno.getNota3() > 0 && alumno.getNota3() <= 10)) {
            alumno.setNota3(0);
        }
        if(alumno.getNota1() < 5 || alumno.getNota2() < 5 || alumno.getNota3() < 5) {
            return "Debe superar algun modulo.";
        } else {
            double media = ((alumno.getNota1()+alumno.getNota2()+ alumno.getNota3())/3);
            return (""+media);
        }
    }

 */
    private String calcularMedia(Alumno alumno) {
        double media = ((alumno.getNota1()+alumno.getNota2()+ alumno.getNota3())/3);
        return (""+media);
    }

}
