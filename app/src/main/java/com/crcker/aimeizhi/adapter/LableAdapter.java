package com.crcker.aimeizhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.bean.LableInfoBean;
import com.crcker.aimeizhi.bean.PicInfoBean;

import java.util.ArrayList;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 22:31
 */

public class LableAdapter extends RecyclerView.Adapter<LableAdapter.MyViewHolder> {
    private Context mContext;

    private ArrayList<LableInfoBean> lableInfoBeen;

    public LableAdapter(Context context, ArrayList<LableInfoBean> datas) {
        mContext = context;
        lableInfoBeen = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.lable_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(lableInfoBeen.get(position).getTitle() + "(" + lableInfoBeen.get(position).getPicCount() + ")");

        Glide.with(mContext).load(lableInfoBeen.get(position).getLable_pic_url())
                .into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return lableInfoBeen.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_lable_item);
            iv = (ImageView) view.findViewById(R.id.iv_lable_item);
        }
    }
}