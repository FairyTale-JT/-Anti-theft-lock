package com.example.hjl.jgpushtest.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.enity.CsLb;

import java.util.List;

/**
 * 拆锁Adapter
 */

public class CSAdapter extends RecyclerView.Adapter {
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;//点击
    private List<CsLb> list;
//    public void setDateJiaSuoAdapter(List<Jsjv> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }

    public CSAdapter(List<CsLb> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (list != null && list.size() > 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cs_adapter, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tishi_no_date, parent, false);
            MyViewHolderTwo viewHolderTwo = new MyViewHolderTwo(view);
            return viewHolderTwo;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).suo_cs_ch.setText(list.get(position).getCxh());
                ((MyViewHolder) holder).suo_cs_fz.setText(list.get(position).getFz());
                ((MyViewHolder) holder).suo_cs_dz.setText(list.get(position).getDz());
                ((MyViewHolder) holder).
                        suo_cs_suo1.setText(list.get(position).getSuo1_id().toString());
                ((MyViewHolder) holder).suo_cs_zt1.setText(
                        list.get(position).getSuo1_ztbj().toString());

                if (list.get(position).getSuo2_id() != null) {
                    ((MyViewHolder) holder).
                            suo_cs_suo2.setText(list.get(position).getSuo2_id().toString());
                    ((MyViewHolder) holder).suo_cs_zt2.setText(
                            list.get(position).getSuo2_ztbj().toString());
                }
            }
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView suo_cs_ch;
        private TextView suo_cs_fz;
        private TextView suo_cs_dz;
        private TextView suo_cs_suo1;
        private TextView suo_cs_suo2;
        private TextView suo_cs_zt1;
        private TextView suo_cs_zt2;

        public MyViewHolder(View itemView) {
            super(itemView);
            suo_cs_ch = (TextView) itemView.findViewById(R.id.suo_cs_ch);
            suo_cs_fz = (TextView) itemView.findViewById(R.id.suo_cs_fz);
            suo_cs_dz = (TextView) itemView.findViewById(R.id.suo_cs_dz);
            suo_cs_suo1 = (TextView) itemView.findViewById(R.id.suo_cs_suo1);
            suo_cs_suo2 = (TextView) itemView.findViewById(R.id.suo_cs_suo2);
            suo_cs_zt1 = (TextView) itemView.findViewById(R.id.suo_cs_zt1);
            suo_cs_zt2 = (TextView) itemView.findViewById(R.id.suo_cs_zt2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

    class MyViewHolderTwo extends RecyclerView.ViewHolder {

        public MyViewHolderTwo(View itemView) {
            super(itemView);
        }
    }
}
