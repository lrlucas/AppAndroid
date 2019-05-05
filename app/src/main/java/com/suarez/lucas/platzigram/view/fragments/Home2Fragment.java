package com.suarez.lucas.platzigram.view.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.apifirebase.CustomAdapter;
import com.suarez.lucas.platzigram.apifirebase.GetDataService;
import com.suarez.lucas.platzigram.apifirebase.PostModel;
import com.suarez.lucas.platzigram.apifirebase.RetrofitClientInstace;
import com.suarez.lucas.platzigram.model.RetroPhoto;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home2Fragment extends Fragment {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;


    public Home2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        // mostramos el toolbar
        showToolbar(getResources().getString(R.string.tab_home), false, view);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();


        // create un hadle para retrofitIntace
        GetDataService service = RetrofitClientInstace
                .getRetrofitInstance()
                .create(GetDataService.class);



        Call<List<RetroPhoto>> call = service.getAllPhotos();

        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {

                progressDialog.dismiss();

//                    //llamar a un adapter vacio
//                    adapter = new CustomAdapter(response.body(), getActivity());
//                    recyclerView.setAdapter(adapter);

//                    System.out.println("ERRORES DE LA API");
//                    System.out.println(response.body().toString());

                // este metodo traer la lista de fotos
                generateDataList(response.body());

            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Ocurrio un error...", Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPostFragment newPostFragment = new NewPostFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newPostFragment).addToBackStack(null).commit();
            }
        });
        return view;
    }


    // metodo para generar una lista de la data usando RecyclerView con un custom adapter
    private void generateDataList(List<RetroPhoto> retroPhotos) {
        recyclerView = getActivity().findViewById(R.id.customRecyclerView);

        adapter = new CustomAdapter(retroPhotos, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

    }




    public void showToolbar(String title, boolean upButton, View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
