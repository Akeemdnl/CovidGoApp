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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidgofyp.Model.Articles;
import com.example.covidgofyp.Model.Source;
import com.example.covidgofyp.R;

import java.util.Date;
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
        holder.newsSource.setText("Source: "+source.getName());

        String date = articlesList.get(position).getPublishedAt();
        String YY = date.substring(0,4);
        String DD = date.substring(8,10);
        String MM = date.substring(5,7);

        holder.newsPublishedAt.setText(DD + "-"+ MM + "-"+YY);

        Glide.with(context)
                .load(articlesList.get(position).getUrlToImage())
                .into(holder.newsImage);

        holder.cvNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,NewsDetail.class);
                intent.putExtra("title",articlesList.get(position).getTitle());
                intent.putExtra("source",source.getName());
                intent.putExtra("time",DD + "-"+ MM + "-"+YY);
                intent.putExtra("imageUrl",articlesList.get(position).getUrlToImage());
                intent.putExtra("desc",articlesList.get(position).getDescription());
                intent.putExtra("url",articlesList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle, newsPublishedAt, newsSource;
        ImageView newsImage;
        CardView cvNews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cvNews = itemView.findViewById(R.id.cvNews);
            newsTitle = itemView.findViewById(R.id.newTitle);
            newsPublishedAt = itemView.findViewById(R.id.newsPublishedAt);
            newsSource = itemView.findViewById(R.id.sourceName);
            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}
