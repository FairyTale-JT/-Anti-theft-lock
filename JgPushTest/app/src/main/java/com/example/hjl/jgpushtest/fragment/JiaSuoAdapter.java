package com.example.hjl.jgpushtest.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewLongItemClickListener;
import com.example.hjl.jgpushtest.enity.Jsjv;

import java.util.List;

/**
 * 加锁记录
 */

public class JiaSuoAdapter extends RecyclerView.Adapter {
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;//点击
    public OnRecyclerViewLongItemClickListener mOnLongItemClickListener = null;//长按
    private List<Jsjv> list;
    public void setDateJiaSuoAdapter(List<Jsjv> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public JiaSuoAdapter() {
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongItemClickListener(OnRecyclerViewLongItemClickListener listener) {
        this.mOnLongItemClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (list!=null&&list.size()>0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.js_list_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tishi_no_date, parent, false);
            MyViewHolderTwo viewHolderTwo=new MyViewHolderTwo(view);
            return viewHolderTwo;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list!=null&&list.size()>0) {
            if (holder instanceof  MyViewHolder) {
                ((MyViewHolder) holder).js_tv1.setText(list.get(position).getCxh());
                ((MyViewHolder) holder).js_tv2.setText(list.get(position).getFz());
                ((MyViewHolder) holder).js_tv3.setText(list.get(position).getDz());
                ((MyViewHolder) holder).js_tv4.setText(list.get(position).getSuo1());
                ((MyViewHolder) holder).js_tv5.setText(list.get(position).getSuo2());
                ((MyViewHolder) holder).js_tv6.setText(list.get(position).getSzt());
            }
        }

    }

    @Override
    public int getItemCount() {
        if (list==null||list.size()==0) {
            return 1;
        }else {
            return list.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView js_tv1;
        private TextView js_tv2;
        private TextView js_tv3;
        private TextView js_tv4;
        private TextView js_tv5;
        private TextView js_tv6;

        public MyViewHolder(final View itemView) {
            super(itemView);
            js_tv1 = (TextView) itemView.findViewById(R.id.js_list_tv1);
            js_tv2 = (TextView) itemView.findViewById(R.id.js_list_tv2);
            js_tv3 = (TextView) itemView.findViewById(R.id.js_list_tv3);
            js_tv4 = (TextView) itemView.findViewById(R.id.js_list_tv4);
            js_tv5 = (TextView) itemView.findViewById(R.id.js_list_tv5);
            js_tv6 = (TextView) itemView.findViewById(R.id.js_list_tv6);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mOnLongItemClickListener != null) {
                        mOnLongItemClickListener.onLongItemClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
    class MyViewHolderTwo extends RecyclerView.ViewHolder{

        public MyViewHolderTwo(View itemView) {
            super(itemView);
        }
    }
}
