package com.suarez.lucas.platzigram.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.suarez.lucas.platzigram.LoginActivity;
import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.adapter.PictureAdapterRecyclerView;
import com.suarez.lucas.platzigram.model.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    TextView txtUsernameProfile;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showToolbar("", false, view);

        txtUsernameProfile = view.findViewById(R.id.usernameProfile);

        SharedPreferences prefs = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        txtUsernameProfile.setText(email);

        RecyclerView picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureProfileRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView =
                new PictureAdapterRecyclerView(buildPictures(), R.layout.cardview_picture, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);

        //onClick logout
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public ArrayList<Picture> buildPictures() {
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("https://pl.scdn.co/images/pl/default/8d83a4d18bde3bfad4fcd4be8e1bfc3cc2ffdc44", "Lucas Suarez", "4 dias", "3 Me Gusta"));
        pictures.add(new Picture("https://images-na.ssl-images-amazon.com/images/I/518VNcn1-CL._SX372_BO1,204,203,200_.jpg", "Ruddy Suarez", "2 dias", "3 Me Gusta"));
        pictures.add(new Picture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRueeKzifgQ7V24dpwDL-2I3go8WS_dCD4tzNaBIeLXslMiTlL4w", "Carlos Santana", "5 dias", "3 Me Gusta"));
        pictures.add(new Picture("https://pl.scdn.co/images/pl/default/648a0d8b0c0639f39ef174c747185c0caaa6b9c7", "Lucas Suarez", "5 dias", "3 Me Gusta"));
        pictures.add(new Picture("https://i.ytimg.com/vi/uhBHL3v4d3I/maxresdefault.jpg", "Lucas Suarez", "5 dias", "3 Me Gusta"));
        return pictures;
    }

    public void showToolbar(String title, boolean upButton, View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


}
