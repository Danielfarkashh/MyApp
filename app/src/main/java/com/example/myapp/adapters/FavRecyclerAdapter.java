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

public class FavRecyclerAdapter extends RecyclerView.Adapter<FavRecyclerAdapter.MyViewHolder> {
    private final ArrayList<Article> favArticleslist;
    private final SharedPreferences sp;
    public FavRecyclerAdapter(Context context, ArrayList<Article> favArticleslist) {
        this.favArticleslist = favArticleslist;
        sp = context.getSharedPreferences("articles", MODE_PRIVATE);
    }

    @NonNull
    @Override
    public FavRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    //set the article information to fit the way the xml is built
    public void onBindViewHolder(@NonNull FavRecyclerAdapter.MyViewHolder holder, int position) {
        final String title = favArticleslist.get(position).getTitle();
        String description = favArticleslist.get(position).getDescription();
        String category = favArticleslist.get(position).getCategory();
        holder.title1.setText(title);
        holder.description1.setText(description);
        holder.category1.setText(category);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            //remove the chosen article from the shared pref
            public void onClick(View view) {
                SharedPreferences.Editor editor =sp.edit();
                String s = sp.getString("articles", "[]");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Article>>(){} .getType();
                ArrayList<Article> articles = gson.fromJson(s, type);
                for(int i=0;i< articles.size();i++){
                    if(title.equals(articles.get(i).getTitle())){
                        articles.remove(i);
                        break;
                    }
                }
                String saveArticlesString = gson.toJson(articles);
                editor.putString("articles",saveArticlesString);
                editor.commit();
                Toast.makeText(holder.category1.getContext(), "Deleted", Toast.LENGTH_LONG).show();
                favArticleslist.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favArticleslist.size();
    }
    //declaration of the Views
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title1;
        private final TextView description1;
        private final TextView category1;
        private final Button button;

        //connecting the code parameters to the xml views
        public MyViewHolder(final View view) {
            super(view);
            title1 = view.findViewById(R.id.titleTextView);
            description1 = view.findViewById(R.id.descriptionTextView);
            category1 = view.findViewById(R.id.categoryOfArticleView);
            button = view.findViewById(R.id.saveButton);
            button.setText("Remove");
        }
    }
}
