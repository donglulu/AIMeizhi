package com.crcker.aimeizhi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * 热门分类
 */
public class HotFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private GetDataFromHtml dataFromHtml;

    //当前页
    private int pages = 1;

    private ArrayList<PicInfoBean> picInfoBeen;
    //是否是第一次进入
    private boolean isFrist = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 2));
        update();
        return v;
    }

    private void update() {
        picInfoBeen = new ArrayList<>();
        UpdataTask updateTextTask = new UpdataTask(getActivity(), Constant.HOT_ADDRESS);
        updateTextTask.execute();


    }

    public static HotFragment getInstance() {
        HotFragment fra = new HotFragment();
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
            picInfoBeen = dataFromHtml.getHomeData(pages, isFrist, url);
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
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