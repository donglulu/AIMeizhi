package com.crcker.aimeizhi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.RecyclerAdapter;
import com.crcker.aimeizhi.base.BaseFragment;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.constant.Constant;
import com.crcker.aimeizhi.model.GetDataFromHtml;
import com.crcker.aimeizhi.view.SetOfPicActivity;

import java.util.ArrayList;

/**
 * 随机
 */
public class RecommedFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    private GetDataFromHtml dataFromHtml;

    //当前页
    private int pages = 1;

    //是否是第一次进入
    private boolean isFrist = true;

    private ArrayList<PicInfoBean> picInfoBeen;

    private SwipeRefreshLayout mRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        inintView(v);
        update();
        return v;
    }

    private void inintView(View v) {

        mRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 2));


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });
    }


    private void update() {
        picInfoBeen = new ArrayList<>();
        UpdataTask updateTextTask = new UpdataTask(getActivity(), Constant.HOT_ADDRESS);
        updateTextTask.execute();


    }

    public static RecommedFragment getInstance() {
        RecommedFragment fra = new RecommedFragment();
        return fra;
    }

    class UpdataTask extends AsyncTask<Void, Integer, Integer> {
        private Context context;
        private String url;

        UpdataTask(Context context, String url) {
            dataFromHtml = new GetDataFromHtml();
            this.context = context;
            this.url = url;
        }


        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            picInfoBeen = dataFromHtml.getRandomData();
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {


            if (mRefreshLayout != null) {

                mRefreshLayout.setRefreshing(false);

            }

            mAdapter = new RecyclerAdapter(mRecyclerView.getContext(), picInfoBeen);
            mRecyclerView.setAdapter(mAdapter);
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

    }


}