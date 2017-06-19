package com.example.hjl.jgpushtest.suoactivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.hjl.jgpushtest.astuetz.BaseActivity;
import com.example.hjl.jgpushtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoMainActivity extends BaseActivity {
    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    @Bind(R.id.layFrame)
    FrameLayout frameLayout;

    SuoCZGLfragment czgLfragment;
    SuoCRKfragment crKfragment;
    SuoWDfragment wDfragment;
    SuoTZCXfragment tzcXfragment;


    @Override
    protected void onResume() {
/**
 * SCREEN_ORIENTATION_PORTRAIT 设置强制竖屏
 * SCREEN_ORIENTATION_LANDSCAPE 设置强制横屏
 */
//        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    public int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suo_maintv);
        ButterKnife.bind(this);

        getWindow()
                .getDecorView()
                .findViewById(android.R.id.content)
                .setPadding(0, 0, 0, getNavigationBarHeight());//从新设置视图边距
        bottomNavigationBar.
                setMode(BottomNavigationBar.MODE_FIXED);
        //BottomNavigationBar.MODE_FIXED：固定大小
        bottomNavigationBar.
                setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);


        //Bottom navigation的背景色是设置的颜色（ActiveColor）
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_slideshow, "作业管理"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_camera, "出入库"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_gallery, "查询"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_manage, "我的"))
                .setActiveColor(R.color.gry)//设置主题颜色
                .setInActiveColor(R.color.dark)//图标未选中的颜色
                .setBarBackgroundColor(R.color.colorAccent)//图标被选中的颜色
                .setFirstSelectedPosition(0)
                .initialise();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                doit(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void doit(int position) {

        FragmentManager fm = this.getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (czgLfragment == null) {
                    czgLfragment = SuoCZGLfragment.getnewInstance_czgl("加锁");
                }
                transaction.replace(R.id.layFrame, czgLfragment);
                break;
            case 1:
                if (crKfragment == null) {
                    crKfragment = SuoCRKfragment.getnewInstance_crk("解锁");
                }
                transaction.replace(R.id.layFrame, crKfragment);
                break;
            case 2:
                if (tzcXfragment == null) {
                    tzcXfragment = SuoTZCXfragment.getnewInstance_tzcx("强拆");
                }
                transaction.replace(R.id.layFrame, tzcXfragment);
                break;
            case 3:
                if (wDfragment == null) {
                    wDfragment = SuoWDfragment.getnewInstance_wd("补锁");
                }
                transaction.replace(R.id.layFrame, wDfragment);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        czgLfragment = SuoCZGLfragment.getnewInstance_czgl("加锁");
        transaction.replace(R.id.layFrame, czgLfragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
