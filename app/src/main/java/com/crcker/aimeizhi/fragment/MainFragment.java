package com.crcker.aimeizhi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.RecyclerAdapter;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.constant.Constant;
import com.crcker.aimeizhi.model.GetDataFromHtml;
import com.crcker.aimeizhi.view.SetOfPicActivity;

import java.util.ArrayList;

/**
 * 首页
 */
public class MainFragment extends Fragment {
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
                    Intent intent = new Intent(getActivity(), SetOfPicActivity.class);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initView(v);
        new MyThread().start();
        return v;
    }

    private void initView(View v) {
        picInfoBeen = new ArrayList<>();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        final GridLayoutManager layoutManager = new GridLayoutManager(mRecyclerView.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);


        mRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout);

        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new MyThread().start();

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
                    new MyThread().start();
                }
            }
        });


    }


    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (isFrist == true) {

                picInfoBeen.addAll(dataFromHtml.getHomeData(pages, isFrist, Constant.SERVER_ADDRESS));
                handler.sendEmptyMessage(0);
            } else {
                isLoading = false;

                picInfoBeen.addAll(dataFromHtml.getHomeData(pages, isFrist, Constant.PAGE_ADDRSS));

                handler.sendEmptyMessage(0);
            }


        }
    }

    public static MainFragment getInstance() {
        MainFragment fra = new MainFragment();
        return fra;
    }


}