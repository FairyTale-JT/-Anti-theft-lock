package com.example.hjl.jgpushtest.suoactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.bendiapi.OnRecyclerViewItemClickListener;
import com.example.hjl.jgpushtest.enity.CsLb;
import com.example.hjl.jgpushtest.fragment.CSAdapter;
import com.example.hjl.jgpushtest.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 拆锁界面.
 */

public class SuoCZGLorCs extends Fragment {
    private View view;
    private RecyclerView re_csuo;
    private List<CsLb> Cslist;
    private CSAdapter csAdapter;

    public static SuoCZGLorCs getnewInstance_Cs(String param1) {
        SuoCZGLorCs my = new SuoCZGLorCs();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return my;

    }

    public static SuoCZGLorCs getnewInstance_Cs() {
        SuoCZGLorCs my = new SuoCZGLorCs();
        return my;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.suo_cs, container, false);
        }
        init();
        initDate();
        CsListview();
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");

        return view;
    }

    /**
     * 初始化控件
     */
    private void init() {
        re_csuo = (RecyclerView) view.findViewById(R.id.re_csuo);
    }

    /**
     * 数据
     */
    private void initDate() {
        Cslist = new ArrayList<CsLb>();
        for (int i = 0; i < 10; i++) {
            CsLb csLb = new CsLb();
            csLb.setCxh("10001" + i);
            csLb.setFz("北京");
            csLb.setDz("成都");
            csLb.setSuo1_id("10002" + i);
            csLb.setSuo2_id("10003" + i);
            csLb.setSuo1_ztbj("加锁");
            csLb.setSuo2_ztbj("加锁");
            Cslist.add(csLb);
        }
        re_csuo.setLayoutManager(new LinearLayoutManager(getContext()));
        csAdapter = new CSAdapter(Cslist);
        re_csuo.setAdapter(csAdapter);
    }

    private void CsListview() {
        csAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = Cslist.get(position).getCxh();
                CSialog(s);
            }
        });
    }

    /**
     * 加锁确认对话框
     *
     * @param cxh
     */
    private void CSialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("车号/箱号为" + cxh + "拆锁确认?");
        builder.setTitle("提示");
        builder.setPositiveButton("拆锁确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /**
                 * do what
                 */
           ToastUtils.showmyToasty_success(getContext(), "拆锁成功");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
           ToastUtils.showmyToasty_Er(getContext(), "取消拆锁");
           builder.show().dismiss();
            }
        });
        builder.create().show();
    }
}
