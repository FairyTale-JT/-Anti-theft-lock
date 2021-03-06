package com.example.hjl.jgpushtest.astuetz;

import android.view.Menu;

/**
 * Created by yiyi on 2016/12/26.
 */

public interface BaseFuncIml {
    /* 初始化数据方法 */
    void initData();

    /* 初始化UI控件方法 */
    void initView();

    /* 初始化事件监听方法 */
    void initListener();

    /* 初始化界面加载方法 */
    void initLoad();
    /*设置toolbar*/
    void initTheme();
    /*加载菜单栏*/
    void setMenu(Menu menu);
}
