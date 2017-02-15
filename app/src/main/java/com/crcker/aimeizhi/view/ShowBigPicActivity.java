package com.crcker.aimeizhi.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crcker.aimeizhi.R;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;
import com.github.piasy.biv.view.BigImageView;

public class ShowBigPicActivity extends AppCompatActivity {

    private String url = "http://img.mmjpg.com/2016/557/1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BigImageViewer.initialize(FrescoImageLoader.with(ShowBigPicActivity.this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_pic);


        BigImageView bigImageView = (BigImageView) findViewById(R.id.mBigImage);

        bigImageView.showImage(
                Uri.parse(url),
                Uri.parse(url)
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
