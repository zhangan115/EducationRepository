package com.xueli.application.mode.user;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.user.NewVersion;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.bean.user.VipContent;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/forgetpassword")
    Observable<Bean<VerificationCode>> userForgetPass(@Query("accountName") String accountName, @Query("phone") String phone);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/updatepassword")
    Observable<Bean<String>> userUpdatePass(@QueryMap() Map<String, String> map);

    /**
     * 文件上传
     *
     * @param partList 参数
     * @return 订阅
     */
    @POST("maccount/image/upload")
    @Multipart
    Observable<Bean<List<String>>> postFile(@Part List<MultipartBody.Part> partList);

    /**
     * 检测新版本
     *
     * @return 请求对象
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("version/latestCustomerApp.json")
    Observable<Bean<NewVersion>> newVersion(@Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/membercard/list")
    Observable<Bean<List<VipContent>>> vipCardList(@Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/membercard/pay")
    Observable<Bean<String>> payVip(@Body() String string);

}
