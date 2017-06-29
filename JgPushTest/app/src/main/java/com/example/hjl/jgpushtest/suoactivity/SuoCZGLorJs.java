package com.example.hjl.jgpushtest.suoactivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjl.jgpushtest.beanClass.HttpFdsuo;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewLongItemClickListener;
import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.BinCZB;
import com.example.hjl.jgpushtest.enity.FaZhan;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.enity.NowUser;
import com.example.hjl.jgpushtest.enity.VerCode;
import com.example.hjl.jgpushtest.fragment.JiaSuoAdapter;
import com.example.hjl.jgpushtest.fragment.SpinnerAdapter;
import com.example.hjl.jgpushtest.http.ApiService;
import com.example.hjl.jgpushtest.http.HttpUtils;
import com.example.hjl.jgpushtest.http.Url;
import com.example.hjl.jgpushtest.myview.CustomDialog;
import com.example.hjl.jgpushtest.util.FindTest;
import com.example.hjl.jgpushtest.util.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 加锁
 */

public class SuoCZGLorJs extends Fragment {
    private RecyclerView js_rv;
    private View view;
    private JiaSuoAdapter jiaSuoAdapter;
    private List<Jsjv> list;
    private Button js_xzs, js_tj;
    SwipeRefreshLayout swipeRefreshLayout;
   private List<Subscription> subscriptions = new ArrayList<>();
    private EditText suo1, suo2, cx_NO, dz_Ming;
    private Button daoz_bt;
    FdSuo fdSuo1 = null;
    FdSuo fdSuo2 = null;
    private String dz_DBM = "", fz_DBM = "";

    private String suo1_sbbh = null, suo1_id = null, suo1_ztbj = null, suo2_id = null, suo2_sbbh = null, suo2_ztbj = null;
    private List<BinCZB> czbList;
    private String[] areas;
    private Spinner js_lx, js_fz;
    private List<FaZhan> fa_list;
    private SpinnerAdapter spinnerAdapter;
    private String jslx, fz;
    private ImageView iv_showCode;
    private EditText et_phoneCode;
    private Button but_toSetCode;
    private TextView custom_sc, custom_qzjs;
    private String realCode;
    private LocalBroadcastManager broadcastManager;

    public static SuoCZGLorJs getnewInstance_Js(String param1) {
        SuoCZGLorJs my = new SuoCZGLorJs();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return my;

    }

    public static SuoCZGLorJs getnewInstance_Js() {
        SuoCZGLorJs my = new SuoCZGLorJs();
        return my;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册广播
        registerReceiver();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.suo_js2, container, false);
        }
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        list = new ArrayList<>();
        czbList = new ArrayList<>();
        initView();
        initdata();
        JsListview();
        JsButton();
        /**
         * 下拉刷新的监听
         */
        swipCheak();
        //车站选择
        DzXz();
        //类型和发站选择
        ZSpinner();
        Log.e("TAGTOKEN+USER:",NowUser.getuser(getContext())+"----"+NowUser.getToken(getContext()));
        return view;
    }

    /**
     * 初始化本地数据
     */
    private void initdata() {
        // DataSupport.deleteAll(FdSuo.class);


        List<Jsjv> li = DataSupport.where("user = ?", NowUser.getuser(getContext())).find(Jsjv.class);
        if (li.size() > 0) {
            for (int i = 0; i < li.size(); i++) {
                list.add(li.get(i));
            }
        }
        js_rv.setAdapter(jiaSuoAdapter);
        jiaSuoAdapter.setDateJiaSuoAdapter(list);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.suo_swip_item);
        suo1 = (EditText) view.findViewById(R.id.suo_suohao1_ed);
        suo2 = (EditText) view.findViewById(R.id.suo_suohao2_ed);
        cx_NO = (EditText) view.findViewById(R.id.suo_haoma_ed);
        dz_Ming = (EditText) view.findViewById(R.id.suo_daoz_ed);
        daoz_bt = (Button) view.findViewById(R.id.suo_daoz_bt);

        js_rv = (RecyclerView) view.findViewById(R.id.suo_js_rev_fragment);
        js_xzs = (Button) view.findViewById(R.id.suo_js_xzsuo);
        js_tj = (Button) view.findViewById(R.id.suo_js_tijiao);
        js_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        js_rv.setItemAnimator(new DefaultItemAnimator());
        jiaSuoAdapter = new JiaSuoAdapter();
        js_rv.setAdapter(jiaSuoAdapter);

        //选择类型
        js_lx = (Spinner) view.findViewById(R.id.suo_js_lx);
        //选择发站
        js_fz = (Spinner) view.findViewById(R.id.suo_js_dz);
        fa_list = new ArrayList<FaZhan>();


        List<FaZhan> fazhanlist = DataSupport.where("user = ?", NowUser.getuser(getContext())).find(FaZhan.class);
        if (fazhanlist != null && fazhanlist.size() > 0) {

            fa_list.addAll(fazhanlist);
        } else {
            fa_list.add(new FaZhan("城厢", "CFW"));
        }
        spinnerAdapter = new SpinnerAdapter(getContext(), fa_list);
        js_fz.setAdapter(spinnerAdapter);
        //   js_dz.setEnabled(false);
    }

    /**
     * 车站选择
     */
    private void DzXz() {
        //到站选择
        daoz_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dz_Ming.getText().toString().equals("")) {
                    getDZ();
                } else {
                    Toasty.info(getContext(), "请输入车站的名,再选择", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void getDZ() {
        czbList.clear();
        final CustomDialog customDialog = new CustomDialog(getContext(), R.style.loadstyle);
        Subscription s =
                Observable.create(new Observable.OnSubscribe<List<BinCZB>>() {
                    @Override
                    public void call(Subscriber<? super List<BinCZB>> subscriber) {
                        Throwable x = null;
                        try {
                            czbList =
                                    FindTest.FindShezhiZM(getResources().
                                                    openRawResource(R.raw.czb),
                                            dz_Ming.getText().toString());

                            Log.e("TAG", "try");
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("TAG", "e1");

                        }
                        subscriber.onNext(czbList);
                        subscriber.onCompleted();
                        subscriber.onError(x);
                    }
                }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                customDialog.show();
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                        .subscribe(new Observer<List<BinCZB>>() {
                            @Override

                            public void onNext(final List<BinCZB> li) {
                                if (!(li.size() > 0)) {
                                    dz_Ming.setText("");
                                    ToastUtils.showmyToasty_War(getContext(), "查无此站!");
                                } else {
                                    areas = new String[li.size()];
                                    for (int i = 0; i < li.size(); i++) {
                                        areas[i] = li.get(i).getZM();

                                    }
                                    new AlertDialog.Builder(getContext()).setTitle("请选择车站")
                                            .setCancelable(true)
                                            .setSingleChoiceItems(areas, -1, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int i) {
                                                    dz_Ming.setText(li.get(i).getZM());
                                                    dz_DBM = li.get(i).getCZID();
                                                    dialog.dismiss();
                                                }
                                            }).show();
                                }

                                Log.e("TAG", "next");
                            }

                            @Override
                            public void onCompleted() {
                                customDialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                customDialog.dismiss();

                                Log.e("TAG", "e2");
                                ToastUtils.showmyToasty_Er(getContext(), "Error!");
                            }
                        });
        subscriptions.add(s);
    }

    /**
     * Spinner发站/类型
     */
    private void ZSpinner() {
        //类型
        js_lx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] fa = getResources().getStringArray(R.array.spingarr);
                jslx = fa[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //发站
        js_fz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fz = fa_list.get(i).getFz();
                fz_DBM = fa_list.get(i).getFzDBM();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    /**
     * 下拉刷新的监听
     */
    private void swipCheak() {
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // 开始刷新，设置当前为刷新状态
                        //swipeRefreshLayout.setRefreshing(true);
                        // 这里是主线程
                        // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                        // TODO 获取数据
                        doGet();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * 获取数据
     */
    private void doGet() {
        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);
        Subscription s= HttpUtils.getMy_Retrofit(Url.FDS_URL_MY,getContext())
                .create(ApiService.class)
                .getHoldingLock(NowUser.getuser(getContext()),NowUser.getToken(getContext()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        customDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HttpFdsuo>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","获取锁连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","获取锁错误222");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(List<HttpFdsuo> s) {

                        List<FdSuo> li = new ArrayList<>();
                        if (s.size() > 0) {
                            for (int i = 0; i < s.size(); i++) {
                                FdSuo jsjv = new FdSuo();
                                jsjv.setSuo_sbBH(s.get(i).getDeviceNo());
                                jsjv.setSuo_haoma(s.get(i).getLockNo());
                                jsjv.setSuo_ztBJ(s.get(i).getStateName());
                                jsjv.setSuo_isuse(1);
                                jsjv.setUser(NowUser.getuser(getContext()));
                                li.add(jsjv);
                                Log.e("TAGSSS", s.get(i).getDeviceNo() + "\n" + s.get(i).getLockNo() + "\n" + s.get(i).getStateName() + "\n");
                            }
                        }
                        Log.e("TAGli", "li+++" + li.toString());
                        //网络获取到数据后 进行本地数据库操作
                        if (li.size() > 0) {
                            for (int i = 0; i < li.size(); i++) {
                                List<FdSuo> myL =
                                        DataSupport
                                                .where("suo_sbBH = ? and user = ?", li.get(i).getSuo_sbBH(), NowUser.getuser(getContext()))
                                                .find(FdSuo.class);
                                Log.e("TAGMYL", "MYL__" + myL.toString());
                                if (myL.size() > 0) {
                                    ContentValues values = new ContentValues();
                                    values.put("suo_ztBJ", li.get(i).getSuo_ztBJ());
                                    DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", myL.get(0).getSuo_sbBH(), NowUser.getuser(getContext()));
                                } else {
                                    li.get(i).save();
                                    Log.e("TAGID", "li+++" + li.get(i).getId());
                                }
                            }
                        }

                        /**
                         * 操作Jsjv数据库
                         */
                        List<Jsjv> myRWlist = DataSupport.where("isOk > ? and user = ?", "0", NowUser.getuser(getContext())).find(Jsjv.class);
                        Log.e("TAGmyRWlist", "myRWlist__" + myRWlist.toString());
                        if (myRWlist.size() > 0) {
                            for (int i = 0; i < myRWlist.size(); i++) {
                                String suo1_sbbh = myRWlist.get(i).getFdSuo1_sbbh();
                                List<FdSuo> fdsuo1 = DataSupport
                                        .where("suo_sbBH = ? and user = ?", suo1_sbbh, NowUser.getuser(getContext()))
                                        .find(FdSuo.class);
                                if (fdsuo1.size() > 0) {
                                    Log.e("TAGsuo1_sbbh", "suo1_sbbh----" + suo1_sbbh + "++++++" + fdsuo1.toString());
                                    ContentValues values = new ContentValues();
                                    values.put("fdSuo1_ztbj", fdsuo1.get(0).getSuo_ztBJ());
                                    DataSupport.updateAll(Jsjv.class, values, "fdSuo1_sbbh = ? and user = ?", myRWlist.get(i).getFdSuo1_sbbh(), NowUser.getuser(getContext()));
                                    Log.e("TAGsuo1_sbbh```", "SUO1++++改变");
                                }
                                if (myRWlist.get(i).getFdSuo2_sbbh() != null
                                        && !myRWlist.get(i).getFdSuo2_sbbh().equals("")) {
                                    String suo2_sbbh = myRWlist.get(i).getFdSuo2_sbbh();
                                    Log.e("TAGSuo2```", suo2_sbbh);
                                    List<FdSuo> fdsuo2 = DataSupport
                                            .where("suo_sbBH = ? and user = ?", suo2_sbbh, NowUser.getuser(getContext()))
                                            .find(FdSuo.class);
                                    if (fdsuo2.size() > 0) {
                                        Log.e("TAGSuo2```", "SUO2++++" + fdsuo2.toString());
                                        ContentValues values = new ContentValues();
                                        values.put("fdSuo2_ztbj", fdsuo2.get(0).getSuo_ztBJ());
                                        DataSupport.updateAll(Jsjv.class, values, "fdSuo2_sbbh = ? and user = ?", myRWlist.get(i).getFdSuo2_sbbh(), NowUser.getuser(getContext()));
                                        Log.e("TAGSuo2```", "SUO2++++改变");
                                    }
                                }
                            }
                        }
                        list.clear();
                        list=DataSupport.where("user = ?",NowUser.getuser(getContext())).find(Jsjv.class);
                        js_rv.setAdapter(jiaSuoAdapter);
                        jiaSuoAdapter.setDateJiaSuoAdapter(list);
                    }
                });

        if (s != null) {
            subscriptions.add(s);
        }
    }

    /**
     * 选择锁以及提交按钮
     */
    private void JsButton() {

        //选择锁
        js_xzs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SuoCZGLorJs_Tjs.class);
                startActivityForResult(intent, 1);//带返回参数的跳转
            }
        });

        //提交
        js_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Jsjv jsjv = new Jsjv();
                if (
                        (!TextUtils.isEmpty(cx_NO.getText())) && (!TextUtils.isEmpty(dz_Ming.getText()))
                                && (!TextUtils.isEmpty(suo1.getText()))
                        ) {

                    if (suo1_id != null) {
                        jsjv.setFdSuo1_id(suo1_id);
                        jsjv.setFdSuo1_sbbh(suo1_sbbh);
                        jsjv.setFdSuo1_ztbj(suo1_ztbj);

                    }
                    if (suo2_id != null) {
                        jsjv.setFdSuo2_id(suo2_id);
                        jsjv.setFdSuo2_sbbh(suo2_sbbh);
                        jsjv.setFdSuo2_ztbj(suo2_ztbj);
                    }
                    jsjv.setDz(dz_Ming.getText().toString());
                    jsjv.setDzdbm(dz_DBM);
                    jsjv.setCxh(cx_NO.getText().toString());
                    jsjv.setFz(getFZ_bendi());
                    jsjv.setFzdbm(fz_DBM);
                    jsjv.setUser(NowUser.getuser(getContext()));
                    jsjv.setIsOk(1);


                    //动态修改Fdsuo本地数据库
                    if (suo1_id != null) {
                        ContentValues values = new ContentValues();
                        values.put("suo_isuse", -1);
                        DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", suo1_sbbh, NowUser.getuser(getContext()));
                    }
                    if (suo2_id != null && !suo2_id.equals("")) {
                        ContentValues values = new ContentValues();
                        values.put("suo_isuse", -1);
                        DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", suo2_sbbh, NowUser.getuser(getContext()));
                    }
                    //添加数据，重新绑定Adater刷新界面
                    if (jslx.equals("整车")) {
                        if (cx_NO.getText().toString().length() == 7) {
                            list.add(jsjv);
                            jsjv.save();
                            js_rv.setAdapter(jiaSuoAdapter);
                            jiaSuoAdapter.setDateJiaSuoAdapter(list);
                            Log.e("TAGJS", jsjv.toString());
                            cx_NO.setText("");
                            dz_Ming.setText("");
                            suo1.setText("");
                            suo2.setText("");
                            suo1_sbbh = null;
                            suo1_id = null;
                            suo1_ztbj = null;
                            suo2_id = null;
                            suo2_sbbh = null;
                            suo2_ztbj = null;
                        }else {
                            ToastUtils.showmyToasty_War(getContext(),"车号有误！！");
                        }
                    }else if (jslx.equals("集装箱")){
                        if (cx_NO.getText().toString().length() == 11) {
                            list.add(jsjv);
                            jsjv.save();
                            js_rv.setAdapter(jiaSuoAdapter);
                            jiaSuoAdapter.setDateJiaSuoAdapter(list);
                            Log.e("TAGJS", jsjv.toString());
                            cx_NO.setText("");
                            dz_Ming.setText("");
                            suo1.setText("");
                            suo2.setText("");
                            suo1_sbbh = null;
                            suo1_id = null;
                            suo1_ztbj = null;
                            suo2_id = null;
                            suo2_sbbh = null;
                            suo2_ztbj = null;
                        }else {
                            ToastUtils.showmyToasty_War(getContext(),"集装箱号有误！！");
                        }
                    }

                } else {
                    ToastUtils.showmyToasty_Er(getContext(), "请输入完整");
                }


            }
        });

    }

    /**
     * Listview item 点击与长按
     */

    private void JsListview() {

        jiaSuoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                UDialog(s, position);
            }
        });
        jiaSuoAdapter.setOnLongItemClickListener(new OnRecyclerViewLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                showCustomViewDialog(view, s, position);
                //DeleteDialog(s, position);
            }
        });
    }

    /**
     * 加锁确认对话框
     *
     * @param cxh
     */
    private void UDialog(String cxh, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("车号/箱号为" + cxh + "加锁确认?");
        builder.setTitle("提示");
        builder.setPositiveButton("加锁确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * do what
                 */

                if (list.get(position).getFdSuo2_id() != null &&
                        !list.get(position).getFdSuo2_id().equals("")) {
                    if (list.get(position).getFdSuo1_ztbj().equals("加锁") &&
                            list.get(position).getFdSuo2_ztbj().equals("加锁")) {
                        doJiaSsuo2(position,1);
                    }else {
                        ToastUtils.showmyToasty_info(getContext(),"有锁未确认成功");
                    }

                } else {
                    if (list.get(position).getFdSuo1_ztbj().equals("加锁")) {
                        doJiaSsuo(position,1);
                    }else {
                        ToastUtils.showmyToasty_info(getContext(),"有锁未确认成功");
                    }
                }


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


    private void doJiaSsuo2(final int position, final int a) {
        final CustomDialog customDialog = new CustomDialog(getContext(), R.style.loadstyle);
        Log.e("TAGLIST", list.get(position).toString());
        Subscription s=    HttpUtils.getMy_Retrofit(Url.FDS_URL_MY, getContext())
                .create(ApiService.class)
                .submitSealT(NowUser.getuser(getContext()), NowUser.getToken(getContext()), list.get(position).getCxh(), list.get(position).getFzdbm(), list.get(position).getDzdbm(), list.get(position).getFdSuo1_sbbh(), a, list.get(position).getFdSuo2_sbbh(), 1)
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
                        Log.e("TAG", "2加锁连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "2加锁错误");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(Integer s) {

                        int a = list.get(position).getId();
                        DataSupport.delete(Jsjv.class, a);
                        DataSupport.deleteAll(FdSuo.class, "suo_sbBH = ?",
                                list.get(position).getFdSuo1_sbbh().toString());
                        DataSupport.deleteAll(FdSuo.class, "suo_sbBH = ?",
                                list.get(position).getFdSuo2_sbbh().toString());
                        list.remove(position);
                        js_rv.setAdapter(jiaSuoAdapter);
                        jiaSuoAdapter.setDateJiaSuoAdapter(list);
                        Log.e("TAG", "2加锁成功:" + s.toString());

                    }
                });
        if (s != null) {
            subscriptions.add(s);
        }
    }

    private void doJiaSsuo(final int position, final int a) {
        final CustomDialog customDialog = new CustomDialog(getContext(), R.style.loadstyle);
        Log.e("TAGLIST", list.get(position).toString());
     Subscription s=   HttpUtils.getMy_Retrofit(Url.FDS_URL_MY, getContext())
                .create(ApiService.class)
                .submitSealF(NowUser.getuser(getContext()), NowUser.getToken(getContext()), list.get(position).getCxh(), list.get(position).getFzdbm(), list.get(position).getDzdbm(), list.get(position).getFdSuo1_sbbh(), a)
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
                        Log.e("TAG", "1加锁连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "1加锁错误");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(Integer s) {

                        int a = list.get(position).getId();
                        DataSupport.delete(Jsjv.class, a);
                        DataSupport.deleteAll(FdSuo.class, "suo_sbBH = ?",
                                list.get(position).getFdSuo1_sbbh());
                        list.remove(position);
                        js_rv.setAdapter(jiaSuoAdapter);
                        jiaSuoAdapter.setDateJiaSuoAdapter(list);
                        Log.e("TAG", "1加锁成功:" + s.toString());


                }
            });
    if (s != null) {
        subscriptions.add(s);
    }
}



    /**
     * 删除对话框
     *
     * @param cxh
     * @param position
     */
    private void DeleteDialog(String cxh, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定删除车号/箱号为" + cxh + "的记录?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //动态修改Fdsuo本地数据库
                if (list.get(position).getFdSuo1_sbbh() != null &&
                        !list.get(position).getFdSuo1_sbbh().equals("")) {
                    ContentValues values = new ContentValues();
                    values.put("suo_isuse", 1);
                    DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", list.get(position).getFdSuo1_sbbh(), NowUser.getuser(getContext()));
                }
                if (list.get(position).getFdSuo2_sbbh() != null &&
                        !list.get(position).getFdSuo2_sbbh().equals("")) {
                    ContentValues values = new ContentValues();
                    values.put("suo_isuse", 1);
                    DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", list.get(position).getFdSuo2_sbbh(), NowUser.getuser(getContext()));
                }

                int a = list.get(position).getId();
                list.remove(position);
                js_rv.setAdapter(jiaSuoAdapter);
                jiaSuoAdapter.setDateJiaSuoAdapter(list);
                DataSupport.delete(Jsjv.class, a);

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
     * 长按弹出删除与强制加锁Dialog
     *
     * @param view
     * @param cxh
     * @param position
     */
    private void showCustomViewDialog(View view, final String cxh, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("车号/箱号为" + cxh + ":");
        /**
         * 设置内容区域为自定义View
         */
        LinearLayout CustomDialog = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.custom_view, null);
        builder.setView(CustomDialog);

        custom_sc = (TextView) CustomDialog.findViewById(R.id.custom_sc);
        custom_qzjs = (TextView) CustomDialog.findViewById(R.id.custom_qzjs);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();
        custom_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DeleteDialog(cxh, position);
            }
        });
        custom_qzjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                CodeDialog(view, cxh, position);
            }
        });

    }

    /**
     * 强制加锁Dialog
     *
     * @param view
     * @param cxh
     * @param position
     */
    private void CodeDialog(View view, final String cxh, int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("车号/箱号为" + cxh + "强制加锁");
        LinearLayout CustomDialog = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.custom_code, null);
        builder.setView(CustomDialog);
        iv_showCode = (ImageView) CustomDialog.findViewById(R.id.iv_showCode);
        iv_showCode.setImageBitmap(VerCode.getInstance().createBitmap());
        realCode = VerCode.getInstance().getCode().toLowerCase();
        et_phoneCode = (EditText) CustomDialog.findViewById(R.id.et_phoneCodes);
        but_toSetCode = (Button) CustomDialog.findViewById(R.id.but_forgetpass_toSetCodes);
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        CodeOnClick(dialog, position);
        dialog.show();

    }

    /**
     * 验证码
     *
     * @param dialog
     */
    private void CodeOnClick(final AlertDialog dialog, final int position) {

        iv_showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_showCode.setImageBitmap(VerCode.getInstance().createBitmap());
                realCode = VerCode.getInstance().getCode().toLowerCase();
            }
        });
        but_toSetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneCode = et_phoneCode.getText().toString().toLowerCase();
                String msg = "生成的验证码：" + realCode + "输入的验证码:" + phoneCode;

                if (phoneCode.equals(realCode)) {
                    if (list.get(position).getFdSuo2_sbbh() != null &&
                            !list.get(position).getFdSuo2_sbbh().equals("")) {
                        doJiaSsuo2(position, 5);
                    } else {
                        doJiaSsuo(position, 5);
                    }
                    ToastUtils.showmyToasty_success(getContext(), phoneCode + "--position--" + position + "验证码正确");
                    dialog.dismiss();

                } else {
                    ToastUtils.showmyToasty_Er(getContext(), phoneCode + "验证码错误");
                    et_phoneCode.setText("");
                    iv_showCode.setImageBitmap(VerCode.getInstance().createBitmap());
                    realCode = VerCode.getInstance().getCode().toLowerCase();
                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        suo1_sbbh = null;
        suo1_id = null;
        suo1_ztbj = null;
        suo2_id = null;
        suo2_sbbh = null;
        suo2_ztbj = null;
        suo1.setText("");
        suo2.setText("");

        Log.e("TAG", "requestCode=" + requestCode + "resultCode" + resultCode);
        if (requestCode == 1) {
            if (resultCode == SuoMainActivity.RESULT_OK) {
                //获取返回信息
                String string1 = data.getExtras().getString("suo1");
                String string2 = data.getExtras().getString("suo2");
                String sbbh1 = data.getExtras().getString("sbbh1");
                String sbbh2 = data.getExtras().getString("sbbh2");
                String ztbj1 = data.getExtras().getString("ztbj1");
                String ztbj2 = data.getExtras().getString("ztbj2");
                if (string2 != null) {
                    suo2.setText(string2);
                    suo2_id = string2;
                    suo2_sbbh = sbbh2;
                    suo2_ztbj = ztbj2;
                }
                if (string1 != null) {
                    suo1.setText(string1);
                    suo1_id = string1;
                    suo1_sbbh = sbbh1;
                    suo1_ztbj = ztbj1;
                }
            }

        } else {
            ToastUtils.showmyToasty_info(getContext(), "返回信息有问题");
        }


    }

    /**
     * 获取该用户设置的发站
     *
     * @return
     */
    String getFZ_bendi() {
        return fz;
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
    }

    /**
     * 注册广播接收器
     */
    private void registerReceiver() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("jerry");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("tuisong");
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter);
        broadcastManager.registerReceiver(mAdDownLoadReceiver, intentFilter2);
    }

    //
    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String change = intent.getStringExtra("change");
            if ("yes".equals(change)) {
// 这地方只能在主线程中刷新UI,子线程中无效，因此用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
//在这里来写你需要刷新的地方
                        //例如：testView.setText("恭喜你成功了");
                        list.clear();
                        list = DataSupport.where("user = ?", NowUser.getuser(getContext())).find(Jsjv.class);
                        js_rv.setAdapter(jiaSuoAdapter);
                        jiaSuoAdapter.setDateJiaSuoAdapter(list);
                    }
                });
            }
        }
    };

    /**
     * 注销广播
     */
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mAdDownLoadReceiver);
    }
}
