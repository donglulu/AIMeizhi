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
import android.widget.Toast;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.LableAdapter;
import com.crcker.aimeizhi.base.BaseFragment;
import com.crcker.aimeizhi.bean.LableInfoBean;
import com.crcker.aimeizhi.model.GetDataFromHtml;
import com.crcker.aimeizhi.view.DefaultActivity;

import java.util.ArrayList;

/**
 * Created by crcker
 * <p>
 * 分类
 */
public class LableFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private LableAdapter mAdapter;
    private GetDataFromHtml dataFromHtml;

    private ArrayList<LableInfoBean> lableInfoBeens;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 3));
        UpdataTask updataTask = new UpdataTask(getActivity());
        updataTask.execute();
        return v;
    }


    public static LableFragment getInstance() {
        LableFragment fra = new LableFragment();
        return fra;
    }


    class UpdataTask extends AsyncTask<Void, Integer, Integer> {
        private Context context;

        UpdataTask(Context context) {
            dataFromHtml = new GetDataFromHtml();
            this.context = context;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            Toast.makeText(context, "抓取数据", Toast.LENGTH_SHORT).show();
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            lableInfoBeens = dataFromHtml.getLableData();
            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {
            mAdapter = new LableAdapter(mRecyclerView.getContext(), lableInfoBeens);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new LableAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), DefaultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", lableInfoBeens.get(position).getTitle());
                    bundle.putString("url", lableInfoBeens.get(position).getUrl());
                    intent.putExtras(bundle);
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