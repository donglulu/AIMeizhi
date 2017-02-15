package com.crcker.aimeizhi.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.RecyclerAdapter;
import com.crcker.aimeizhi.base.BaseFragment;
import com.crcker.aimeizhi.bean.PicInfoBean;
import com.crcker.aimeizhi.model.GetDataFromHtml;

import java.util.ArrayList;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 17:36
 */
public class HotFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private GetDataFromHtml dataFromHtml;
    private static final String ARG_TITLE = "title";
    private String mTitle;

    private ArrayList<PicInfoBean> picInfoBeen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 2));
        return v;
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
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            Toast.makeText(context, "开始执行", Toast.LENGTH_SHORT).show();
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
           // picInfoBeen = dataFromHtml.getHomeData();
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
            mAdapter = new RecyclerAdapter(mRecyclerView.getContext(), picInfoBeen);
            mRecyclerView.setAdapter(mAdapter);
         mAdapter.setOnItemClickListener(new RecyclerAdapter.OnRecyclerViewItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {
                 Toast.makeText(getActivity(), position+"dd", Toast.LENGTH_LONG).show();
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