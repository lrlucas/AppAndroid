package com.suarez.lucas.platzigram.apifirebase;

import com.suarez.lucas.platzigram.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
