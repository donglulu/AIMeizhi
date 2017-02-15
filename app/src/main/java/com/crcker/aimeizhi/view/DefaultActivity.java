package com.crcker.aimeizhi.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.RecyclerAdapter;
import com.crcker.aimeizhi.base.BaseActivity;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.model.GetDataFromHtml;

import java.util.ArrayList;

public class DefaultActivity extends BaseActivity {
    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    private GetDataFromHtml dataFromHtml = new GetDataFromHtml();
    //当前页
    private int pages = 1;

    private ArrayList<PicInfoBean> picInfoBeen;
    //是否是第一次进入
    private boolean isFrist = true;
    //续加载的数据
    private Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        initView();
        initData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mBundle.getString("title"));
        setSupportActionBar(toolbar);

    }

    private void initData() {
        mBundle = getIntent().getExtras();
        update();
    }


    private void update() {
        picInfoBeen = new ArrayList<>();
        UpdataTask updateTextTask = new UpdataTask(this, pages);
        updateTextTask.execute();


    }


    class UpdataTask extends AsyncTask<Void, Integer, ArrayList<PicInfoBean>> {
        private Context context;


        UpdataTask(Context context, int pages) {
            dataFromHtml = new GetDataFromHtml();
            this.context = context;

        }


        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected ArrayList<PicInfoBean> doInBackground(Void... params) {

            return dataFromHtml.getHomeData(pages, isFrist, mBundle.getString("url"));
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(final ArrayList<PicInfoBean> been) {

            super.onPostExecute(picInfoBeen);


            picInfoBeen.addAll(been);

            Log.d("msg", picInfoBeen.size() + "个数");

            mAdapter = new RecyclerAdapter(mRecyclerView.getContext(), picInfoBeen);
            mRecyclerView.setAdapter(mAdapter);


            mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(DefaultActivity.this, SetOfPicActivity.class);
                    intent.putExtra("url", picInfoBeen.get(position).getUrl());
                    intent.putExtra("title", picInfoBeen.get(position).getPicTitle());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });


        }

    }


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_default);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

    }
}
