package com.example.hjl.jgpushtest.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.bendiapi.CallbackActivity;
import com.example.hjl.jgpushtest.enity.CsLb;

import java.util.List;

/**
 * 补锁Adapter
 */

public class BSAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<CsLb> BSlist;
    private CallbackActivity bscallback;

    public BSAdapter(List<CsLb> BSlist, CallbackActivity bscallback) {
        this.BSlist = BSlist;
        this.bscallback = bscallback;
    }

    @Override
    public int getItemCount() {
        if (BSlist == null || BSlist.size() == 0) {
            return 0;
        } else {
            return BSlist.size();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (BSlist != null && BSlist.size() > 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bs_adapter_item, parent, false);
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
        if (BSlist != null && BSlist.size() > 0) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).suo_bs_ch.setText(BSlist.get(position).getCxh());
                ((MyViewHolder) holder).suo_bs_fz.setText(BSlist.get(position).getFz());
                ((MyViewHolder) holder).suo_bs_dz.setText(BSlist.get(position).getDz());
                ((MyViewHolder) holder).
                        suo_bs_suo1.setText(BSlist.get(position).getSuo1_id().toString());
                ((MyViewHolder) holder).
                        suo_bs_suo1.setOnClickListener(this);
                ((MyViewHolder) holder).
                        suo_bs_suo1.setTag(position);
                ((MyViewHolder) holder).suo_bs_zt1.setText(
                        BSlist.get(position).getSuo1_ztbj().toString());

                if (BSlist.get(position).getSuo2_id() != null) {
                    ((MyViewHolder) holder).
                            suo_bs_suo2.setText(BSlist.get(position).getSuo2_id().toString());
                    ((MyViewHolder) holder).
                            suo_bs_suo2.setOnClickListener(this);
                    ((MyViewHolder) holder).
                            suo_bs_suo2.setTag(position);
                    ((MyViewHolder) holder).suo_bs_zt2.setText(
                            BSlist.get(position).getSuo2_ztbj().toString());
                }
            }
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView suo_bs_ch;
        private TextView suo_bs_fz;
        private TextView suo_bs_dz;
        private TextView suo_bs_suo1;
        private TextView suo_bs_suo2;
        private TextView suo_bs_zt1;
        private TextView suo_bs_zt2;

        public MyViewHolder(View itemView) {
            super(itemView);
            suo_bs_ch = (TextView) itemView.findViewById(R.id.suo_bs_ch);
            suo_bs_fz = (TextView) itemView.findViewById(R.id.suo_bs_fz);
            suo_bs_dz = (TextView) itemView.findViewById(R.id.suo_bs_dz);
            suo_bs_suo1 = (TextView) itemView.findViewById(R.id.suo_bs_suo1);
            suo_bs_suo2 = (TextView) itemView.findViewById(R.id.suo_bs_suo2);
            suo_bs_zt1 = (TextView) itemView.findViewById(R.id.suo_bs_zt1);
            suo_bs_zt2 = (TextView) itemView.findViewById(R.id.suo_bs_zt2);

        }
    }

    class MyViewHolderTwo extends RecyclerView.ViewHolder {

        public MyViewHolderTwo(View itemView) {
            super(itemView);
        }
    }

    /**
     * 响应按钮点击事件,调用子定义接口，并传入View
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        bscallback.click(view);
    }
}
