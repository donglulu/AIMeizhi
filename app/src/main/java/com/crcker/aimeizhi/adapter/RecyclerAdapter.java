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
import com.crcker.aimeizhi.bean.PicInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 22:31
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private ArrayList<PicInfoBean> picInfoBeen;

    public RecyclerAdapter(Context context, ArrayList<PicInfoBean> datas) {
        mContext = context;
        picInfoBeen = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.item_main, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(picInfoBeen.get(position).getPicTitle());

        Glide.with(mContext).load(picInfoBeen.get(position).getPicUrl())
                .into(holder.iv);
        holder.itemView.setTag(position);
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return picInfoBeen.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item);
            iv = (ImageView) view.findViewById(R.id.iv_item);
        }
    }


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
}