package com.example.appyhighnewsapptask.database.repo

import androidx.lifecycle.LiveData
import com.example.appyhighnewsapptask.database.dao.NewsDao
import com.example.appyhighnewsapptask.database.entities.News
import com.example.appyhighnewsapptask.model.Article

class NewsRepo(private val newsDao: NewsDao) {

    val readAllNews: LiveData<News>
        get() = newsDao.readAllNews()


    suspend fun addAllNews(news: News) {
        newsDao.addAllNews(news)
    }
}