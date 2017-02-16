package com.crcker.aimeizhi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crcker.aimeizhi.R;
import com.crcker.aimeizhi.bean.SetOfPicInfoBean;

import java.util.ArrayList;

/**
 * Created by Crcker on 15/02/2017.
 * 邮箱：635281462@qq.com
 */

public class SetOfPicAdapter extends RecyclerView.Adapter<SetOfPicAdapter.MyViewHolder> {
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private ArrayList<SetOfPicInfoBean> setOfPicInfoBeen;

    public SetOfPicAdapter(Context context, ArrayList<SetOfPicInfoBean> datas) {
        mContext = context;
        setOfPicInfoBeen = datas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.set_of_pic_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Glide.with(mContext).load(setOfPicInfoBeen.get(position).getPic_url()).into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getLayoutPosition();
                onItemClickListener.onItemLongClick(holder.itemView, position);
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return setOfPicInfoBeen.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_set_of_pic);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
