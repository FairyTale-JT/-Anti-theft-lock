package com.example.hjl.jgpushtest.suoactivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.TestACT;
import com.example.hjl.jgpushtest.astuetz.BaseActivity;
import com.example.hjl.jgpushtest.beanClass.HttpFaZhan;
import com.example.hjl.jgpushtest.beanClass.HttpFdsuo;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.enity.NowUser;
import com.example.hjl.jgpushtest.fragment.JsTjAdapter;
import com.example.hjl.jgpushtest.http.ApiService;
import com.example.hjl.jgpushtest.http.HttpUtils;
import com.example.hjl.jgpushtest.http.Url;
import com.example.hjl.jgpushtest.myview.CustomDialog;
import com.example.hjl.jgpushtest.util.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 选择锁
 */

public class SuoCZGLorJs_Tjs extends BaseActivity {
    @Bind(R.id.jstj_xiala)
    SwipeRefreshLayout swipeRefreshLayout_tjs;
    @Bind(R.id.jstj_bt1)
    Button jstjBt1;
    @Bind(R.id.jstj_et1)
    EditText jstjEt1;
    @Bind(R.id.jstj_bt2)
    Button jstjBt2;
    @Bind(R.id.jstj_lv)
    ListView jstjLv;
    private List<FdSuo> list, isChoseList;
    private JsTjAdapter jsTjAdapter;
    private List<Subscription> subscriptions = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_tj);
        ButterKnife.bind(this);
        jstjLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        jsTjAdapter = new JsTjAdapter();
        list = new ArrayList<>();
        chaXunSuoId();//锁号查询按钮监听
        JstjListView();//获取本地数据 并加载界面
        tiaoZhuan_fanhui();//确定按钮监听
        initview();
        swipCheak();
    }

    private void initview() {


///////////////////////////////////
        TextWatcher textWatch = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int count,
                                          int after) {
                //s:变化前的所有字符； start:字符开始的位置；
                // count:变化前的总字节数；after:变化后的字节数

            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {
                //S：变化后的所有字符；start：字符起始的位置；
                // before: 变化之前的总字节数；count:变化后的字节数
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    jstjEt1.setText(str1);
                    jstjEt1.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                String s1;
                s1 = jstjEt1.getText().toString().trim();

                if (s1 != null && s1.length() > 0) {
                    //设置按钮可点击
                    jstjBt1.setEnabled(true);
                    //设置按钮为正常状态
//                    denglu.setPressed(true);

                } else {
                    //设置按钮不可点击
                    jstjBt1.setEnabled(false);
                    //设置按钮为按下状态
//                    denglu.setPressed(false);

                }
            }
        };
//监听EditText内容变化设置Button是否可点击
        jstjEt1.addTextChangedListener(textWatch);

    }

    private void chaXunSuoId() {

        jstjBt1.setEnabled(false);
        jstjBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stID = null;
                stID = jstjEt1.getText().toString();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (stID.equals(list.get(i).getSuo_haoma())) {
                            jstjLv.setAdapter(jsTjAdapter);
                            jsTjAdapter.setArg(i);
                            jstjLv.setSelection(i);
                        }
                    }
                }
            }
        });
    }

    /**
     * 下拉刷新监听
     */
    private void swipCheak() {
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout_tjs.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout_tjs.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout_tjs.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // 开始刷新，设置当前为刷新状态
                        //swipeRefreshLayout.setRefreshing(true);
                        // 这里是主线程
                        // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                        // TODO 获取数据
                        getDate();
                        swipeRefreshLayout_tjs.setRefreshing(false);
                    }
                });
    }


    /**
     * 刷新获取数据操作
     */
    private void getDate() {
        final CustomDialog customDialog = new CustomDialog(SuoCZGLorJs_Tjs.this, R.style.loadstyle);
    Subscription s=    HttpUtils.getMy_Retrofit(Url.FDS_URL_MY, SuoCZGLorJs_Tjs.this)
                .create(ApiService.class)
                .getHoldingLock("UID", "Token")
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
                        Log.e("TAG", "获取锁连接");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "获取锁错误222");
                        if (customDialog != null) {
                            customDialog.dismiss();
                        }
                    }


                    @Override
                    public void onNext(List<HttpFdsuo> s) {
//                        initDate(s);//获取s进行的操作
                        List<FdSuo> li = new ArrayList<>();
                        if (s.size() > 0) {
                            for (int i = 0; i < s.size(); i++) {
                                FdSuo jsjv = new FdSuo();
                                jsjv.setSuo_sbBH(s.get(i).getDeviceNo());
                                jsjv.setSuo_haoma(s.get(i).getLockNo());
                                jsjv.setSuo_ztBJ(s.get(i).getStateName());
                                jsjv.setSuo_isuse(1);
                                jsjv.setUser(NowUser.getuser());
                                li.add(jsjv);
                                Log.e("TAGSSS", s.get(i).getDeviceNo() + "\n" + s.get(i).getLockNo() + "\n" + s.get(i).getStateName() + "\n");
                            }
                        }
                        Log.e("TAGli","li+++"+li.toString());
                        //网络获取到数据后 进行本地数据库操作
                        if (li.size() > 0) {
                            for (int i = 0; i < li.size(); i++) {
                                List<FdSuo> myL =
                                        DataSupport
                                                .where("suo_sbBH = ? and user = ?", li.get(i).getSuo_sbBH(), NowUser.getuser())
                                                .find(FdSuo.class);
                                Log.e("TAGMYL","MYL__"+myL.toString());
                                if (myL.size() > 0) {
                                    ContentValues values = new ContentValues();
                                    values.put("suo_ztBJ", li.get(i).getSuo_ztBJ());
                                    DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", myL.get(0).getSuo_sbBH(), NowUser.getuser());
                                } else {
                                    li.get(i).save();
                                    Log.e("TAGID","li+++"+li.get(i).getId());
                                }
                            }
                        }

                        /**
                         * 操作Jsjv数据库
                         */

                        List<Jsjv> myRWlist = DataSupport.where("isOk > ? and user = ?", "0", NowUser.getuser()).find(Jsjv.class);
                        if (myRWlist.size() > 0) {
                            for (int i = 0; i < myRWlist.size(); i++) {
                                String suo1_sbbh = myRWlist.get(i).getFdSuo1_sbbh();
                                List<FdSuo> fdsuo1 = DataSupport
                                        .where("suo_sbBH = ? and user = ?", suo1_sbbh, NowUser.getuser())
                                        .find(FdSuo.class);
                                if (fdsuo1.size() > 0) {
                                    ContentValues values = new ContentValues();
                                    values.put("fdSuo1_ztbj", fdsuo1.get(0).getSuo_ztBJ());
                                    DataSupport.updateAll(Jsjv.class, values, "fdSuo1_sbbh = ? and user = ?", myRWlist.get(i).getFdSuo1_sbbh(), NowUser.getuser());
                                }
                                if (myRWlist.get(i).getFdSuo2_sbbh() != null &&
                                        !myRWlist.get(i).getFdSuo2_sbbh().equals("")) {
                                    String suo2_sbbh = myRWlist.get(i).getFdSuo2_sbbh();
                                    Log.e("TAGSuo2", suo2_sbbh);
                                    List<FdSuo> fdsuo2 = DataSupport
                                            .where("suo_sbBH = ? and user = ?", suo2_sbbh, NowUser.getuser())
                                            .find(FdSuo.class);
                                    if (fdsuo2.size() > 0) {
                                        ContentValues values = new ContentValues();
                                        values.put("fdSuo2_ztbj", fdsuo2.get(0).getSuo_ztBJ());
                                        DataSupport.updateAll(Jsjv.class, values, "fdSuo2_sbbh = ? and user = ?", myRWlist.get(i).getFdSuo2_sbbh(), NowUser.getuser());
                                    }
                                }
                            }
                        }
                        Intent intent = new Intent("jerry");
                        intent.putExtra("change", "yes");
                        LocalBroadcastManager.getInstance(SuoCZGLorJs_Tjs.this).sendBroadcast(intent);
                        sendBroadcast(intent);
                        list.clear();
                        List<FdSuo> liBenDi = new ArrayList<>();
                        liBenDi = DataSupport.where("user = ?", NowUser.getuser()).find(FdSuo.class);
                        list.addAll(liBenDi);
                        jstjLv.setAdapter(jsTjAdapter);
                        jsTjAdapter.setsetDateJsTjAdapter(list);
                        Log.e("TAG", "获取锁成功:" + s.toString());

                    }
                });
        if (s != null) {
            subscriptions.add(s);
        }
    }

    private void choseSuo() {
       /*
        * 当为单选时，调用getCheckedItemPosition()获取选中的item的position
        */
        //为多选时方法如下
        SparseBooleanArray array = jstjLv.getCheckedItemPositions();
        isChoseList = new ArrayList<>();
        for (int x = 0; x < array.size(); x++) {
            int key = array.keyAt(x);
            boolean b = array.get(key);
            if (b) {
                //key指的是该item在listview中的position
                isChoseList.add(list.get(key));
            }
        }
    }

    private void tiaoZhuan_fanhui() {
        jstjBt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //存储返回数据   也要用intent
                choseSuo();
                String date_id = null, date_sbbh = null, date_ztbj = null;
                String date2_id = null, date2_sbbh = null, date2_ztbj = null;
                Bundle bundle = new Bundle();
                if (isChoseList != null && isChoseList.size() > 0) {
                    if (isChoseList.size() == 1) {
                        date_id = isChoseList.get(0).getSuo_haoma().toString();
                        date_sbbh = isChoseList.get(0).getSuo_sbBH().toString();
                        date_ztbj = isChoseList.get(0).getSuo_ztBJ().toString();
                        bundle.putString("suo1", date_id);
                        bundle.putString("sbbh1", date_sbbh);
                        bundle.putString("ztbj1", date_ztbj);
                        doit(bundle);
                    }
                    if (isChoseList.size() == 2) {
                        date_id = isChoseList.get(0).getSuo_haoma().toString();
                        date_sbbh = isChoseList.get(0).getSuo_sbBH().toString();
                        date_ztbj = isChoseList.get(0).getSuo_ztBJ().toString();
                        date2_id = isChoseList.get(1).getSuo_haoma().toString();
                        date2_sbbh = isChoseList.get(1).getSuo_sbBH().toString();
                        date2_ztbj = isChoseList.get(1).getSuo_ztBJ().toString();
                        bundle.putString("suo1", date_id);
                        bundle.putString("sbbh1", date_sbbh);
                        bundle.putString("ztbj1", date_ztbj);
                        bundle.putString("suo2", date2_id);
                        bundle.putString("sbbh2", date2_sbbh);
                        bundle.putString("ztbj2", date2_ztbj);
                        doit(bundle);
                    }
                    if (isChoseList.size() > 2) {
                        ToastUtils.showmyToasty_War(SuoCZGLorJs_Tjs.this, "最多可以选择两把锁");
                    }
                } else {
                    finish();
                }
            }
        });
    }

    private void doit(Bundle bundle) {
        //设置返回数据
        // 先设置ReaultCode,再设置存储数据的意图
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        //关闭当前activity
        finish();
    }

    private void JstjListView() {
        List<FdSuo> li = new ArrayList<>();

        li = DataSupport.where("user = ?", NowUser.getuser()).find(FdSuo.class);
        if (li.size() > 0) {
            list.addAll(li);
        }
        jstjLv.setAdapter(jsTjAdapter);
        jsTjAdapter.setsetDateJsTjAdapter(list);
        Log.e("TAG+LIST", li.toString());
        Log.e("TAG+LIST", list.toString());
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
