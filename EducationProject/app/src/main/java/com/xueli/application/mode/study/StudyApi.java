package com.xueli.application.mode.study;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.school.SchoolBean;
import com.xueli.application.mode.bean.study.StudyMessage;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface StudyApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/msg/list")
    Observable<Bean<List<StudyMessage>>> getMessageList(@Query("range") long id
            , @Query("lastId") long lastId
            , @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/msg/list")
    Observable<Bean<List<SchoolBean>>> getSchoolList(@Query("range") long id
            , @Query("count") long count
            , @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/msg/list")
    Observable<Bean<List<StudyMessage>>> getMessageList(@Query("range") long id
            , @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/sysmsg/details")
    Observable<String> getWebUrl(@Query("id") long id, @Body() String string);


    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/msg/details")
    Observable<Bean<StudyMessage>> getMessageDetails(@Query("id") long id, @Body() String string);

}
