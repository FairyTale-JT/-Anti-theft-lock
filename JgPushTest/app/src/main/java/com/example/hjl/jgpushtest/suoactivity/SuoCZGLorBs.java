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

public class SuoCZGLorBs extends Fragment{
    public static SuoCZGLorBs getnewInstance_Bs(String param1){
        SuoCZGLorBs my=new SuoCZGLorBs();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return  my;

    }
    public static SuoCZGLorBs getnewInstance_Bs(){
        SuoCZGLorBs my=new SuoCZGLorBs();
        return  my;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (view == null) {
           view = inflater.inflate(R.layout.suo_bs, container, false);
        }

        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        return view;
    }
}
