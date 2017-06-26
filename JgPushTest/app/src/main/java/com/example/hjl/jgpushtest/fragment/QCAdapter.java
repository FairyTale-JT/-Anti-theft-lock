package com.example.hjl.jgpushtest.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.CsLb;
import java.util.List;

/**
 * 强拆
 */

public class QCAdapter extends RecyclerView.Adapter {
    private List<CsLb> list;
    private Context context;
//    public void setDateJiaSuoAdapter(List<Jsjv> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }

    public QCAdapter(List<CsLb> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (list != null && list.size() > 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qc_adapter_item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tishi_no_date, parent, false);
            MyViewHolderTwo viewHolderTwo = new MyViewHolderTwo(view);
            return viewHolderTwo;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (list != null && list.size() > 0) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).suo_qc_ch.setText(list.get(position).getCxh());
                ((MyViewHolder) holder).suo_qc_fz.setText(list.get(position).getFz());
                ((MyViewHolder) holder).suo_qc_dz.setText(list.get(position).getDz());
                ((MyViewHolder) holder).
                        suo_qc_suo1.setText(list.get(position).getSuo1_id().toString());
                ((MyViewHolder) holder).suo_qc_suo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QCialog(list.get(position).getSuo1_id());
                    }
                });
                ((MyViewHolder) holder).suo_qc_zt1.setText(
                        list.get(position).getSuo1_ztbj().toString());
                ((MyViewHolder) holder).suo_qc_zt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QCialog(list.get(position).getSuo1_id());
                    }
                });

                if (list.get(position).getSuo2_id() != null) {
                    ((MyViewHolder) holder).
                            suo_qc_suo2.setText(list.get(position).getSuo2_id().toString());
                    ((MyViewHolder) holder).suo_qc_suo2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            QCialog(list.get(position).getSuo2_id());
                        }
                    });
                    ((MyViewHolder) holder).suo_qc_zt2.setText(
                            list.get(position).getSuo2_ztbj().toString());
                    ((MyViewHolder) holder).suo_qc_zt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            QCialog(list.get(position).getSuo2_id());
                        }
                    });

                }
            }

        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView suo_qc_ch;
        private TextView suo_qc_fz;
        private TextView suo_qc_dz;
        private TextView suo_qc_suo1;
        private TextView suo_qc_suo2;
        private TextView suo_qc_zt1;
        private TextView suo_qc_zt2;

        public MyViewHolder(View itemView) {
            super(itemView);
            suo_qc_ch = (TextView) itemView.findViewById(R.id.suo_qc_ch);
            suo_qc_fz = (TextView) itemView.findViewById(R.id.suo_qc_fz);
            suo_qc_dz = (TextView) itemView.findViewById(R.id.suo_qc_dz);
            suo_qc_suo1 = (TextView) itemView.findViewById(R.id.suo_qc_suo1);

            suo_qc_suo2 = (TextView) itemView.findViewById(R.id.suo_qc_suo2);
            suo_qc_zt1 = (TextView) itemView.findViewById(R.id.suo_qc_zt1);
            suo_qc_zt2 = (TextView) itemView.findViewById(R.id.suo_qc_zt2);

        }
    }

    class MyViewHolderTwo extends RecyclerView.ViewHolder {

        public MyViewHolderTwo(View itemView) {
            super(itemView);
        }
    }


    /**
     * 强拆拆锁对话框
     *
     * @param cxh
     */
    private void QCialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("车号/箱号为" + cxh + "强制拆锁?");
        builder.setTitle("提示");
        builder.setPositiveButton("加锁确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * do what
                 */
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.show().dismiss();
            }
        });
        builder.create().show();

    }
}
