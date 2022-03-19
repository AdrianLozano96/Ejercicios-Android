package com.example.concesionario_adrian;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{

    private List<Vehiculo> vehiculos;
    final private ItemClickListener mOnClickListener;

    public Adaptador(List<Vehiculo> vehiculos, ItemClickListener mOnClickListener) {
        this.vehiculos = vehiculos;
        this.mOnClickListener = mOnClickListener;
    }

    private Animation miAnimacion;

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {

       holder.itemView.setAnimation(miAnimacion);

       holder.marca.setText(vehiculos.get(position).getMarca());
       holder.modelo.setText(vehiculos.get(position).getModelo());
       holder.matricula.setText(String.valueOf(vehiculos.get(position).getMatricula()));

       if(!vehiculos.get(position).isCoche()){
           holder.coche.setVisibility(View.GONE);
       }
        if(!vehiculos.get(position).isMoto()){
            holder.moto.setVisibility(View.GONE);
        }
        if(!vehiculos.get(position).isCamion()){
            holder.camion.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView marca, modelo, matricula;
        private ImageView coche, moto, camion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            marca = itemView.findViewById(R.id.ai_marca);
            modelo = itemView.findViewById(R.id.ai_modelo);
            matricula = itemView.findViewById(R.id.ai_matricula);
            coche = itemView.findViewById(R.id.ai_coche);
            moto = itemView.findViewById(R.id.ai_moto);
            camion = itemView.findViewById(R.id.ai_camion);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onItemClick(position);
        }
    }

    public void removeVehiculo(Vehiculo v){
        vehiculos.remove(v);
        notifyDataSetChanged();
    }

    public void envioSharedPreferences(Animation a1){
        miAnimacion = a1;
    }

    public interface ItemClickListener {
        void onItemClick (int position);
    }

}
