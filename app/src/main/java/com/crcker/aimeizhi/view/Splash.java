package com.crcker.aimeizhi.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

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


        if (isNetWorkAvailable() == true) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    enterHomeActivity();
                }
            }, 2000);

        } else {


            Toast.makeText(this, "请检查网络,即将退出", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    finish();
                }
            }, 2000);
        }


    }

    public boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
