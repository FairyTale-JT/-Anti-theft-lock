package com.example.hjl.jgpushtest.suoactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjl.jgpushtest.R;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoTZCXfragment extends Fragment{
    public static SuoTZCXfragment getnewInstance_tzcx(String param1){
        SuoTZCXfragment suoTZCXfragment=new SuoTZCXfragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        suoTZCXfragment.setArguments(args);
        return suoTZCXfragment;
    }
    public static SuoTZCXfragment getnewInstance_tzcx(){
        SuoTZCXfragment suoTZCXfragment=new SuoTZCXfragment();
        return suoTZCXfragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.suo_maintv_tzcx, container, false);
        }

        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        return view;
    }
}
