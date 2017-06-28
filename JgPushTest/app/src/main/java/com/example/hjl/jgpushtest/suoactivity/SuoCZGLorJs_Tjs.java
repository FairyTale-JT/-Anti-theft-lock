package com.example.hjl.jgpushtest.suoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.NowUser;
import com.example.hjl.jgpushtest.fragment.JsTjAdapter;
import com.example.hjl.jgpushtest.util.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

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
                        doGet();
                        swipeRefreshLayout_tjs.setRefreshing(false);
                    }
                });
    }

    /**
     * 刷新获取数据操作
     */
    private void doGet() {
        initDate();
    }


    /**
     * 获取数据
     */
    private void initDate() {
        List<FdSuo> li = new ArrayList<>();
//        List<FdSuo> liBenDi=new ArrayList<>();
//        liBenDi=DataSupport.where("suo_isuse > ?", "0").find(FdSuo.class);
        list.clear();
        for (int i = 1; i < 33; i++) {
            FdSuo jsjv = new FdSuo();
            jsjv.setSuo_sbBH("10000" + i);
            jsjv.setSuo_haoma("10000" + i);
            jsjv.setSuo_cdTS("11" + i);
            jsjv.setSuo_ztBJ("出库");
            jsjv.setSuo_isuse(1);
            jsjv.setUser(NowUser.getuser());
            li.add(jsjv);
        }
//        if (liBenDi.size() > 0) {
//
//        }else {
//
//        }
        if (li.size() > 0) {
//            List<FdSuo> s=new ArrayList<>();
            list.addAll(li);
        }
        jstjLv.setAdapter(jsTjAdapter);
        jsTjAdapter.setsetDateJsTjAdapter(list);
        DataSupport.deleteAll(FdSuo.class);
        DataSupport.saveAll(list);
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
        li = DataSupport.where("suo_isuse = ? and user = ?", "1", NowUser.getuser()).find(FdSuo.class);
        if (li.size() > 0) {
            list.addAll(li);
        }
        jstjLv.setAdapter(jsTjAdapter);
        jsTjAdapter.setsetDateJsTjAdapter(list);
        Log.e("TAG+LIST", li.toString());
        Log.e("TAG+LIST", list.toString());
    }
}
