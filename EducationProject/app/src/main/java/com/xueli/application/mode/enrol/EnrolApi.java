package com.xueli.application.mode.enrol;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.enrol.bean.MajorBean;
import com.xueli.application.mode.enrol.bean.SchoolBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface EnrolApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/school/list")
    Observable<Bean<List<SchoolBean>>> getSchoolList(@Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/specialtyCatalog/list")
    Observable<Bean<List<MajorBean>>> getMajorList(@Query("schoolId") Long schoolId, @Body() String string);


    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/maccount/signup")
    Observable<Bean<String>> uploadData(@Body() String string);


}
