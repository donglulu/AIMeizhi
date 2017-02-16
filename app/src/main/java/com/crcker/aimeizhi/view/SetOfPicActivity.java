package com.crcker.aimeizhi.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.SetOfPicAdapter;
import com.crcker.aimeizhi.base.BaseActivity;
import com.crcker.aimeizhi.bean.SetOfPicInfoBean;
import com.crcker.aimeizhi.model.GetDataFromHtml;
import com.crcker.aimeizhi.utils.DownloadUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/*
* 套图查看
* */
public class SetOfPicActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ArrayList<SetOfPicInfoBean> setOfPicInfoBeen;
    private SetOfPicAdapter setOfPicAdapter;
    private GetDataFromHtml getDataFromHtml;
    //默认
    private String url = "http://www.mmjpg.com/mm/886";
    private String title = "套图";

    private TextView tv_title;

    private ImageView iv_down;


    //文件保存位置
    String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/爱妹纸";
    //下载器
    private DownloadUtils downloadUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_of_pic);
        initView();
        initData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        tv_title.setText(title);
        MyTask task = new MyTask();
        task.execute();
    }

    private void initView() {
        iv_down = (ImageView) findViewById(R.id.iv_download_more);
        iv_down.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_set_of_pic);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 1));
        setOfPicInfoBeen = new ArrayList<>();
        getDataFromHtml = new GetDataFromHtml();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_download_more:

                showDialog("是否下载全部" + "(" + setOfPicInfoBeen.size() + "条)", "保存位置：sdcard/爱妹纸", -1);

                break;

        }


    }

    class MyTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            setOfPicInfoBeen = getDataFromHtml.getSetPic(url);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            setOfPicAdapter = new SetOfPicAdapter(SetOfPicActivity.this, setOfPicInfoBeen);

            mRecyclerView.setAdapter(setOfPicAdapter);

            setOfPicAdapter.setOnItemClickListener(new SetOfPicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(SetOfPicActivity.this, ShowBigPicActivity.class);
                    intent.putExtra("url", setOfPicInfoBeen.get(position).getPic_url());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {


                    showDialog("是否保存？", "保存位置：sdcard/爱妹纸", position);
                }
            });

            super.onPostExecute(integer);


        }
    }


    //检查权限
    public void checkpermission() {
        if (ContextCompat.checkSelfPermission(SetOfPicActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SetOfPicActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        } else {
            mkDir();
        }
    }


    //创建文件夹
    public void mkDir() {
        File sdcardDir = Environment.getExternalStorageDirectory();
        //得到一个路径，内容是sdcard的文件夹路径和名字
        String path = sdcardDir.getPath() + "/爱妹纸";
        File path1 = new File(path);
        if (!path1.exists()) {
            //若不存在，创建目录，可以在应用启动的时候创建
            path1.mkdirs();

        }
    }

    //批量下载
    public void downMore() {

        new MyThread().start();
    }


    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();

            for (int i = 0; i < setOfPicInfoBeen.size(); i++) {

                try {
                    Thread.sleep(200);
                    startDownload(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }
    }


    public void startDownload(int position) {
        String url = setOfPicInfoBeen.get(position).getPic_url();
        int start = url.lastIndexOf("/");
        String fileName = url.substring(start + 1, url.length());

        downloadUtils = new DownloadUtils(dir, title + fileName);

        downloadUtils.downloadImg(SetOfPicActivity.this, setOfPicInfoBeen.get(position).getPic_url());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mkDir();
            } else {

                Toast.makeText(SetOfPicActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showDialog(String titles, String message, final int index) {


        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(titles);
        builder.setMessage(message);
        builder.setNeutralButton("不再提醒", null);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkpermission();
                if (index == -1) {
                    Toast.makeText(SetOfPicActivity.this, "添加任务", Toast.LENGTH_SHORT).show();
                    downMore();
                    Toast.makeText(SetOfPicActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    startDownload(index);
                    Toast.makeText(SetOfPicActivity.this, "已保存", Toast.LENGTH_SHORT).show();
                }


            }
        });
        builder.show();
    }
}
