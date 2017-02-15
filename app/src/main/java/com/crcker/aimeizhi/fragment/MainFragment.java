package com.crcker.aimeizhi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.RecyclerAdapter;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.model.GetDataFromHtml;
import com.crcker.aimeizhi.view.SetOfPicActivity;
import com.crcker.aimeizhi.view.ShowBigPicActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 17:36
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

    private TwinklingRefreshLayout refreshLayout;

    public static MainFragment getInstance() {
        MainFragment fra = new MainFragment();
        return fra;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initView(v);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        final GridLayoutManager layoutManager = new GridLayoutManager(mRecyclerView.getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        update();


        return v;
    }

    private void initView(View v) {

        refreshLayout = (TwinklingRefreshLayout) v.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                update();
                pages++;
                isFrist = false;
            }
        });
    }


    private void update() {
        picInfoBeen = new ArrayList<>();
        UpdataTask updateTextTask = new UpdataTask(getActivity(), pages);
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

            return dataFromHtml.getHomeData(pages, isFrist);
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(final ArrayList<PicInfoBean> been) {

            super.onPostExecute(picInfoBeen);


            picInfoBeen.addAll(been);
            refreshLayout.finishLoadmore();
            Log.d("msg", picInfoBeen.size() + "个数");

            mAdapter = new RecyclerAdapter(mRecyclerView.getContext(), picInfoBeen);
            mRecyclerView.setAdapter(mAdapter);


            mAdapter.setOnItemClickListener(new RecyclerAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), SetOfPicActivity.class);
                    intent.putExtra("url", picInfoBeen.get(position).getUrl());
                    intent.putExtra("title", picInfoBeen.get(position).getPicTitle());
                    startActivity(intent);
                }
            });


        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

}