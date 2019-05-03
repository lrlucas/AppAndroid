package com.suarez.lucas.platzigram.apifirebase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/post.json")
    Call<List<PostModel>> getAllPhotos();
}
