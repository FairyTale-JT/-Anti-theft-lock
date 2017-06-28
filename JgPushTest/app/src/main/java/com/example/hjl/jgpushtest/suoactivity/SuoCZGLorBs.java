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
import com.example.hjl.jgpushtest.fragment.BSAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 补锁界面
 */

public class SuoCZGLorBs extends Fragment implements CallbackActivity {
    private View view;
    private RecyclerView bs_csuo;
    private List<CsLb> BSlist;
    private BSAdapter bsAdapter;

    public static SuoCZGLorBs getnewInstance_Bs(String param1) {
        SuoCZGLorBs my = new SuoCZGLorBs();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        my.setArguments(args);
        return my;
    }

    public static SuoCZGLorBs getnewInstance_Bs() {
        SuoCZGLorBs my = new SuoCZGLorBs();
        return my;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.suo_bs, container, false);
        }

        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        init();
        initData();
        return view;
    }

    private void init() {
        bs_csuo = (RecyclerView) view.findViewById(R.id.bs_csuo);
    }

    private void initData() {
        BSlist = new ArrayList<CsLb>();
        for (int i = 0; i < 10; i++) {
            CsLb csLb = new CsLb();
            csLb.setCxh("10001" + i);
            csLb.setFz("北京");
            csLb.setDz("成都");
            csLb.setSuo1_id("10003" + i);
            csLb.setSuo2_id("10004" + i);
            csLb.setSuo1_ztbj("加锁");
            csLb.setSuo2_ztbj("加锁");
            BSlist.add(csLb);
        }
        bs_csuo.setLayoutManager(new LinearLayoutManager(getContext()));
        bsAdapter = new BSAdapter(BSlist, this);
        bs_csuo.setAdapter(bsAdapter);
    }

    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.suo_bs_suo1:
                BSialog(BSlist.get((Integer) v.getTag()).getSuo1_id());
                break;
            case R.id.suo_bs_suo2:
                BSialog(BSlist.get((Integer) v.getTag()).getSuo2_id());
                break;
        }
    }

    /**
     * 强拆拆锁对话框
     *
     * @param cxh
     */
    private void BSialog(String cxh) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("车号/箱号为" + cxh + "补锁?");
        builder.setTitle("提示");
        builder.setPositiveButton("补锁确认", new DialogInterface.OnClickListener() {
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
