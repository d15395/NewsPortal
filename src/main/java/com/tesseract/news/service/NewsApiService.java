package com.tesseract.news.service;

import com.tesseract.news.models.NewsRecords;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface NewsApiService {

    @GET("v2/everything")
    Call<NewsRecords> getAllRecords(@Header("X-Api-Key") String authorization, @QueryMap Map<String, String> queryKeys);

    @GET("v2/top-headlines")
    Call<NewsRecords> getTopRecords(@Header("X-Api-Key") String authorization, @QueryMap Map<String, String> queryKeys);

}
