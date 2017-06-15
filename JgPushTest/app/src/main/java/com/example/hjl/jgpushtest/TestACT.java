package com.example.hjl.jgpushtest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hjl.jgpushtest.suoactivity.SuoMainActivity;
import com.example.hjl.jgpushtest.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hjl on 2017/6/8.
 */

public class TestACT extends AppCompatActivity{
    @Bind(R.id.username)
    EditText useName;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.suo_login1)
    Button denglu;
    boolean isDJ=false;
    String user_in,password_in;
    private Boolean isEmpty = true;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_suo);
        ButterKnife.bind(this);
        initview();
        initdate();

    }

    private void initview() {
        denglu.setEnabled(isDJ);//设置登录按钮不可点击
        //设置登录按钮的监听事件
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_in=useName.getText().toString();
                password_in=password.getText().toString();
                ToastUtils.showToast(TestACT.this,user_in+"\n"+password_in);
                Intent intent =new Intent(TestACT.this, SuoMainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void initdate() {


///////////////////////////////////
        TextWatcher textWatch=new TextWatcher() {
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

            }
            @Override
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                String s1,s2;
                s1=useName.getText().toString();
                s2=password.getText().toString();
                if(s1!=null&&s2!=null&&s1.length()>8&&s2.length()>8){
                    //设置按钮可点击
                    denglu.setEnabled(true);
                    //设置按钮为正常状态
//                    denglu.setPressed(true);
                    denglu.setBackgroundColor(TestACT.this.getResources().getColor(R.color.my));

                }else{
                    //设置按钮不可点击
                    denglu.setEnabled(false);
                    //设置按钮为按下状态
//                    denglu.setPressed(false);
                    denglu.setBackgroundColor(TestACT.this.getResources().getColor(R.color.gry));
                }
            }
        };
//监听EditText内容变化设置Button是否可点击
        useName.addTextChangedListener(textWatch);
        password.addTextChangedListener(textWatch);

    }

    @Override
    protected void onResume() {
/**
 * SCREEN_ORIENTATION_PORTRAIT 设置强制竖屏
 * SCREEN_ORIENTATION_LANDSCAPE 设置强制横屏
 */
//        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
}
