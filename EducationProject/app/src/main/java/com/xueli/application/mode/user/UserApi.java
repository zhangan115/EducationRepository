package com.xueli.application.mode.user;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.user.NewVersion;
import com.xueli.application.mode.bean.user.PaySchoolList;
import com.xueli.application.mode.bean.user.QQLoginBean;
import com.xueli.application.mode.bean.user.User;
import com.xueli.application.mode.bean.user.VerificationCode;
import com.xueli.application.mode.bean.user.VipContent;
import com.xueli.application.mode.bean.user.WeiXinLoginBean;
import com.xueli.application.mode.bean.user.WeiXinPayBean;


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
    Observable<Bean<User>> userReg(@Body() String info);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/forgetpassword")
    Observable<Bean<VerificationCode>> userForgetPass(@Query("phone") String phone);

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
    @POST("api/getNewVersionForapp")
    Observable<Bean<NewVersion>> newVersion(@Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/membercard/list")
    Observable<Bean<List<VipContent>>> vipCardList(@Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/membercard/pay")
    Observable<Bean<User>> payVip(@QueryMap() Map<String, String> map, @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/membercard/paySuccessful")
    Observable<Bean<User>> paySuccess(@QueryMap() Map<String, String> map, @Body() String string);

    @POST("api/membercard/getCard")
    Observable<Bean<String>> getOrderString(@QueryMap() Map<String, String> map, @Body() String string);

    /**
     * 缴费列表
     *
     * @param string
     * @return
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/tuition/list")
    Observable<Bean<List<PaySchoolList>>> paySchoolList(@Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/pay/cardOrTuition")
    Observable<Bean<String>> paySchool(@QueryMap() Map<String, String> map, @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/pay/cardOrTuition")
    Observable<Bean<WeiXinPayBean>> payWeiXin(@QueryMap() Map<String, String> map, @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/app/pay/back")
    Observable<Bean<User>> paySchoolSuccess(@QueryMap() Map<String, String> map, @Body() String string);

    /**
     * 微信 登陆 获取 数据
     */
    @GET("account/getAccessToken")
    Observable<Bean<WeiXinLoginBean>> getAccessToken(@Query("code") String code);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/updateUserInfo")
    Observable<Bean<User>> updateUserInfo(@QueryMap() Map<String, String> map);

    /**
     * QQ 登陆 获取 数据
     */
    @GET("account/getUserByQqOpenId")
    Observable<Bean<QQLoginBean>> getQQLoginInfo(@Query("openId") String openId);

    /**
     * 查询绑定的手机号是否存在用户
     * @param info 参数
     * @return 用户信息
     */
    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("account/bindingPhone")
    Observable<Bean<User>> queryUserInfo(@Body() String info);

}
