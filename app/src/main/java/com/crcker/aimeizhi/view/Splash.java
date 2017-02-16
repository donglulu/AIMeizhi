package com.crcker.aimeizhi.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crcker.aimeizhi.MainActivity;
import com.crcker.aimeizhi.R;


public class Splash extends Activity {
    private ImageView iv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = Splash.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);


        setContentView(R.layout.splash_activity);

        iv_splash = (ImageView) findViewById(R.id.iv_splash);

        Glide.with(this).load("http://img.mmjpg.com/2016/557/1.jpg").into(iv_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHomeActivity();
            }
        }, 2000);
    }


    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
