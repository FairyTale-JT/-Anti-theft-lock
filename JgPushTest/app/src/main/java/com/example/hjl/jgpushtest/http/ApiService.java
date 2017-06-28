package com.example.hjl.jgpushtest.http;

import com.example.hjl.jgpushtest.beanClass.HttpFaZhan;
import com.example.hjl.jgpushtest.beanClass.HttpFdsuo;
import com.example.hjl.jgpushtest.beanClass.HttpJsjv;
import com.example.hjl.jgpushtest.beanClass.HttpResult;
import com.example.hjl.jgpushtest.beanClass.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hjl on 2017/5/26.
 */

public interface ApiService {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
    //登录
    @POST("elelock/app/login.htmls")
    Observable<String> login(@Query("UID") String uid, @Query("password") String password);
    //获取锁
    @POST("elelock/app/seal/getHoldingLock.htmls")
    Observable<List<HttpFdsuo>> getHoldingLock(@Query("UID") String uid, @Query("token") String token);
    //获取发站属性
    @POST("elelock/app/queryRelativeStation.htmls")
    Observable<List<HttpFaZhan>> queryRelativeStation (@Query("UID") String uid, @Query("token") String token);
     //获取权限
    @POST("elelock/app/queryPerm.htmls")
    Observable<String[]> queryPerm(@Query("UID") String uid, @Query("token") String token);

   //确认加锁1
    @POST("elelock/app/seal/submitSeal.htmls")
    Observable <Integer> submitSealF(@Query("UID") String uid,@Query("token") String token,@Query("coachNo") String coachNo,@Query("startStationId") String startStationId,@Query("destStationId") String destStationId,@Query("deviceNo") String deviceNo,@Query("flag") int flag);
    //确认加锁2
    @POST("elelock/app/seal/submitSeal.htmls")
    Observable <Integer> submitSealT(@Query("UID") String uid,@Query("token") String token,@Query("coachNo") String coachNo,@Query("startStationId") String startStationId,@Query("destStationId") String destStationId,@Query("deviceNo") String deviceNo,@Query("flag") int flag,@Query("deviceNo") String deviceNo2,@Query("flag") int flag2);

    //拆锁
    @POST("elelock/app/seal/unlock.htmls")
    Observable<Integer>  unlock(@Query("UID") String uid,@Query("token") String token,@Query("deviceNo") String deviceNo);

    //根据车号箱号查询
    @POST("elelock/app/seal/querySealByCoach.htmls")
    Observable<HttpJsjv> querySealByCoach(@Query("UID") String uid,@Query("token") String token,@Query("coachNo") String coachNo);


    //根据发站查询
    @POST("elelock/app/seal/querySealByStart.htmls")
    Observable<List<HttpJsjv>> querySealByStart(@Query("UID") String uid,@Query("token") String token,@Query("startStationId") String startStationId);

}
