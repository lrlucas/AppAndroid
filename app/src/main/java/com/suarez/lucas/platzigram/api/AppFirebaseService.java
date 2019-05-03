package com.suarez.lucas.platzigram.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AppFirebaseService {
    @GET("post")
    Call<PostResponse> getPostList();
}
