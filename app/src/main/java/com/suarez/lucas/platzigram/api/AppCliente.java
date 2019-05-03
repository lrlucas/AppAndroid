package com.suarez.lucas.platzigram.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppCliente {

    private Retrofit retrofit;

    private final static String FIREBASE_BASE_URL = "https://platzigram-98553.firebaseio.com/";

    public AppCliente() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PostResponse.class, new PostResponseTypeAdapter())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(FIREBASE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }


    public AppFirebaseService getService() {
        return retrofit.create(AppFirebaseService.class);
    }
}
