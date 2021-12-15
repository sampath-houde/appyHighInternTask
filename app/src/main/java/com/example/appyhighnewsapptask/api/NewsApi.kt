package com.example.appyhighnewsapptask.api

import com.example.appyhighnewsapptask.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(@Query("q") keyword: String, @Query("apiKey") key: String) : NewsResponse
}