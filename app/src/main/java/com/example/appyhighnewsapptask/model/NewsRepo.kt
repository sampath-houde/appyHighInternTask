package com.example.appyhighnewsapptask.model

import com.example.appyhighnewsapptask.api.NewsApi
import com.example.appyhighnewsapptask.utils.BaseRepository

class NewsRepo(
    private val api: NewsApi
) : BaseRepository(){

    suspend fun getNews(keyword: String, apiKey: String) = safeApiCall {
        api.getNews(keyword, apiKey)
    }
}