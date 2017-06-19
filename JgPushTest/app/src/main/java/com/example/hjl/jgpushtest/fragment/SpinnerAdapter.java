package com.example.hjl.jgpushtest.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.FaZhan;

import java.util.List;

/**
 * Spinner Adapter
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<FaZhan> fa_list;
    private Context context;

    public SpinnerAdapter(Context context, List<FaZhan> fa_list) {
        this.fa_list = fa_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fa_list == null ? 0 : fa_list.size();
    }

    @Override
    public Object getItem(int i) {
        return fa_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        view = Inflater.inflate(R.layout.spinner_adapter_item, null);
        if (view != null) {
            TextView spinner_fz = (TextView) view.findViewById(R.id.spinner_fz);
            spinner_fz.setText(fa_list.get(i).getFz());
        }
        return view;
    }
}
