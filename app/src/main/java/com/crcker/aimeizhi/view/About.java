package com.crcker.aimeizhi.view;

import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.base.BaseActivity;

public class About extends BaseActivity {
    private TextView tv_qq, tv_weibo, tv_github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void initView() {
        tv_weibo = (TextView) findViewById(R.id.tv_weibo);

        tv_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://weibo.com/52doser");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        tv_github = (TextView) findViewById(R.id.tv_github);
        tv_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/donglulu/AIMeizhi");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        tv_qq = (TextView) findViewById(R.id.tv_qq);

        tv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tv_qq.getText());

                String url="mqqwpa://im/chat?chat_type=wpa&uin=635281462";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                 Toast.makeText(About.this, "已复制号码", Toast.LENGTH_LONG).show();
            }
        });
    }

}
