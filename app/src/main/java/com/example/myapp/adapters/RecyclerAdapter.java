package com.example.myapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.model.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private final ArrayList<Article> articleslist = new ArrayList<>();
    public SharedPreferences sp;

    public RecyclerAdapter(Context context) {
        sp = context.getSharedPreferences("articles", MODE_PRIVATE);
    }

    public void updateArticles(List<Article> articles){
        articleslist.clear();
        articleslist.addAll(articles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    //set the article information to fit the way the xml is built
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        String title = articleslist.get(position).getTitle();
        String description = articleslist.get(position).getDescription();
        String category = articleslist.get(position).getCategory();
        holder.title.setText(title);
        holder.description.setText(description);
        holder.category.setText(category);
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //save the chosen article to the shared pref in order to show it in the favorite page later
            public void onClick(View view) {
                SharedPreferences.Editor editor =sp.edit();
                String s = sp.getString("articles", "[]");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Article>>(){} .getType();
                ArrayList<Article> articles = gson.fromJson(s, type);
                articles.add(articleslist.get(holder.getAdapterPosition()));
                String saveArticlesString = gson.toJson(articles);
                editor.putString("articles",saveArticlesString);
                editor.commit();
                Toast.makeText(holder.category.getContext(), "saved", Toast.LENGTH_LONG).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return articleslist.size();
    }

    //declaration of the Views
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final TextView category;
        private final Button saveButton;
        //connecting the code parameters to the xml views
        public MyViewHolder(final View view) {
            super(view);
            title = view.findViewById(R.id.titleTextView);
            description = view.findViewById(R.id.descriptionTextView);
            category = view.findViewById(R.id.categoryOfArticleView);
            saveButton = view.findViewById(R.id.saveButton);

        }
    }
}



