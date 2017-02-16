package com.crcker.aimeizhi.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.RecyclerAdapter;
import com.crcker.aimeizhi.base.BaseActivity;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.constant.Constant;
import com.crcker.aimeizhi.fragment.MainFragment;
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

    boolean isLoading;
    private SwipeRefreshLayout mRefreshLayout;

    //续加载的数据
    private Bundle mBundle;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (mAdapter == null) {

                mAdapter = new RecyclerAdapter(mRecyclerView.getContext(), picInfoBeen);
                mRecyclerView.setAdapter(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());


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
    };

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
        picInfoBeen = new ArrayList<>();
        mBundle = getIntent().getExtras();
        new MyThread(mBundle.getString("url")).start();
    }


    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_default);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {


                    boolean isRefreshing = mRefreshLayout.isRefreshing();

                    if (isRefreshing) {

                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    pages++;
                    isLoading = true;
                    isFrist = false;

                    if (pages <= Integer.parseInt(picInfoBeen.get(0).getPages())) {
                        Toast.makeText(DefaultActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
                        new MyThread(mBundle.getString("url")).start();
                    }



                }
            }
        });


    }


    class MyThread extends Thread {
        String url;

        public MyThread(String url) {
            this.url = url;
        }


        @Override
        public void run() {
            super.run();
            if (isFrist == true) {

                picInfoBeen.addAll(dataFromHtml.getHomeData(pages, isFrist, url));
                handler.sendEmptyMessage(0);
            } else {
                isLoading = false;

                picInfoBeen.addAll(dataFromHtml.getHomeData(pages, isFrist, url));

                handler.sendEmptyMessage(0);
            }


        }
    }
}
