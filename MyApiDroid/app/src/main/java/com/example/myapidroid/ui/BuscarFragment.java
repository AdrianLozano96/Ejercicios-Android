package com.example.myapidroid.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapidroid.R;
import com.example.myapidroid.RecyclerViewAdapterProduct;
import com.example.myapidroid.api.ApiCliente;
import com.example.myapidroid.api.ApiService;
import com.example.myapidroid.model.Producto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarFragment extends Fragment {

    EditText etId;
    TextView tvTitle, tvPrice, tvCategory, tvDescripcion;
    ImageView imageViewProducto;
    Button btnSearch;

    ApiService apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = ApiCliente.getCliente().create(ApiService.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buscar,container,false);

        etId = view.findViewById(R.id.editText_id);
        tvTitle =view.findViewById(R.id.textView_title);
        tvPrice = view.findViewById(R.id.textView_price);
        tvCategory = view.findViewById(R.id.textView_category);
        tvDescripcion = view.findViewById(R.id.textView_descripcion);
        imageViewProducto = view.findViewById(R.id.image_producto);

        btnSearch = view.findViewById(R.id.button_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //recoger el id
                int num = Integer.parseInt(etId.getText().toString());
                //con el id realizamos la busqueda
                Call<Producto> productoCall = apiService.getProduct(num);

                productoCall.enqueue(new Callback<Producto>() {
                    @Override
                    public void onResponse(Call<Producto> call, Response<Producto> response) {

                        if (!response.isSuccessful()){
                            Toast.makeText(getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                        }
                        //vamos a mostrar el producto
                        Producto producto = response.body();
                        tvTitle.setText(producto.getTitle());
                        tvPrice.setText("Precio: "+producto.getPrice());
                        tvCategory.setText("Categoria: "+producto.getCategory());
                        tvDescripcion.setText("Descripcion: "+producto.getDescription());

                        Glide.with(getActivity())
                                .load(producto.getImage())
                                .error(R.drawable.ic_launcher_background)
                                .into(imageViewProducto)
                        ;

                    }

                    @Override
                    public void onFailure(Call<Producto> call, Throwable t) {
                        //ha fallado la conexion
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("info", "onFailure: "+ t.getMessage());
                    }
                });

            }
        });

        return view;

    }

}