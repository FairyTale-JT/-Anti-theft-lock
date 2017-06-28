package com.example.hjl.jgpushtest.fragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    private int arg=-1;

    public JsTjAdapter() {


    }
public void setArg(int arg1){
//    this.arg=arg1;
//    notifyDataSetChanged();
    if(arg1 != arg){
        arg = arg1;
        notifyDataSetChanged();
    }
}
    public void setsetDateJsTjAdapter(List<FdSuo> list) {
        this.list = list;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        FdSuo fdSuo = (FdSuo) getItem(i);
        Util util = null;
        if (view == null) {
            util = new Util();
            view = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.jstj_list_item, null);
            util.tv1 = (TextView) view.findViewById(R.id.jstj_tv1);
            util.tv2 = (TextView) view.findViewById(R.id.jstj_tv2);
            util.tv3 = (TextView) view.findViewById(R.id.jstj_tv3);
            util.tv4 = (TextView) view.findViewById(R.id.jstj_tv4);
            util.tv5 = (TextView) view.findViewById(R.id.jstj_tv5);
            util.tv6 = (TextView) view.findViewById(R.id.jstj_tv6);
            view.setTag(util);
        }else {
            util = (Util) view.getTag();
        }
        if (fdSuo.getSuo_isuse() >0) {
            util.tv1.setText("待用");
        }else {
            util.tv1.setText("已编辑");

        }
        util.tv2.setText(fdSuo.getSuo_haoma());
//        util.tv3.setText(fdSuo.getSuo_sbBH());
//        tv4.setText(fdSuo.getSuo_sbBH());
        util.tv5.setText(Integer.toString(i + 1));
        util.tv6.setText(fdSuo.getSuo_ztBJ());
        if (arg==i) {
            util.tv5.setTextColor(viewGroup.getContext().getResources().getColor(R.color.bt1_blue));
            util.tv6.setTextColor(viewGroup.getContext().getResources().getColor(R.color.bt1_blue));
            util.tv1.setTextColor(viewGroup.getContext().getResources().getColor(R.color.bt1_blue));
            util.tv2.setTextColor(viewGroup.getContext().getResources().getColor(R.color.bt1_blue));

            Log.e("TAG",arg+"");
           arg=-1;
            Log.e("TAG",arg+"");
        }else {
            util.tv5.setTextColor(viewGroup.getContext().getResources().getColor(R.color.tv_black));
            util.tv6.setTextColor(viewGroup.getContext().getResources().getColor(R.color.tv_black));
            util.tv1.setTextColor(viewGroup.getContext().getResources().getColor(R.color.tv_black));
            util.tv2.setTextColor(viewGroup.getContext().getResources().getColor(R.color.tv_black));
        }
        return view;
    }
   static class Util {

        TextView tv1, tv2, tv3,tv4,tv5,tv6;


    }
}
