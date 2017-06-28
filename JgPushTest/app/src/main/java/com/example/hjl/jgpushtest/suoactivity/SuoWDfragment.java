package com.example.hjl.jgpushtest.suoactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.TestACT;
import com.example.hjl.jgpushtest.beanClass.HttpFaZhan;
import com.example.hjl.jgpushtest.enity.FaZhan;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.NowUser;
import com.example.hjl.jgpushtest.http.ApiService;
import com.example.hjl.jgpushtest.http.HttpUtils;
import com.example.hjl.jgpushtest.http.Url;
import com.example.hjl.jgpushtest.myview.CustomDialog;
import com.example.hjl.jgpushtest.util.SharePreferencesHelper;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoWDfragment extends Fragment {
    @Bind(R.id.quanxan_get)
    Button hqQx;
    @Bind(R.id.zm_get)
    Button zMget;

    @Bind(R.id.zm_1)
    TextView zm_1;
    @Bind(R.id.zm_2)
    TextView zm_2;
    @Bind(R.id.zm_3)
    TextView zm_3;
    @Bind(R.id.zm_4)
    TextView zm_4;

    @Bind(R.id.quanxian_jiasuo)
    TextView qx_js;
    @Bind(R.id.quanxian_caisuo)
    TextView qx_cs;
    @Bind(R.id.quanxian_busuo)
    TextView qx_bs;
    @Bind(R.id.quanxian_qiangc)
    TextView qx_qc;

    @Bind(R.id.all_tuichu)
    Button tuichu;


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
    @Override public void onDestroyView() {
        super.onDestroyView();

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
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        initview();

      zMget.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getUserZM();
          }
      });//获取站名
        hqQx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserQS();
            }
        });//获取权限
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void initview() {
        List<FaZhan> li =   DataSupport.where("user = ?",NowUser.getuser(getContext())).find(FaZhan.class);

        if (li!=null&&li.size()>0) {
            if (li.size() == 1) {
                zm_1.setText("发站1："+li.get(0).getFz().toString());
            }
            if (li.size() == 2) {
                zm_1.setText("发站1："+li.get(0).getFz().toString());
                zm_2.setText("发站2："+li.get(1).getFz().toString());
            }
            if (li.size() == 3) {
                zm_1.setText("发站1："+li.get(0).getFz().toString());
                zm_2.setText("发站2："+li.get(1).getFz().toString());
                zm_3.setText("发站2："+li.get(2).getFz().toString());
            }
            if (li.size() == 4) {
                zm_1.setText("发站1："+li.get(0).getFz().toString());
                zm_2.setText("发站2："+li.get(1).getFz().toString());
                zm_3.setText("发站2："+li.get(2).getFz().toString());
                zm_4.setText("发站2："+li.get(3).getFz().toString());
            }
        }

      if(  SharePreferencesHelper.getInstance(getContext()).getString("加锁权限")!=null
              &&SharePreferencesHelper.getInstance(getContext()).getString("加锁权限").equals("10"))
          {
              qx_js.setTextColor(getContext().getResources().getColor(R.color.my));
      }else {
          qx_js.setTextColor(getContext().getResources().getColor(R.color.dark));
      }

        if(  SharePreferencesHelper.getInstance(getContext()).getString("拆锁权限")!=null
                &&SharePreferencesHelper.getInstance(getContext()).getString("拆锁权限").equals("11"))
        {
            qx_cs.setTextColor(getContext().getResources().getColor(R.color.my));
        }else {
            qx_cs.setTextColor(getContext().getResources().getColor(R.color.dark));
        }

        if(  SharePreferencesHelper.getInstance(getContext()).getString("补锁权限")!=null
                &&SharePreferencesHelper.getInstance(getContext()).getString("补锁权限").equals("12"))
        {
            qx_bs.setTextColor(getContext().getResources().getColor(R.color.my));
        }else {
            qx_bs.setTextColor(getContext().getResources().getColor(R.color.dark));
        }

        if(  SharePreferencesHelper.getInstance(getContext()).getString("强拆权限")!=null
                &&SharePreferencesHelper.getInstance(getContext()).getString("强拆权限").equals("13"))
        {
            qx_qc.setTextColor(getContext().getResources().getColor(R.color.my));
        }else {
            qx_qc.setTextColor(getContext().getResources().getColor(R.color.dark));
        }
    }

    private void getUserQS() {
        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);
        HttpUtils.getMy_Retrofit(Url.FDS_URL_MY,getContext())
                .create(ApiService.class)
                .queryPerm(NowUser.getuser(getContext()),NowUser.getToken(getContext()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        customDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String[]>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","权限连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","权限错误");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(String[] s) {
                        qx_dowhat(s);//获取s进行的操作
                        Log.e("TAG","权限成功:"+s.toString());

                    }
                });
    }

    private void qx_dowhat(String[] s) {
        if (useLoop(s,"10")) {
            SharePreferencesHelper.getInstance(getContext()).putString("加锁权限","10");
            qx_js.setTextColor(getContext().getResources().getColor(R.color.my));
        }  else {
            qx_js.setTextColor(getContext().getResources().getColor(R.color.dark));
        }
        if (useLoop(s,"11")) {
            SharePreferencesHelper.getInstance(getContext()).putString("拆锁权限","11");
            qx_cs.setTextColor(getContext().getResources().getColor(R.color.my));
        }  else {
            qx_cs.setTextColor(getContext().getResources().getColor(R.color.dark));
        }
        if (useLoop(s,"12")) {
            SharePreferencesHelper.getInstance(getContext()).putString("补锁权限","12");
            qx_bs.setTextColor(getContext().getResources().getColor(R.color.my));
        }  else {
            qx_bs.setTextColor(getContext().getResources().getColor(R.color.dark));
        }

        if (useLoop(s,"13")) {
            SharePreferencesHelper.getInstance(getContext()).putString("强拆权限","13");
            qx_qc.setTextColor(getContext().getResources().getColor(R.color.my));
        }  else {
            qx_qc.setTextColor(getContext().getResources().getColor(R.color.dark));
        }

    }
    public static boolean useLoop(String[] arr, String targetValue) {
        for(String s: arr){
            if(s.equals(targetValue))
                return true;
        }
        return false;
    }
    private void getUserZM() {
        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);
        HttpUtils.getMy_Retrofit(Url.FDS_URL_MY,getContext())
                .create(ApiService.class)
                .queryRelativeStation(NowUser.getuser(getContext()),NowUser.getToken(getContext()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        customDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HttpFaZhan>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","站名连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","站名错误");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(List<HttpFaZhan> s) {

                        if (s.size()>0 ) {
                             DataSupport.deleteAll(FaZhan.class);
                            for (int i = 0; i <s.size() ; i++) {
                                FaZhan faz=new FaZhan();
                                faz.setUser(NowUser.getuser(getContext()));
                                faz.setFz(s.get(i).getName().toString());
                                faz.setFzDBM(s.get(i).getId().toString());
                                faz.save();
                            }
                        }
                        dowhat(s);//获取s进行的操作
                        Log.e("TAG","站名成功:"+s.get(0).getName()+s.get(0).getId());

                    }
                });
    }

    private void dowhat(List<HttpFaZhan> s) {
        if (s.size() == 1) {
            zm_1.setText("发站1："+s.get(0).getName().toString());
        }
        if (s.size() == 2) {
            zm_1.setText("发站1："+s.get(0).getName().toString());
            zm_2.setText("发站2："+s.get(1).getName().toString());
        }
        if (s.size() == 3) {
            zm_1.setText("发站1："+s.get(0).getName().toString());
            zm_2.setText("发站2："+s.get(1).getName().toString());
            zm_3.setText("发站2："+s.get(2).getName().toString());
        }
        if (s.size() == 4) {
            zm_1.setText("发站1："+s.get(0).getName().toString());
            zm_2.setText("发站2："+s.get(1).getName().toString());
            zm_3.setText("发站2："+s.get(2).getName().toString());
            zm_4.setText("发站2："+s.get(3).getName().toString());
        }
    }
}

