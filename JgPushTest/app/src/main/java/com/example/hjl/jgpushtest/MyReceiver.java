package com.example.hjl.jgpushtest;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.hjl.jgpushtest.beanClass.HttpFdsuo;
import com.example.hjl.jgpushtest.beanClass.HttpJsjv;
import com.example.hjl.jgpushtest.beanClass.HttpSuoSx;
import com.example.hjl.jgpushtest.enity.BinCZB;
import com.example.hjl.jgpushtest.enity.FdSuo;
import com.example.hjl.jgpushtest.enity.Jsjv;
import com.example.hjl.jgpushtest.enity.NowUser;
import com.example.hjl.jgpushtest.suoactivity.SuoCZGLorJs_Tjs;
import com.example.hjl.jgpushtest.suoactivity.SuoMainActivity;
import com.example.hjl.jgpushtest.util.FindTest;
import com.example.hjl.jgpushtest.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.e("TAG", "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.e("TAG", "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.e("TAG", "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e("TAG", "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.e("TAG", "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Log.e("TAG", "[MyReceiver] 用户点击打开了通知");

          //打开自定义的Activity
          Intent i = new Intent(context, SuoMainActivity.class);
          i.putExtras(bundle);
          //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
          context.startActivity(i);
            Log.e("TAG","[MyReceiver] 接收到推送下来的通知的ID: 并跳转 " );

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.e("TAG", "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..



        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e("TAG", "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.e("TAG", "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        //省略了
        return null;
    }

    //send msg to MainActivity
    private void processCustomMessage(final Context context, final Bundle bundle) {
       //省略了
        final String s=bundle.getString(JPushInterface.EXTRA_MESSAGE);
        Log.e("TAG推送改变布局",""+s);

        Observable.create(new Observable.OnSubscribe<FdSuo>() {
            @Override
            public void call(Subscriber<? super FdSuo> subscriber) {
                Throwable x = null;
                JSONObject json = null;
                FdSuo l=null;
                try {
                    String stss= "{\"a\":\"b\", \"c\":\"d\"}";
                    String sts=bundle.getString(JPushInterface.EXTRA_MESSAGE);
                    JSONObject a = new JSONObject(sts);
                 String ss =  a.getString("lockDeviceNo");
                    String ss1 =  a.getString("state");
                    l=new FdSuo();
                    l.setSuo_ztBJ(ss1);
                    l.setSuo_sbBH(ss);
                    Log.e("TAG推送改变布局json1STSS",ss);
                    Log.e("TAG推送改变布局json1STS",ss1);
//                    Log.e("TAG推送改变布局json",l.toString()+"lll");
                } catch (JSONException e) {
                    e.printStackTrace();
                    x=e;
                    Log.e("TAG推送改变布局json","2");
                }


                subscriber.onNext(l);
                subscriber.onCompleted();
               subscriber.onError(x);
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(Schedulers.io()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<FdSuo>() {
                    @Override

                    public void onNext(final FdSuo li) {
                        List<FdSuo> myL =
                                DataSupport
                                        .where("suo_sbBH = ? and user = ?", li.getSuo_sbBH(), NowUser.getuser(context))
                                        .find(FdSuo.class);
                        if (myL.size() > 0) {
                            ContentValues values = new ContentValues();
                            values.put("suo_ztBJ", li.getSuo_ztBJ());
                            DataSupport.updateAll(FdSuo.class, values, "suo_sbBH = ? and user = ?", myL.get(0).getSuo_sbBH(), NowUser.getuser(context));
                        } else {
                            li.save();
                            Log.e("TAGID", "li+++" + li.getId());
                        }
                        List<Jsjv> myRWlist = DataSupport.where("isOk > ? and user = ?", "0", NowUser.getuser(context)).find(Jsjv.class);
                        if (myRWlist.size() > 0) {
                            for (int i = 0; i < myRWlist.size(); i++) {
                                String suo1_sbbh = myRWlist.get(i).getFdSuo1_sbbh();
                                List<FdSuo> fdsuo1 = DataSupport
                                        .where("suo_sbBH = ? and user = ?", suo1_sbbh, NowUser.getuser(context))
                                        .find(FdSuo.class);
                                if (fdsuo1.size() > 0) {
                                    ContentValues values = new ContentValues();
                                    values.put("fdSuo1_ztbj", fdsuo1.get(0).getSuo_ztBJ());
                                    DataSupport.updateAll(Jsjv.class, values, "fdSuo1_sbbh = ? and user = ?", myRWlist.get(i).getFdSuo1_sbbh(), NowUser.getuser(context));
                                }
                                if (myRWlist.get(i).getFdSuo2_sbbh() != null &&
                                        !myRWlist.get(i).getFdSuo2_sbbh().equals("")) {
                                    String suo2_sbbh = myRWlist.get(i).getFdSuo2_sbbh();
                                    Log.e("TAGSuo2", suo2_sbbh);
                                    List<FdSuo> fdsuo2 = DataSupport

                                            .where("suo_sbBH = ? and user = ?", suo2_sbbh, NowUser.getuser(context))
                                            .find(FdSuo.class);
                                    if (fdsuo2.size() > 0) {
                                        ContentValues values = new ContentValues();
                                        values.put("fdSuo2_ztbj", fdsuo2.get(0).getSuo_ztBJ());
                                        DataSupport.updateAll(Jsjv.class, values, "fdSuo2_sbbh = ? and user = ?", myRWlist.get(i).getFdSuo2_sbbh(), NowUser.getuser(context));
                                    }
                                }
                            }
                        }
                        Intent intent = new Intent("tuisong");
                        intent.putExtra("change", "yes");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                       context.sendBroadcast(intent);
                       Log.e("TAG推送改变布局Next", li.toString())  ;
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {


                        Log.e("TAG", "e2");

                    }
                });

    }
}