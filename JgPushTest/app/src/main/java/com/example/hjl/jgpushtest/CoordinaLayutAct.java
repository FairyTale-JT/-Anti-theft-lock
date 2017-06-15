package com.example.hjl.jgpushtest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hjl on 2017/5/15.
 */

public class CoordinaLayutAct extends AppCompatActivity {
    RecyclerView re;
    int a,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dds);
        re= (RecyclerView) findViewById(R.id.review);
        re.setLayoutManager(new LinearLayoutManager(this));
        re.addItemDecoration(new ItemDivider().setDividerWith(2).setDividerColor(Color.MAGENTA));
        re.setAdapter(new MyAdapter());
        initToolbar();
    }

    private void initToolbar() {
        /**
         * 自定义Toolbar
         *
         */
        a=R.id.id_toolbar_re;
        Toolbar toolbar=(Toolbar)findViewById(R.id.id_toolbar_re);
        setSupportActionBar(toolbar);//继承自ActionBarActivity
        //隐藏Toolbar的标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /**
         * 设置菜单栏字体颜色
         */
        toolbar.setPopupTheme(R.style.menuTextColor);

        toolbar.findViewById(R.id.toolbar_num).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false)) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView.findViewById(android.R.id.text1);
            tv.setText(String.valueOf(position));
//            holder.itemView.setBackgroundColor(Color.BLUE);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
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
}
