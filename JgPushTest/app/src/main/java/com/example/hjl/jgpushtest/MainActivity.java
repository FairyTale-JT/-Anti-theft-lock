package com.example.hjl.jgpushtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;


import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
//        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add("andfixdemo");
        set.add("andfixdemo1");
        set.add("andfixdemo2");
        set.add("andfixdemo3");//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签
        //设置别名
        JPushInterface.setAlias(this, "test1", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("xsx", i + "");
            }
        });

        setContentView(R.layout.activity_main);
        /**
         * 自定义Toolbar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);//继承自ActionBarActivity
        //隐藏Toolbar的标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setOverflowIcon(MainActivity.this.getResources().getDrawable(R.drawable.actionbar_more_icon));
        /**
         * 设置菜单栏风格
         */

        toolbar.findViewById(R.id.toolbar_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.men, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        /*
         * 将actionBar的HomeButtonEnabled设为ture，
         *
         * 将会执行此case
         */
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_album:
//                Toast.makeText(this, "我的人生", Toast.LENGTH_LONG).show();
//                break;
            case R.id.action_collection:
                Toast.makeText(this, "我的人生2", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_card:
                Toast.makeText(this, "我的人生3", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "我的人生4", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_feed:
                Toast.makeText(this, "我的人生45", Toast.LENGTH_LONG).show();
                break;
            // 其他省略...
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 防止没有Menu按键的手机不显示Menu：
     */
    private void setMenuAlwaysShow() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
