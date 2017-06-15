package com.example.hjl.jgpushtest.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.Jsjv;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class JsTjAdapter extends BaseAdapter {
    private List<FdSuo> list;
    public JsTjAdapter() {


    }

public void setsetDateJsTjAdapter(List<FdSuo> list){
    this.list=list;
    notifyDataSetChanged();
}
    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jstj_list_item, null);
        FdSuo fdSuo = (FdSuo) getItem(i);
        TextView tv1 = (TextView) view.findViewById(R.id.jstj_tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.jstj_tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.jstj_tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.jstj_tv4);
        TextView tv5 = (TextView) view.findViewById(R.id.jstj_tv5);
        TextView tv6 = (TextView) view.findViewById(R.id.jstj_tv6);
        tv1.setText(fdSuo.getSuo_sbBH());
        tv2.setText(fdSuo.getSuo_haoma());
//        tv3.setText(fdSuo.getSuo_sbBH());
//        tv4.setText(fdSuo.getSuo_sbBH());
        tv5.setText(fdSuo.getSuo_cdTS());
        tv6.setText(fdSuo.getSuo_ztBJ());
        return view;
    }
}
