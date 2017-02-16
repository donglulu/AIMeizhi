package com.crcker.aimeizhi.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.bean.LableInfoBean;

import java.util.ArrayList;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 22:31
 */

public class LableAdapter extends RecyclerView.Adapter<LableAdapter.MyViewHolder> implements View.OnClickListener {
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private ArrayList<LableInfoBean> lableInfoBeen;

    public LableAdapter(Context context, ArrayList<LableInfoBean> datas) {
        mContext = context;
        lableInfoBeen = datas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lable_item, parent, false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv.setText(lableInfoBeen.get(position).getTitle() + "(" + lableInfoBeen.get(position).getPicCount() + ")");

        holder.cardView.setRadius(8);//设置图片圆角的半径大小

        holder.cardView.setCardElevation(8);//设置阴影部分大小

        holder.cardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小
        Glide.with(mContext).load(lableInfoBeen.get(position).getLable_pic_url())
                .into(holder.iv);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {

        return lableInfoBeen.size();
    }

    @Override
    public void onClick(View view) {

        if (mOnItemClickListener != null) {

            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_lable_item);
            iv = (ImageView) view.findViewById(R.id.iv_lable_item);
            cardView = (CardView) view.findViewById(R.id.cardView);
        }
    }

    public static interface OnRecyclerViewItemClickListener {

        void onItemClick(View view, int position);
    }
}