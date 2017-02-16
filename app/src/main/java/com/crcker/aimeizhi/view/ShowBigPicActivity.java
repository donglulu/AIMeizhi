package com.crcker.aimeizhi.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.view.ScaleVIew.ScaleView;

public class ShowBigPicActivity extends AppCompatActivity {

    private String url = "http://img.mmjpg.com/2016/557/1.jpg";
    private ScaleView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_big_pic);
        mImageView = (ScaleView) findViewById(R.id.iv_bigpic);

        Glide.with(this).load(getIntent().getStringExtra("url")).into(mImageView);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

}
