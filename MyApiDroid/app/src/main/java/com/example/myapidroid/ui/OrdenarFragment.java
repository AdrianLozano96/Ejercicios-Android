package com.example.myapidroid.ui;

import android.os.Bundle;

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

public class OrdenarFragment extends Fragment {

    EditText editTextOrder;
    Button btnOrden;

    //lista de elementos
    List<Producto> productoList = new ArrayList<>();

    RecyclerViewAdapterProduct adapter;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordenar, container, false);


        editTextOrder = view.findViewById(R.id.editText_orden);

        btnOrden = view.findViewById(R.id.button_order);


        String order = editTextOrder.toString();

        ApiService apiService = ApiCliente.getCliente().create(ApiService.class);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_order);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        btnOrden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //vamos a cargar todos los productos
                Call<List<Producto>> orderCall = apiService.getProductoOrdenado(order);

                orderCall.enqueue(new Callback<List<Producto>>() {
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

        return  view;
    }
}