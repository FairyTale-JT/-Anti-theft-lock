package com.example.hjl.jgpushtest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjl.jgpushtest.CollAct;
import com.example.hjl.jgpushtest.R;
import com.example.hjl.jgpushtest.beanClass.HttpResult;
import com.example.hjl.jgpushtest.beanClass.Subject;
import com.example.hjl.jgpushtest.http.ApiService;
import com.example.hjl.jgpushtest.http.HttpUtils;
import com.example.hjl.jgpushtest.http.Url;
import com.example.hjl.jgpushtest.myview.CustomDialog;
import com.example.hjl.jgpushtest.util.ToastUtils;

import java.util.List;


import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static android.support.v7.widget.RecyclerView.*;


/**
 * Created by hjl on 2017/5/19.
 */

public class TstPageFragment extends Fragment {
    public static final String ARGS_PAGE = "args_page";
    private int mPage;
    private RecyclerView re;
    private TextView tv;
    private SwipeRefreshLayout swipeRefreshLayout;
    MyGrAdapter adapter;
    int flag;
    private Subscription subscription;

    public static TstPageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        TstPageFragment fragment = new TstPageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARGS_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tst_ragment_item1,container,false);
        tv= (TextView) view.findViewById(R.id.textView);
        re= (RecyclerView) view.findViewById(R.id.rev_fragment);
       swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swip_item);
        tv.setText("第"+mPage+"页");
        re.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new MyGrAdapter();

        re.setAdapter(adapter);

       swipCheak();

        return view;
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
     * 适配器
     */
    private class MyGrAdapter extends RecyclerView.Adapter<ViewHolder>{
                 List<Subject> list;
       void setData(List<Subject> list){
            this.list=list;
           notifyDataSetChanged();
        }
        public MyGrAdapter(){

        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,parent,false)) {
            };
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
         TextView textView= (TextView) holder.itemView.findViewById(R.id.fg_tv);
            if (list!=null&&list.size()>0) {
                textView.setText(list.get(position).getTitle());
            }

        }
        @Override
        public int getItemCount() {

            if (list==null){
                return 1;
            }else {
                return list.size()==0?1:list.size();
            }

        }
    }
    private void doGet() {
        final CustomDialog customDialog=new CustomDialog(getContext(),R.style.loadstyle);


        subscription=  HttpUtils.getMy_Retrofit(Url.BASE_URL_TWO,getContext()).
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
                        adapter.setData(list);


                    }
                });
//取消订阅
//         subscription.unsubscribe();
    }
}