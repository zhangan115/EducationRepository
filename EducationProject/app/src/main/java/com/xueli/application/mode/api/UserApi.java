package com.xueli.application.mode.api;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.User;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 用户类api
 * Created by pingan on 2017/11/30.
 */

public interface UserApi {

    /**
     * 登陆
     *
     * @param info 数据
     * @return 回调
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("user/login.json")
    Observable<Bean<User>> userLogin(@Body() String info);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("user/appEdit.json")
    Observable<Bean<String>> updateUserInfo(@Body() String info);
}
