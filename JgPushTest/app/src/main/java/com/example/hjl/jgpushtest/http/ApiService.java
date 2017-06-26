package com.example.hjl.jgpushtest.http;

import com.example.hjl.jgpushtest.beanClass.HttpResult;
import com.example.hjl.jgpushtest.beanClass.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hjl on 2017/5/26.
 */

public interface ApiService {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
