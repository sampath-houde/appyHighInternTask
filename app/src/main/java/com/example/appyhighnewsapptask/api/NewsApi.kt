package com.example.appyhighnewsapptask.api

import com.example.appyhighnewsapptask.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNews(@Query("country") keyword: String, @Query("apiKey") key: String) : NewsResponse
}