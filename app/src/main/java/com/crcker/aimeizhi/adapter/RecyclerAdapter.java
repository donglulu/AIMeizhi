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
import com.crcker.aimeizhi.bean.PicInfoBean;

import java.util.ArrayList;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 22:31
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    private OnItemClickListener onItemClickListener;
    private ArrayList<PicInfoBean> picInfoBeen;


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public RecyclerAdapter(Context context, ArrayList<PicInfoBean> datas) {
        mContext = context;
        picInfoBeen = datas;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
            return new MyViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {


            View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent, false);
            return new FootViewHolder(view);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            //holder.tv.setText(data.get(position));
            if (onItemClickListener != null) {

                ((MyViewHolder) holder).tv.setText(picInfoBeen.get(position).getPicTitle());

                ((MyViewHolder) holder).cardView.setRadius(8);//设置图片圆角的半径大小

                ((MyViewHolder) holder).cardView.setCardElevation(8);//设置阴影部分大小

                ((MyViewHolder) holder).cardView.setContentPadding(5, 5, 5, 5);//设置图片距离阴影大小

                Glide.with(mContext).load(picInfoBeen.get(position).getPicUrl())
                        .into(((MyViewHolder) holder).iv);
                holder.itemView.setTag(position);

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
        }
    }

    @Override
    public int getItemViewType(int position) {


            return TYPE_ITEM;

    }


    @Override
    public int getItemCount() {
        return picInfoBeen.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_item);
            iv = (ImageView) view.findViewById(R.id.iv_item);
            cardView = (CardView) view.findViewById(R.id.cardView);

        }
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}