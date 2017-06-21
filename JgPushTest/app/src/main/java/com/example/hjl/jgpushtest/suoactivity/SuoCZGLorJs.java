package com.example.hjl.jgpushtest.suoactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewLongItemClickListener;
import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.BinCZB;
import com.example.hjl.jgpushtest.enity.FaZhan;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.fragment.JiaSuoAdapter;
import com.example.hjl.jgpushtest.fragment.SpinnerAdapter;
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
 * Created by hjl on 2017/6/12.
 */

public class SuoCZGLorJs extends Fragment {
    private RecyclerView js_rv;
    private View view;
    private JiaSuoAdapter jiaSuoAdapter;
    private List<Jsjv> list;
    private Button js_xzs, js_tj;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Subscription> subscriptions = new ArrayList<>();
    private EditText suo1, suo2, cx_NO, dz_Ming;
    private Button daoz_bt;
    FdSuo fdSuo1 = null;
    FdSuo fdSuo2 = null;
    private String cz_DBM;

    private String suo1_sbbh = null, suo1_id = null, suo1_ztbj = null, suo2_id = null, suo2_sbbh = null, suo2_ztbj = null;
    private List<BinCZB> czbList;
    private String[] areas;
    private Spinner js_lx, js_fz;
    private List<FaZhan> fa_list;
    private SpinnerAdapter spinnerAdapter;
    private String jslx, fz;

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
        return view;
    }

    /**
     * 初始化本地数据
     */
    private void initdata() {
//        DataSupport.deleteAll(FdSuo.class);

        List<Jsjv> li = DataSupport.where("isOk > ?", "0").find(Jsjv.class);
        if (li.size() > 0) {
            for (int i = 0; i < li.size(); i++) {
                list.add(li.get(i));
            }
        }
        js_rv.setAdapter(jiaSuoAdapter);
        jiaSuoAdapter.setDateJiaSuoAdapter(list);

    }

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
        fa_list.add(new FaZhan("北京"));
        fa_list.add(new FaZhan("成都"));
        spinnerAdapter = new SpinnerAdapter(getContext(), fa_list);
        js_fz.setAdapter(spinnerAdapter);
//        js_dz.setEnabled(false);
    }

    /**
     * 车站选择
     */
    private void DzXz() {
        daoz_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dz_Ming.getText().toString().equals("")) {
                    getDZ();
                } else {
                    Toasty.info(getContext(), "请输入车站的名,再选择", Toast.LENGTH_SHORT).show();
                }
//                getDZ();
            }
        });

    }

    void getDZ(){
        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);
    Subscription s=
             Observable.create(new Observable.OnSubscribe<List<BinCZB>>() {
            @Override
            public void call(Subscriber<? super List<BinCZB>> subscriber) {
                Throwable x=null;
                try {
                    czbList =
                            FindTest.FindShezhiZM(getResources().
                                            openRawResource(R.raw.czb),
                            dz_Ming.getText().toString());

                    Log.e("TAG","try");
                } catch (Exception e) {
                    e.printStackTrace();
                     x=e;
                    Log.e("TAG","e1");
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
                        if (!(li.size()>0)) {
                            dz_Ming.setText("");
                            ToastUtils.showmyToasty_War(getContext(),"查无此站!");
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
                                            cz_DBM=li.get(i).getCZID();//电报码
                                            Log.e("TAG","电报码=="+cz_DBM);
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                        Log.e("TAG","next");
                    }

                    @Override
                    public void onCompleted() {
                        customDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        customDialog.dismiss();

                        Log.e("TAG","e2");
                        ToastUtils.showmyToasty_Er(getContext(),"Error!");
                    }
                });
        subscriptions.add(s);
    }

    //Spinner发站/类型
    private void ZSpinner() {
        js_lx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] fa = getResources().getStringArray(R.array.spingarr);
                jslx = fa[i];

//                Toasty.info(context, "你点击的是:" + jslx, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        js_fz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fz = fa_list.get(i).getFz();
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

    }

    //Listview item监听
    private void JsListview() {

        jiaSuoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                ToastUtils.showmyToasty_info(getContext(), s + "---点击");
                UDialog(s);
            }
        });
        jiaSuoAdapter.setOnLongItemClickListener(new OnRecyclerViewLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                ToastUtils.showmyToasty_info(getContext(), s + "---长按");
                DeleteDialog(s, position);
            }
        });
    }
    private void  OnLongClick(){

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
                    jsjv.setCxh(cx_NO.getText().toString());
                    jsjv.setFz(getFZ_bendi());
                    jsjv.setIsOk(1);

                    //添加数据，重新绑定Adater刷新界面
                    list.add(jsjv);
                    jsjv.save();
                    js_rv.setAdapter(jiaSuoAdapter);
                    jiaSuoAdapter.setDateJiaSuoAdapter(list);
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
                } else {
                    ToastUtils.showmyToasty_Er(getContext(), "请输入完整");
                }


            }
        });

    }

    //加锁确认对话框
    private void UDialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("车号/箱号为" + cxh + "加锁确认?");
        builder.setTitle("提示");
        builder.setPositiveButton("加锁确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
    /**
    * do what
    */
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.create().dismiss();
            }
        });
        builder.create().show();

    }

    //删除对话框
    private void DeleteDialog(String cxh, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定删除车号/箱号为" + cxh + "的记录?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
                builder.create().dismiss();
            }
        });
        builder.create().show();

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

        if (subscriptions != null&&subscriptions.size()>0) {
            for (int i = 0; i <subscriptions.size() ; i++) {
                subscriptions.get(i).unsubscribe();//取消订阅
            }
        }
    }
}
