package com.example.hjl.jgpushtest.suoactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjl.jgpushtest.R;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoWDfragment extends Fragment {
    public static SuoWDfragment getnewInstance_wd(String param1) {
        SuoWDfragment fragment = new SuoWDfragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
    public static SuoWDfragment getnewInstance_wd() {
        SuoWDfragment fragment = new SuoWDfragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.suo_maintv_wode, container, false);
        }

        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        return view;
    }
}

