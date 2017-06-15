package com.example.hjl.jgpushtest.myprovider;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import com.example.hjl.jgpushtest.R;

/**
 * Created by hjl on 2017/5/9.
 */

public class MyProvider extends ActionProvider{
    Context context;
    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public MyProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        return null;
    }

    @Override
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        subMenu.add(context.getString(R.string.plus_group_chat))
        .setIcon(R.drawable.ofm_group_chat_icon)
        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return true;
            }
        });
        subMenu.add(context.getString(R.string.plus_add_friend))
                .setIcon(R.drawable.ofm_add_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.plus_video_chat))
                .setIcon(R.drawable.ofm_video_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.plus_scan))
                .setIcon(R.drawable.ofm_qrcode_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
        subMenu.add(context.getString(R.string.plus_take_photo))
                .setIcon(R.drawable.ofm_camera_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return false;
                    }
                });
    }

    @Override
    public boolean hasSubMenu() {
        return true;
    }
}
