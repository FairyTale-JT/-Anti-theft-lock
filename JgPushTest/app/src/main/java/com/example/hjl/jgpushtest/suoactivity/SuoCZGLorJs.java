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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewLongItemClickListener;
import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.fragment.JiaSuoAdapter;
import com.example.hjl.jgpushtest.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by hjl on 2017/6/12.
 */

public class SuoCZGLorJs extends Fragment {
    private RecyclerView js_rv;
    private View view;
    private Context context;
    private JiaSuoAdapter jiaSuoAdapter;
    private List<Jsjv> list;
    private Button js_xzs, js_tj;
    SwipeRefreshLayout swipeRefreshLayout;

    private EditText suo1, suo2, cx_NO, dz_Ming;
    FdSuo fdSuo1 = null;
    FdSuo fdSuo2 = null;
    private String suo1_sbbh = null, suo1_id = null, suo1_ztbj = null, suo2_id = null, suo2_sbbh = null, suo2_ztbj = null;

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
        list = new ArrayList<>();
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
        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.suo_swip_item);
        suo1 = (EditText) view.findViewById(R.id.suo_suohao1_ed);
        suo2 = (EditText) view.findViewById(R.id.suo_suohao2_ed);
        cx_NO = (EditText) view.findViewById(R.id.suo_haoma_ed);
        dz_Ming = (EditText) view.findViewById(R.id.suo_daoz_ed);
        js_rv = (RecyclerView) view.findViewById(R.id.suo_js_rev_fragment);
        js_xzs = (Button) view.findViewById(R.id.suo_js_xzsuo);
        js_tj = (Button) view.findViewById(R.id.suo_js_tijiao);
        js_rv.setLayoutManager(new LinearLayoutManager(context));
        js_rv.setItemAnimator(new DefaultItemAnimator());
        jiaSuoAdapter = new JiaSuoAdapter();
        js_rv.setAdapter(jiaSuoAdapter);
    }

    /**
     * 下拉刷新的监听
     */
    private void swipCheak() {
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
//        for (int i = 0; i < 5; i++) {
//            Jsjv jsjv = new Jsjv();
//            jsjv.setCxh("100000" + i);
//            jsjv.setFz("北京");
//            jsjv.setDz("成都");
//            jsjv.setSuo1("" + i);
//            jsjv.setSuo2("" + i);
//            jsjv.setSzt("--");
//            list.add(jsjv);
//
//        }
//        jiaSuoAdapter.setDateJiaSuoAdapter(list);
        jiaSuoAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                //Toast.makeText(context.getApplicationContext(), s + "---点击", Toast.LENGTH_SHORT).show();
                Toasty.info(context.getApplicationContext(), s + "---点击", Toast.LENGTH_SHORT, true).show();
                UDialog(s);
            }
        });
        jiaSuoAdapter.setOnLongItemClickListener(new OnRecyclerViewLongItemClickListener() {
            @Override
            public void onLongItemClick(View view, int position) {
                String s = list.get(position).getCxh();
                //Toast.makeText(context.getApplicationContext(), s + "---长按", Toast.LENGTH_SHORT).show();
                Toasty.info(context.getApplicationContext(), s + "---长按", Toast.LENGTH_SHORT, true).show();
                DeleteDialog(s);
            }
        });
    }


    /**
     * 选择锁以及提交按钮
     */
    private void JsButton() {

        js_xzs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, JsTj.class);
                startActivityForResult(intent, 1);//带返回参数的跳转
            }
        });

        js_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            for (int i = 0; i < 5; i++) {
//                Jsjv jsjv = new Jsjv();
//                jsjv.setCxh("100000" + i);
//                jsjv.setFz("北京");
//                jsjv.setDz("成都");
//                jsjv.setSuo1("" + i);
//                jsjv.setSuo2("" + i);
//                jsjv.setSzt("--");
//                list.add(jsjv);
//
//            }

                Jsjv jsjv = new Jsjv();
                if (
                        (!TextUtils.isEmpty(cx_NO.getText())) && (!TextUtils.isEmpty(dz_Ming.getText()))
                                && (!TextUtils.isEmpty(suo1.getText()))
                        ) {
                    jsjv.setDz(cx_NO.getText().toString());
                    fdSuo1 = new FdSuo();
                    fdSuo2 = new FdSuo();
                    if (suo1_id != null) {
                        fdSuo1.setSuo_haoma(suo1_id);
                        fdSuo1.setSuo_sbBH(suo1_sbbh);
                        fdSuo1.setSuo_ztBJ(suo1_ztbj);
                        jsjv.setFdSuo1(fdSuo1);
                    }
                    if (suo2_id != null) {
                        fdSuo2.setSuo_haoma(suo2_id);
                        fdSuo2.setSuo_sbBH(suo2_sbbh);
                        fdSuo2.setSuo_ztBJ(suo2_ztbj);
                        jsjv.setFdSuo2(fdSuo2);
                    }
                    jsjv.setCxh(cx_NO.getText().toString());
                    jsjv.setFz(getFZ_bendi());
                    //添加数据，重新绑定Adater刷新界面
                    list.add(jsjv);
                    js_rv.setAdapter(jiaSuoAdapter);
                    jiaSuoAdapter.setDateJiaSuoAdapter(list);
                    cx_NO.setText("");
                    dz_Ming.setText("");
                    suo1.setText("");
                    suo2.setText("");
                    suo1_sbbh = null;
                    suo1_id = null;
                    suo1_ztbj = null;
                    suo2_id = null;
                    suo2_sbbh = null;
                    suo2_ztbj = null;
                } else {
                    ToastUtils.showToast(getContext(), "请输入完整信息");
                }
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
/**
 * do what
 */
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
        suo1_sbbh = null;
        suo1_id = null;
        suo1_ztbj = null;
        suo2_id = null;
        suo2_sbbh = null;
        suo2_ztbj = null;
        suo1.setText("");
        suo2.setText("");

        Log.e("TAG", "requestCode=" + requestCode + "resultCode" + resultCode);
        if (requestCode == 1) {
            if (resultCode == SuoMainActivity.RESULT_OK) {
                //获取返回信息
                String string1 = data.getExtras().getString("suo1");
                String string2 = data.getExtras().getString("suo2");
                String sbbh1 = data.getExtras().getString("sbbh1");
                String sbbh2 = data.getExtras().getString("sbbh2");
                String ztbj1 = data.getExtras().getString("ztbj1");
                String ztbj2 = data.getExtras().getString("ztbj2");
                if (string2 != null) {
                    suo2.setText(string2);
                    suo2_id = string2;
                    suo2_sbbh = sbbh2;
                    suo2_ztbj = ztbj2;
                }
                if (string1 != null) {
                    suo1.setText(string1);
                    suo1_id = string1;
                    suo1_sbbh = sbbh1;
                    suo1_ztbj = ztbj1;
                }
                Toast.makeText(getContext(), "返回信息=" + string1, Toast.LENGTH_LONG);

            }

        } else {
            Toast.makeText(getContext(), "返回信息有问题", Toast.LENGTH_SHORT);
        }


    }

    /**
     * 获取该用户设置的发站
     *
     * @return
     */
    String getFZ_bendi() {
        return "x";
    }
}
