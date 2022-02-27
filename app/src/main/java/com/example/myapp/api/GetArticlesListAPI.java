package com.example.myapp.api;

import com.example.myapp.model.ArticleResponse;



import retrofit2.Call;
import retrofit2.http.GET;

public interface GetArticlesListAPI {


    //api request
    @GET("news?access_key=2ead25d35c350488b0bdb9d6658b4ac1")
    Call<ArticleResponse> getArticles();



}