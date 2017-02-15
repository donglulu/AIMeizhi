package com.crcker.aimeizhi.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.base.BaseActivity;
import com.crcker.aimeizhi.view.ScaleVIew.ScaleView;
import com.squareup.picasso.Picasso;

public class ShowBigPicActivity extends BaseActivity {

    private String url = "http://img.mmjpg.com/2016/557/1.jpg";
    private ScaleView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_pic);
        mImageView = (ScaleView) findViewById(R.id.iv_bigpic);

        Picasso.with(this).load(getIntent().getStringExtra("url")).into(mImageView);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("大图");
        setSupportActionBar(toolbar);

    }

}
