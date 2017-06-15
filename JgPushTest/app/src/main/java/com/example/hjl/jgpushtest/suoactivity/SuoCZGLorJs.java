package com.example.hjl.jgpushtest.suoactivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewLongItemClickListener;
import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.fragment.JiaSuoAdapter;
import com.example.hjl.jgpushtest.fragment.JsjlAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoCZGLorJs extends Fragment {
    private RecyclerView js_rv;
    private View view;
    private Context context;
    private JsjlAdapter jsadapter;
    private JiaSuoAdapter jiaSuoAdapter;
    private List<Jsjv> list;
    private Button js_xzs,js_tj;
    SwipeRefreshLayout swipeRefreshLayout;
    private EditText suo1,suo2;

    public static SuoCZGLorJs getnewInstance_Js(String param1) {
        SuoCZGLorJs my = new SuoCZGLorJs();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return my;

    }

    public static SuoCZGLorJs getnewInstance_Js() {
        SuoCZGLorJs my = new SuoCZGLorJs();
        return my;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.suo_js2, container, false);
        }
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        initView();
        JsListview();
        JsButton();
        /**
         * 下拉刷新的监听
         */
        swipCheak();
        return view;
    }

    private void initView() {
        swipeRefreshLayout=
                (SwipeRefreshLayout) view.findViewById(R.id.suo_swip_item);
        suo1= (EditText) view.findViewById(R.id.suo_suohao1_ed);
        suo2= (EditText) view.findViewById(R.id.suo_suohao2_ed);
        js_rv = (RecyclerView) view.findViewById(R.id.suo_js_rev_fragment);
        js_xzs = (Button) view.findViewById(R.id.suo_js_xzsuo);
        js_tj= (Button) view.findViewById(R.id.suo_js_tijiao);
        js_rv.setLayoutManager(new LinearLayoutManager(context));
        js_rv.setItemAnimator(new DefaultItemAnimator());
//        jsadapter = new JsjlAdapter();
//        js_rv.setAdapter(jsadapter);
        jiaSuoAdapter=new JiaSuoAdapter();
        js_rv.setAdapter(jiaSuoAdapter);
    }

    /**
     * 下拉刷新的监听
     */
    private void swipCheak()   {
        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
        // 设置下拉进度的背景颜色，默认就是白色的
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // 开始刷新，设置当前为刷新状态
                        //swipeRefreshLayout.setRefreshing(true);
                        // 这里是主线程
                        // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                        // TODO 获取数据
                        doGet();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * 获取数据
     */
    private void doGet() {

    }

    //Listview 添加数据与操作
    private void JsListview() {

        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Jsjv jsjv = new Jsjv();
            jsjv.setCxh("100000" + i);
            jsjv.setFz("北京");
            jsjv.setDz("成都");
            jsjv.setSuo1("" + i);
            jsjv.setSuo2("" + i);
            jsjv.setSzt("--");
            list.add(jsjv);

        }
        jiaSuoAdapter.setDateJiaSuoAdapter(list);
        jiaSuoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                Toast.makeText(context.getApplicationContext(), s + "---点击", Toast.LENGTH_SHORT).show();
                UDialog(s);
            }
        });
        jiaSuoAdapter.setOnLongItemClickListener(new OnRecyclerViewLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                Toast.makeText(context.getApplicationContext(), s + "---长按", Toast.LENGTH_SHORT).show();
                DeleteDialog(s);
            }
        });
    }

    //选择锁
    private void JsButton() {

        js_xzs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, JsTj.class);
                startActivityForResult(intent, 1);//带返回参数的跳转
            }
        });

    }
    //加锁确认对话框
    private void UDialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("车号/箱号为" + cxh + "加锁确认?");
        builder.setTitle("提示");
        builder.setPositiveButton("加锁确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.create().dismiss();
            }
        });
        builder.create().show();

    }
    //删除对话框
    private void DeleteDialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定删除车号/箱号为" + cxh + "的记录?");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.create().dismiss();
            }
        });
        builder.create().show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);

            Log.e("TAG","requestCode="+requestCode+"resultCode"+resultCode);
            if (requestCode ==1 )
            {
                if (resultCode == SuoMainActivity.RESULT_OK)
                {
                    //获取返回信息
                    String string1 = data.getExtras().getString("suo1");
                    String string2 = data.getExtras().getString("suo2");
                    if (string2 != null) {
                        suo2.setText(string2);
                    }
                    if (string1 != null) {
                        suo1.setText(string1);
                    }
                    Toast.makeText(getContext(), "返回信息=" + string1, Toast.LENGTH_LONG);
                }
                else {
                    Toast.makeText(getContext(),"返回信息有问题",Toast.LENGTH_SHORT);
                }
            }


    }
}
