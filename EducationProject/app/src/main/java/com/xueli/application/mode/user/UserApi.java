package com.xueli.application.mode.user;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;


import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 用户api
 * Created by pingan on 2018/3/25.
 */

public interface UserApi {

    /**
     * 用户登陆
     *
     * @param map 请求参数
     * @return 订阅
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/login")
    Observable<Bean<User>> userLogin(@QueryMap() Map<String, String> map);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("user/appEdit.json")
    Observable<Bean<String>> updateUserInfo(@Body() String info);

    @GET("account/sendCode")
    Observable<Bean<VerificationCode>> sendPhoneCode(@Query("phone") String phoneNum);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/reg")
    Observable<Bean<String>> userReg(@QueryMap() Map<String, String> map);

}
