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

public class ListaFragment extends Fragment {

    //lista de elementos
    List<Producto> productoList = new ArrayList<>();

    RecyclerViewAdapterProduct adapter;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista,container,false);

        ApiService apiService = ApiCliente.getCliente().create(ApiService.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //vamos a cargar todos los productos
        Call<List<Producto>> listCall = apiService.getAllProducto();

        listCall.enqueue(new Callback<List<Producto>>() {
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


        return view;
    }
}