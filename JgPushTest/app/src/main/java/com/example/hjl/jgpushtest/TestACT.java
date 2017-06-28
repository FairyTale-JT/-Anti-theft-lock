package com.example.hjl.jgpushtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.hjl.jgpushtest.astuetz.BaseActivity;
import com.example.hjl.jgpushtest.suoactivity.SuoMainActivity;
import com.example.hjl.jgpushtest.util.SharePreferencesHelper;
import com.example.hjl.jgpushtest.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录界面
 */

public class TestACT extends BaseActivity {
    @Bind(R.id.username)
    EditText useName;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.suo_login1)
    Button denglu;
    boolean isDJ = false;
    String user_in, password_in;
    @Bind(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @Bind(R.id.jzmm)
    CheckBox jzmm;
    private boolean isEmpty;
    private SharedPreferences CheckSP;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_suo);
        ButterKnife.bind(this);
        ImmersionBar.with(this).transparentBar().init();
        CheckSP = getSharedPreferences("TestACT", Context.MODE_PRIVATE);
        editor = CheckSP.edit();
        CheckBoxchat();
        initdate();
        initview();

    }

    private void initview() {
        if (CheckSP.getBoolean("ISCHECK", false)) {
            jzmm.setChecked(true);
            useName.setText(CheckSP.getString("useName", ""));
            password.setText(CheckSP.getString("password", ""));
        } else {
            denglu.setEnabled(isDJ);//设置登录按钮不可点击
        }

        //设置登录按钮的监听事件
        denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 ToastUtils.showToast(TestACT.this,user_in+"\n"+password_in);
//                Toasty.success(TestACT.this, user_in + "\n" + password_in, Toast.LENGTH_SHORT).show();
                CheckBox();
            }
        });
    }

    /**
     * 记住密码
     */
    private void CheckBox() {

        user_in = useName.getText().toString();
        password_in = password.getText().toString();
        editor.putString("useName", user_in);
        editor.putString("password", password_in);
        editor.commit();
        Intent intent = new Intent(TestACT.this, SuoMainActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * 判断
     */
    private void CheckBoxchat() {
        jzmm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (jzmm.isChecked()) {
                    editor.putBoolean("ISCHECK", true);
                    editor.commit();
                } else {
                    editor.putString("useName", null);
                    editor.putString("password", null);
                    editor.putBoolean("ISCHECK", false);
                    editor.commit();
                }
            }
        });

    }

    private void initdate() {

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
                    useName.setText(str1);
                    useName.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                String s1, s2;
                s1 = useName.getText().toString().trim();
                s2 = password.getText().toString().trim();
                if (s1 != null && s2 != null && s1.length() > 8 && s2.length() > 8) {
                    //设置按钮可点击
                    denglu.setEnabled(true);
                    //设置按钮为正常状态
//                    denglu.setPressed(true);
                    denglu.setBackgroundColor(TestACT.this.getResources().getColor(R.color.my));

                } else {
                    //设置按钮不可点击
                    denglu.setEnabled(false);
                    //设置按钮为按下状态
//                    denglu.setPressed(false);
                    denglu.setBackgroundColor(TestACT.this.getResources().getColor(R.color.gry));
                }
            }
        };
        TextWatcher textWatch1 = new TextWatcher() {
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
                    password.setText(str1);
                    password.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //s:变化后的所有字符
                String s1, s2;
                s1 = useName.getText().toString().trim();
                s2 = password.getText().toString().trim();
                if (s1 != null && s2 != null && s1.length() > 8 && s2.length() > 8) {
                    //设置按钮可点击
                    denglu.setEnabled(true);
                    //设置按钮为正常状态
//                    denglu.setPressed(true);
                    denglu.setBackgroundColor(TestACT.this.getResources().getColor(R.color.my));

                } else {
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
        password.addTextChangedListener(textWatch1);

    }

}
