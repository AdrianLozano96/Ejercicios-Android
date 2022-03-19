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
import android.widget.Toast;

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

public class MostrarFragment extends Fragment {

    EditText editTextLimit;
    Button btnMostrar;

    //lista de elementos
    List<Producto> productoList = new ArrayList<>();

    RecyclerViewAdapterProduct adapter;
    LinearLayoutManager layoutManager;

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
        View view = inflater.inflate(R.layout.fragment_mostrar,container,false);

        //unir layout y java
        editTextLimit = view.findViewById(R.id.editText_cantidad);
        btnMostrar = view.findViewById(R.id.button_limit);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_limit);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //boton
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recoger el i
                int cantidad = Integer.parseInt(editTextLimit.getText().toString());


                //vamos a cargar una cantidad (limit) de productos
                Call<List<Producto>> productoLimit = apiService.getLimit(cantidad);

                productoLimit.enqueue(new Callback<List<Producto>>() {
                    @Override
                    public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getActivity(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                        }

                        //cargar la lista de elemento en la lista
                        productoList = response.body();

                        adapter = new RecyclerViewAdapterProduct(productoList,getActivity());
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onFailure(Call<List<Producto>> call, Throwable t) {
                        //ha fallado la conexion
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("info", "onFailure: "+t.getMessage());

                    }
                });
            }
        });


        return view;
    }

}