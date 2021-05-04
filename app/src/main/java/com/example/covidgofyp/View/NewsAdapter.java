package com.example.covidgofyp.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidgofyp.Model.Articles;
import com.example.covidgofyp.Model.Source;
import com.example.covidgofyp.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context context;
    private List<Articles> articlesList;

    public NewsAdapter(Context context, List<Articles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {
        Source source = articlesList.get(position).getSource();
        holder.newsTitle.setText(articlesList.get(position).getTitle());
        holder.newsDescription.setText(articlesList.get(position).getDescription());
        holder.newsPublishedAt.setText(articlesList.get(position).getPublishedAt());
        holder.newsSource.setText("Source: "+source.getName());

        Glide.with(context)
                .load(articlesList.get(position).getUrlToImage())
                .into(holder.newsImage);

        holder.newsUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri url = Uri.parse(articlesList.get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle, newsPublishedAt, newsDescription, newsSource, newsUrl;
        ImageView newsImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newTitle);
            newsPublishedAt = itemView.findViewById(R.id.newsPublishedAt);
            newsDescription = itemView.findViewById(R.id.newsDescription);
            newsSource = itemView.findViewById(R.id.sourceName);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsUrl = itemView.findViewById(R.id.urlToNews);
        }
    }
}
