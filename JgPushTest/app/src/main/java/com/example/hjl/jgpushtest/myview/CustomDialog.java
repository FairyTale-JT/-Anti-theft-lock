package com.example.hjl.jgpushtest.myview;

/**
 * Created by hjl on 2017/5/24.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.hjl.jgpushtest.R;


/**
 * 加载提醒对话框
 */
public class CustomDialog extends ProgressDialog
{
    public CustomDialog(Context context)
    {
        super(context);
    }

    public CustomDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.custom_sload_layout);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = 230;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        getWindow().setAttributes(params);
        getWindow().setGravity(Gravity.CENTER_VERTICAL
                | Gravity.CENTER_HORIZONTAL);

    }

    @Override
    public void show()
    {
        super.show();
    }
}
