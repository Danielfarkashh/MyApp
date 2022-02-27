package com.example.myapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.api.GetArticlesListAPI;
import com.example.myapp.R;
import com.example.myapp.adapters.RecyclerAdapter;
import com.example.myapp.model.Article;
import com.example.myapp.model.ArticleResponse;
import com.example.myapp.model.Datum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton favPage;
    private ImageButton favoriteBtn;
    private RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.MainRecyclerView);
        setAdapter();
        favPage = findViewById(R.id.favPageButtom);
        favPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
        fetchArticles();
    }

    private void setAdapter() {
        adapter = new RecyclerAdapter(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void fetchArticles() {
        //connecting to the api and declaring the returning data type
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.mediastack.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetArticlesListAPI getArticlesListAPI = retrofit.create(GetArticlesListAPI.class);
        Call<ArticleResponse> call = getArticlesListAPI.getArticles();
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                ArrayList<Article> atricleListForAdapter = new ArrayList<>();
                ArticleResponse atricleResponse = response.body();
                for (Datum datum : atricleResponse.getData()) {
                    atricleListForAdapter.add(new Article(datum.getTitle(), datum.getDescription(), datum.getCategory()));
                }
                adapter.updateArticles(atricleListForAdapter);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {

            }
        });


    }
}

