package com.example.appyhighnewsapptask.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appyhighnewsapptask.model.FilteredArticle

@Entity(tableName = "news_table")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val news: List<FilteredArticle>
)
