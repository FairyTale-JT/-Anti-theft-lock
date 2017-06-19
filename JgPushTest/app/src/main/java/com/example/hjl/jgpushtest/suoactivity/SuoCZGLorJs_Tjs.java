package com.example.hjl.jgpushtest.suoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.astuetz.BaseActivity;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.fragment.JsTjAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/6/13.
 */

public class SuoCZGLorJs_Tjs extends BaseActivity {
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
        initView();
        initDate();
        JstjListView();
        tiaoZhuan_fanhui();
    }

    /**
     * 初始化界面
     */
    private void initView() {
    }
    /**
     * 初始化数据
     */
    private void initDate() {
        list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            FdSuo jsjv = new FdSuo();
            jsjv.setSuo_sbBH("10000" + i);
            jsjv.setSuo_haoma("10000" + i);
            jsjv.setSuo_cdTS("11" + i);
            jsjv.setSuo_ztBJ("出库");
            jsjv.setSuo_isuse(1);
            list.add(jsjv);
        }
        DataSupport.deleteAll(FdSuo.class);
        DataSupport.saveAll(list);
//        if (list!=null&&list.size()>0) {
//            List<FdSuo> li=new ArrayList<>();
//            for (int i = 0; i <list.size() ; i++) {
//               String sbbh= list.get(i).getSuo_sbBH();
//     if( DataSupport.where("suo_sbBH = ?",sbbh).find(FdSuo.class)==null){
//         li.add(list.get(i));
//            }
//            }
//            DataSupport.saveAll(li);
//        }

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
                        Toasty.warning(SuoCZGLorJs_Tjs.this, "最多可以选择两把锁", Toast.LENGTH_LONG).show();
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
        List<FdSuo> li=new ArrayList<>();
        li=DataSupport.where("suo_isuse > ?","0").find(FdSuo.class);
        jsTjAdapter = new JsTjAdapter();
        jstjLv.setAdapter(jsTjAdapter);
        jsTjAdapter.setsetDateJsTjAdapter(li);
    }
}
