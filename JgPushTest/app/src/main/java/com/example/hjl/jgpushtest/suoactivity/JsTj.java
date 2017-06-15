package com.example.hjl.jgpushtest.suoactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.fragment.JsTjAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13.
 */

public class JsTj extends AppCompatActivity {
    @Bind(R.id.jstj_bt1)
    Button jstjBt1;
    @Bind(R.id.jstj_et1)
    EditText jstjEt1;
    @Bind(R.id.jstj_bt2)
    Button jstjBt2;
    @Bind(R.id.jstj_lv)
    ListView jstjLv;
    private List<Jsjv> list,isChoseList;
    private JsTjAdapter jsTjAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_tj);
        ButterKnife.bind(this);
        jstjLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        JstjListView();
        tiaoZhuan_fanhui();
    }

    private void choseSuo() {
/*
             * 当为单选时，调用getCheckedItemPosition()获取选中的item的position
             */
        //为多选时方法如下
        SparseBooleanArray array = jstjLv.getCheckedItemPositions();
        isChoseList=new ArrayList<>();
        for (int x = 0; x < array.size(); x++) {
            int key = array.keyAt(x);
            boolean b = array.get(key);
            if(b){
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
                String date=null;
                String date2=null;
                Bundle bundle =new Bundle();
                if (isChoseList != null&&isChoseList.size()>0) {
                    if (isChoseList.size()==1) {
                        date=isChoseList.get(0).getSuo1().toString();
                        bundle.putString("suo1",date);
                    }
                    if (isChoseList.size()==2) {
                        date=isChoseList.get(0).getSuo1().toString();
                        date2=isChoseList.get(1).getSuo1().toString();
                        bundle.putString("suo1",date);
                        bundle.putString("suo2",date2);
                    }
                }


                //设置返回数据
                // 先设置ReaultCode,再设置存储数据的意图
                Intent intent=new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                //关闭当前activity
                finish();
            }
        });
    }

    private void JstjListView() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Jsjv jsjv = new Jsjv();
            jsjv.setJlh("10000" + i);
            jsjv.setSuo1("10000" + i);
            jsjv.setSbh("10000" + i);
            jsjv.setCcpc("10000" + i);
            jsjv.setCdts("10" + i);
            jsjv.setSzt("出库");
            list.add(jsjv);
        }
        jsTjAdapter = new JsTjAdapter(list, JsTj.this);
        jstjLv.setAdapter(jsTjAdapter);
    }
}
