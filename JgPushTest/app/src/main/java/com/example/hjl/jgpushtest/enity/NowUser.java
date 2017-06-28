package com.example.hjl.jgpushtest.enity;

import android.content.Context;

import com.example.hjl.jgpushtest.App;
import com.example.hjl.jgpushtest.util.SharePreferencesHelper;

/**
 * Created by hjl on 2017/6/26.
 */

public class NowUser {

    public static String getuser(Context context)
    {
        if ( SharePreferencesHelper.getInstance(context).getString("user")!= null&&
                !SharePreferencesHelper.getInstance(context).getString("user").equals("")) {
            return SharePreferencesHelper.getInstance(context).getString("user");
        }else {
            return  "";
        }


    }
    public static String getUID(Context context){
        if ( SharePreferencesHelper.getInstance(context).getString("useName")!= null&&
                !SharePreferencesHelper.getInstance(context).getString("useName").equals("")) {
            return SharePreferencesHelper.getInstance(context).getString("useName");
        }else {
            return  "";
        }

    }
    public static String getToken(Context context){
        if ( SharePreferencesHelper.getInstance(context).getString("token")!= null&&
                !SharePreferencesHelper.getInstance(context).getString("token").equals("")) {
            return SharePreferencesHelper.getInstance(context).getString("token");
        }else {
            return  "";
        }

    }
}
