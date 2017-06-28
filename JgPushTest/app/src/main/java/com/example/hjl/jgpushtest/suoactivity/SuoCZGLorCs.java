package com.example.hjl.jgpushtest.suoactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.beanClass.HttpJsjv;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.enity.CsLb;
import com.example.hjl.jgpushtest.enity.NowUser;
import com.example.hjl.jgpushtest.fragment.CSAdapter;
import com.example.hjl.jgpushtest.http.ApiService;
import com.example.hjl.jgpushtest.http.HttpUtils;
import com.example.hjl.jgpushtest.http.Url;
import com.example.hjl.jgpushtest.myview.CustomDialog;
import com.example.hjl.jgpushtest.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 拆锁界面.
 */

public class SuoCZGLorCs extends Fragment {
     @Bind(R.id.re_csuo)
     RecyclerView re_csuo;

    @Bind(R.id.haomachaxun)
    Button haomaCx;

    @Bind(R.id.chezhanchaxun)
    Button fazhanCx;

    @Bind(R.id.chaxunhaoma)
    EditText chaxunk;

    private List<HttpJsjv> cslist;
    private CSAdapter csAdapter;

    private List<Subscription> subscriptions = new ArrayList<>();

    public static SuoCZGLorCs getnewInstance_Cs(String param1) {
        SuoCZGLorCs my = new SuoCZGLorCs();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return my;

    }

    public static SuoCZGLorCs getnewInstance_Cs() {
        SuoCZGLorCs my = new SuoCZGLorCs();
        return my;

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
            view = inflater.inflate(R.layout.suo_cs, container, false);
        }
        ButterKnife.bind(this, view);
        cslist = new ArrayList<>();
        initDate();
        CsListview();
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        haomaCx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItforHAOMa();
            }
        });
      fazhanCx.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getitForFz();
          }
      });
        return view;
    }

    /**
     * 号码查询
     */
    private void getItforHAOMa() {
        if (chaxunk.getText().toString().trim() != null
                && !chaxunk.getText().toString().trim().equals("")) {
            final CustomDialog customDialog = new CustomDialog(getContext(), R.style.loadstyle);
            Subscription s= HttpUtils.getMy_Retrofit(Url.FDS_URL_MY, getContext())
                    .create(ApiService.class)
                    .querySealByCoach(NowUser.getuser(getContext()),NowUser.getToken(getContext()),chaxunk.getText().toString().trim() )
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            customDialog.show();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<HttpJsjv>() {
                        @Override
                        public void onCompleted() {
                            Log.e("TAG", "根据车号查询连接");
                            if (customDialog != null) {
                                customDialog.dismiss();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("TAG", "根据车号查询错误");
                            if (customDialog != null) {
                                customDialog.dismiss();
                            }
                        }

                        @Override
                        public void onNext(HttpJsjv s) {
//                        qx_dowhat(s);//获取token进行的操作
                            cslist.clear();
                            if (s != null) {
                                cslist.add(s);
                            }
                            re_csuo.setAdapter(csAdapter);
                            csAdapter.setDateCSAdapter(cslist);
                            Log.e("TAG", "根据车号查询成功:" + s.toString());

                        }
                    });
            if (s != null) {
                subscriptions.add(s);
            }

        }
    }
    /**
     * 发站查询
     */
    private void getitForFz() {
        if (chaxunk.getText().toString().trim()!= null
                &&!chaxunk.getText().toString().trim().equals("")) {


        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);
            Subscription s=   HttpUtils.getMy_Retrofit(Url.FDS_URL_MY,getContext())
                .create(ApiService.class)
                .querySealByStart(NowUser.getuser(getContext()),NowUser.getToken(getContext()),chaxunk.getText().toString().trim())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        customDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HttpJsjv>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","根据发站查询连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","根据发站查询错误");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(List<HttpJsjv> s) {
//                        qx_dowhat(s);//获取token进行的操作
                        cslist.clear();
                        if (s != null&&s.size()>0) {
                            cslist.addAll(s);
                        }
                        re_csuo.setAdapter(csAdapter);
                        csAdapter.setDateCSAdapter(cslist);
                        Log.e("TAG","根据发站查询成功:"+s.toString());

                    }
                });
            if (s != null) {
                subscriptions.add(s);
            }
    }
    }

    /**
     * 数据
     */
    private void initDate() {


        re_csuo.setLayoutManager(new LinearLayoutManager(getContext()));
        csAdapter = new CSAdapter();
        re_csuo.setAdapter(csAdapter);
    }

    private void CsListview() {
        csAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                CSialog(position);
            }
        });
    }

    /**
     * 拆锁确认对话框
     *
     * @param position
     */
    private void CSialog(final int position) {
        String cxh = cslist.get(position).getCoachNo().toString();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("车号/箱号为" + cxh + "拆锁确认?");
        builder.setTitle("提示");
        builder.setPositiveButton("拆锁确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * do what
                 */

                doCsuo(position);
           ToastUtils.showmyToasty_success(getContext(), "拆锁成功");
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

    /**
     * 确认加锁
     */
    private void doCsuo(int position) {


        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);
      Subscription s=  HttpUtils.getMy_Retrofit(Url.FDS_URL_MY,getContext())
                .create(ApiService.class)
                .unlock(NowUser.getuser(getContext()),NowUser.getToken(getContext()),cslist.get(position).getCoachNo().toString())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        customDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","拆锁连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","拆锁错误");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(Integer s) {
//                        qx_dowhat(s);//获取token进行的操作

                        Log.e("TAG","拆锁成功:"+s.toString());
                        ToastUtils.showmyToasty_info(getContext(),"拆锁成功");

                    }
                });
        if (s != null) {
            subscriptions.add(s);
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        if (subscriptions != null && subscriptions.size() > 0) {
            for (int i = 0; i < subscriptions.size(); i++) {
                subscriptions.get(i).unsubscribe();//取消订阅
            }
        }
    }
}
