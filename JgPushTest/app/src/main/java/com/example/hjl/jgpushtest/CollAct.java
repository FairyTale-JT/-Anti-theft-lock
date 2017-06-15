package com.example.hjl.jgpushtest;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hjl.jgpushtest.beanClass.HttpResult;
import com.example.hjl.jgpushtest.beanClass.Subject;
import com.example.hjl.jgpushtest.fragment.MyFragmentPagerAdapter;
import com.example.hjl.jgpushtest.fragment.TstPageFragment;
import com.example.hjl.jgpushtest.http.ApiService;
import com.example.hjl.jgpushtest.http.HttpUtils;
import com.example.hjl.jgpushtest.http.Url;
import com.example.hjl.jgpushtest.myview.CustomDialog;
import com.example.hjl.jgpushtest.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by hjl on 2017/5/18.
 */

public class CollAct extends AppCompatActivity {
    private static final String TAG = CollAct.class.getSimpleName();
    RecyclerView re;
    List<Subscription> subscriptions=new ArrayList<>();

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onResume() {
/**
 * SCREEN_ORIENTATION_PORTRAIT 设置强制竖屏
 * SCREEN_ORIENTATION_LANDSCAPE 设置强制横屏
 */
//        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coll_tst);
        initToolbar();
        initFragment();
    }

    private void initFragment() {
        //Fragment+ViewPager+FragmentViewPager组合的使用
         viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);//设置默认viewPaer 0 为起始页面
        List<Fragment> data = new ArrayList<>();
        data.add(new TstPageFragment());
        data.add(new TstPageFragment());
        data.add(new TstPageFragment());
        data.add(new TstPageFragment());
        data.add(new TstPageFragment());

        //TabLayout
         tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0 ; i < 5 ; i ++){
            tabLayout.getTabAt(i).setText("头部"+(i+1));
        }
        tabLayout.getTabAt(0).select();//设置Tabat 0 为起始的Tablayout
    }

    private void initToolbar() {
        /**
         * 自定义Toolbar
         *
         */

        Toolbar toolbar=(Toolbar)findViewById(R.id.id_toolbar_re_coll);
        setSupportActionBar(toolbar);//继承自ActionBarActivity
        //隐藏Toolbar的标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /**
         * 设置菜单栏字体颜色
         */
        toolbar.setPopupTheme(R.style.menuTextColor);
        ImageButton ibt= (ImageButton) toolbar.findViewById(R.id.toolbar_num);
        ibt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            finish();

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.men, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        /*
         * 将actionBar的HomeButtonEnabled设为ture，
         *
         * 将会执行此case
         */
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_album:
//                Toast.makeText(this, "我的人生", Toast.LENGTH_LONG).show();
//                break;
            case R.id.action_collection:
                Toast.makeText(this, "我的人生2", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_card:
                Toast.makeText(this, "我的人生3", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "我的人生4", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_feed:
                Toast.makeText(this, "我的人生45", Toast.LENGTH_LONG).show();
                break;
            // 其他省略...
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void doGet() {
        final CustomDialog  customDialog=new CustomDialog(CollAct.this,R.style.loadstyle);


                    HttpUtils.getMy_Retrofit(Url.BASE_URL_TWO,CollAct.this).
                            create(ApiService.class).getTopMovie(0,9)
                            .subscribeOn(Schedulers.io())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    customDialog.show();
                                }
                            })
                            .subscribeOn(AndroidSchedulers.mainThread())//显示Dialog在主线程中
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<HttpResult<List<Subject>>>() {
                                @Override
                                public void onCompleted() {
                                    Log.e("TAG","连接");
                                    customDialog.dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e("TAG","错误");
                                    customDialog.dismiss();
                                }

                                @Override
                                public void onNext(HttpResult<List<Subject>> result) {
                                    Log.e("TAG","成功个");
                                    String str="";
                                    List<Subject> list=result.getSubjects();
                                    for (int i = 0; i < list.size(); i++) {
                                        str += "电影名：" + list.get(i).getTitle() + "\n"+"详细："+list.get(i).getCasts()+"\n";
                                    }
                                    ToastUtils.showToast(str);
                                }
                            });


    }
    void doGet2(){}
}
