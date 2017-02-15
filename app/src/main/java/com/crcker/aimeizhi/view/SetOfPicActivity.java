package com.crcker.aimeizhi.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.adapter.SetOfPicAdapter;
import com.crcker.aimeizhi.base.BaseActivity;
import com.crcker.aimeizhi.bean.SetOfPicInfoBean;
import com.crcker.aimeizhi.model.GetDataFromHtml;

import java.util.ArrayList;


/*
* 套图查看
* */
public class SetOfPicActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<SetOfPicInfoBean> setOfPicInfoBeen;
    private SetOfPicAdapter setOfPicAdapter;
    private GetDataFromHtml getDataFromHtml;
    //默认
    private String url = "http://www.mmjpg.com/mm/886";
    private String title = "套图";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_of_pic);
        initView();
        initData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);


    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        MyTask task = new MyTask();
        task.execute();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rl_set_of_pic);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 1));
        setOfPicInfoBeen = new ArrayList<>();
        getDataFromHtml = new GetDataFromHtml();
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

            setOfPicAdapter.setOnItemClickListener(new SetOfPicAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent= new Intent(SetOfPicActivity.this,ShowBigPicActivity.class);
                    intent.putExtra("url",setOfPicInfoBeen.get(position).getPic_url());
                    startActivity(intent);

                }
            });

            super.onPostExecute(integer);


        }
    }

}
