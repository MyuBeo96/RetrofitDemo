package com.myubeo.retrofitdemo;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("/2.2/answers?order=desc&sort=activity&site=stackoverflow")
    Call<List<SOAnswersResponse>> getAnswers();

    @GET("/2.2/answers?order=desc&sort=activity&site=stackoverflow")
    Call<List<SOAnswersResponse>> getAnswers(@Query("tagged") String tags);
}
