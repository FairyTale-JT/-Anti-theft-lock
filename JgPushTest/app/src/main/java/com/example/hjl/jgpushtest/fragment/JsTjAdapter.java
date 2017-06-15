package com.example.hjl.jgpushtest.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.Jsjv;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */

public class JsTjAdapter extends BaseAdapter {
    private List<Jsjv> list;
    private LayoutInflater layoutInflater;

    public JsTjAdapter(List<Jsjv> list, Context context) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);

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
        View view = layoutInflater.inflate(R.layout.jstj_list_item, null);
        Jsjv jsxx = (Jsjv) getItem(i);
        TextView tv1 = (TextView) view.findViewById(R.id.jstj_tv1);
        TextView tv2 = (TextView) view.findViewById(R.id.jstj_tv2);
        TextView tv3 = (TextView) view.findViewById(R.id.jstj_tv3);
        TextView tv4 = (TextView) view.findViewById(R.id.jstj_tv4);
        TextView tv5 = (TextView) view.findViewById(R.id.jstj_tv5);
        TextView tv6 = (TextView) view.findViewById(R.id.jstj_tv6);
        tv1.setText(jsxx.getJlh());
        tv2.setText(jsxx.getSuo1());
        tv3.setText(jsxx.getSbh());
        tv4.setText(jsxx.getCcpc());
        tv5.setText(jsxx.getCdts());
        tv6.setText(jsxx.getSzt());
        return view;
    }
}
