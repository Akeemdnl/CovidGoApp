package com.example.covidgofyp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.covidgofyp.R;

public class NewsDetail extends AppCompatActivity {

    TextView newTitle,sourceName,newsPublishedAt,newsPublishedAtDesc;
    ImageView imageView;
    WebView webView;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newTitle = findViewById(R.id.newTitle);
        sourceName = findViewById(R.id.sourceName);
        newsPublishedAt = findViewById(R.id.newsPublishedAt);
        newsPublishedAtDesc = findViewById(R.id.newsPublishedAtDesc);

        imageView = findViewById(R.id.newsImage);

        webView= findViewById(R.id.webView);
        loader= findViewById(R.id.loaderBrowser);
        loader.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        newTitle.setText(title);
        sourceName.setText(source);
        newsPublishedAt.setText(time);
        newsPublishedAtDesc.setText(desc);

        Glide.with(this).load(imageUrl).into(imageView);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        if (webView.isShown()){
            loader.setVisibility(View.INVISIBLE);
        }
        loader.setVisibility(View.VISIBLE);

    }
}