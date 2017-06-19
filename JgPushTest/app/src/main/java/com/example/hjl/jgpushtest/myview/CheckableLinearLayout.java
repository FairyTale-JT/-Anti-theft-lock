package com.example.hjl.jgpushtest.myview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.example.hjl.jgpushtest.R;

/**
 * Listview选择改变LinerLayout背景颜色
 */

public class CheckableLinearLayout extends LinearLayout implements Checkable {
    private boolean mChecked;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundDrawable(checked ? new ColorDrawable(getResources().getColor(R.color.bt1_blue)) : null);//当选中时呈现蓝色
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
