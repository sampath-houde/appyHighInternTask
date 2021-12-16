package com.example.appyhighnewsapptask.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.appyhighnewsapptask.database.entities.News
import com.example.appyhighnewsapptask.model.Article
import com.example.appyhighnewsapptask.model.FilteredArticle
import com.example.appyhighnewsapptask.model.NewsResponse

@Dao
interface NewsDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllNews(news: News)

    @Query("SELECT * FROM news_table ORDER BY id DESC")
    fun readAllNews(): LiveData<News>

}