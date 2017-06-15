package com.example.hjl.jgpushtest.suoactivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.fragment.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoCZGLfragment extends Fragment{
    String[] st={"加锁","解锁","强拆","补锁"};
    public static SuoCZGLfragment getnewInstance_czgl(String param1){
        SuoCZGLfragment suoCZGLfragment=new SuoCZGLfragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        suoCZGLfragment.setArguments(args);
        return  suoCZGLfragment;
    }
    public static SuoCZGLfragment getnewInstance_czgl(){
        SuoCZGLfragment suoCZGLfragment=new SuoCZGLfragment();
        return  suoCZGLfragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view =null;
        if (view == null) {
            view = inflater.inflate(R.layout.caozuoguanl_view, container, false);
        }
        Bundle bundle = getArguments();
       String agrs1 = bundle.getString("agrs1");
        ViewPager viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        TabLayout tb= (TabLayout) view.findViewById(R.id.tab_viewpager);
        List<Fragment> list=new ArrayList<>();
        list.add(SuoCZGLorJs.getnewInstance_Js("JS"));
        list.add(SuoCZGLorCs.getnewInstance_Cs("CS"));
        list.add(SuoCZGLorBs.getnewInstance_Bs("BS"));
        list.add(SuoCZGLorQc.getnewInstance_Qc("QC"));
        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(),list));
        viewPager.setCurrentItem(0);
        tb.setupWithViewPager(viewPager);
        for (int i = 0; i <4 ; i++) {
            tb.getTabAt(i).setText(st[i]);
        }
        tb.getTabAt(0).select();
        return view;
    }
}
