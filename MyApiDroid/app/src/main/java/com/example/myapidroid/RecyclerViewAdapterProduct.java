package com.example.myapidroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapidroid.model.Producto;

import java.util.List;


public class RecyclerViewAdapterProduct extends RecyclerView.Adapter<RecyclerViewAdapterProduct.ViewHolder> {

    List<Producto> productoList;
    Context context;

    public RecyclerViewAdapterProduct(List<Producto> productoList, Context context) {
        this.productoList = productoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_elem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Producto item = productoList.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvPrice.setText(item.getPrice());
        holder.tvCategory.setText(item.getCategory());
        holder.tvDescripcion.setText(item.getDescription());

        //tratamiento de la imagen con glide
        Glide.with(context)
                .load(item.getImage())
                .into(holder.imageViewProduto);

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvPrice, tvCategory,tvDescripcion;
        ImageView imageViewProduto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           tvTitle = itemView.findViewById(R.id.textView_title);
           tvPrice = itemView.findViewById(R.id.textView_price);
           tvCategory = itemView.findViewById(R.id.textView_category);
           tvDescripcion = itemView.findViewById(R.id.textView_descripcion);
           imageViewProduto = itemView.findViewById(R.id.imageView_product);

        }
    }
}
