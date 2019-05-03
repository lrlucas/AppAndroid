package com.suarez.lucas.platzigram.view.fragments;


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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.adapter.PictureAdapterRecyclerView;
import com.suarez.lucas.platzigram.adapter.PostAdapterRecyclerView;
import com.suarez.lucas.platzigram.api.AppCliente;
import com.suarez.lucas.platzigram.api.AppFirebaseService;
import com.suarez.lucas.platzigram.api.PostResponse;
import com.suarez.lucas.platzigram.model.Picture;
import com.suarez.lucas.platzigram.model.Post;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView picturesRecycler;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Post> posts;
    PostAdapterRecyclerView postAdapterRecyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home), false, view);
        posts = new ArrayList<>();

        populateData();

        picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);

        postAdapterRecyclerView = new PostAdapterRecyclerView(posts, R.layout.cardview_picture, getActivity());
        picturesRecycler.setAdapter(postAdapterRecyclerView);



        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewPostFragment newPostFragment = new NewPostFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newPostFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }

    private void populateData() {
        AppFirebaseService service = (new AppCliente()).getService();
        Call<PostResponse> postListCall = service.getPostList();
        postListCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.isSuccessful()) {
                    PostResponse result = response.body();
                    posts.clear();
                    //TODO aqui se meten todas las images al post
                    posts.addAll(result.getPostList());
                    postAdapterRecyclerView.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                System.out.println("Error la ptm");
            }
        });
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
