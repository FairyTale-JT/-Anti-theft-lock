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
import com.example.hjl.jgpushtest.bendiapi.CallbackActivity;
import com.example.hjl.jgpushtest.enity.CsLb;
import com.example.hjl.jgpushtest.fragment.QCAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 强拆
 */

public class SuoCZGLorQc extends Fragment implements CallbackActivity {
    private View view;
    private RecyclerView qc_csuo;
    private List<CsLb> Cslist;
    private QCAdapter qcAdapter;

    public static SuoCZGLorQc getnewInstance_Qc(String param1) {
        SuoCZGLorQc my = new SuoCZGLorQc();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return my;
    }

    public static SuoCZGLorQc getnewInstance_Qc() {
        SuoCZGLorQc my = new SuoCZGLorQc();
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
            view = inflater.inflate(R.layout.suo_qc, container, false);
        }
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        init();
        initDate();
        return view;
    }

    private void init() {
        qc_csuo = (RecyclerView) view.findViewById(R.id.qc_csuo);
    }

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
        qc_csuo.setLayoutManager(new LinearLayoutManager(getContext()));
        qcAdapter = new QCAdapter(Cslist, this);
        qc_csuo.setAdapter(qcAdapter);
    }

    /**
     * 锁1和锁2点击
     *
     * @param v
     */
    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.suo_qc_suo1:
                QCialog(Cslist.get((Integer) v.getTag()).getSuo1_id());
                break;
            case R.id.suo_qc_suo2:
                QCialog(Cslist.get((Integer) v.getTag()).getSuo2_id());
                break;
        }
    }

    /**
     * 强拆拆锁对话框
     *
     * @param cxh
     */
    private void QCialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("车号/箱号为" + cxh + "强制拆锁?");
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
                builder.show().dismiss();
            }
        });
        builder.create().show();
    }
}
