package com.example.myapp.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.adapters.FavRecyclerAdapter;
import com.example.myapp.R;
import com.example.myapp.model.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView favRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favRecyclerView = findViewById(R.id.favoritesRecyclerView);
        setAdapter();
    }



    private void setAdapter() {
        SharedPreferences sp = getSharedPreferences("articles", MODE_PRIVATE);;
        String s = sp.getString("articles", "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Article>>(){} .getType();
        ArrayList<Article> articles = gson.fromJson(s, type);
        FavRecyclerAdapter adapter = new FavRecyclerAdapter(this,articles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        favRecyclerView.setLayoutManager(layoutManager);
        favRecyclerView.setItemAnimator(new DefaultItemAnimator());
        favRecyclerView.setAdapter(adapter);
    }

}
