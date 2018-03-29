package com.xueli.application.mode.exam;

import com.xueli.application.mode.bean.Bean;
import com.xueli.application.mode.bean.exam.ExamList;
import com.xueli.application.mode.bean.exam.PaperCollection;
import com.xueli.application.mode.bean.exam.PaperSections;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 考试类api
 * Created by pingan on 2018/3/25.
 */

public interface ExamApi {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/paper/objects")
    Observable<Bean<List<ExamList>>> getExamList(@QueryMap() Map<String, String> map, @Body() String string);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/paper/paperQuestion")
    Observable<Bean<List<PaperSections>>> getExamPaperQuesting(@Query("id") long id, @Body() String string);


    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST("api/usercollect/insert")
    Observable<Bean<PaperCollection>> postPaperCollection(@Query("paperQuestionId") long paperQuestionId
            , @Query("accountId") long accountId, @Body() String string);

    @POST("api/usercollect/del")
    Observable<Bean<PaperCollection>> cancelPaperCollection(@Query("id") long id, @Body() String string);

    @GET("api/usercollect/list")
    Observable<Bean<List<PaperSections>>> getMyColletion(@Query("accountId") long accountId, @Query("token") String string);

    @GET("api/usercollect/list")
    Observable<Bean<List<PaperSections>>> getMyColletion(@Query("accountId") long accountId
            , @Query("lastId") long lastId, @Query("token") String string);
}
