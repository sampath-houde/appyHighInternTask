package com.example.appyhighnewsapptask.database

import android.content.Context
import androidx.room.*
import com.example.appyhighnewsapptask.database.entities.News
import com.example.appyhighnewsapptask.database.dao.NewsDao
import com.example.appyhighnewsapptask.utils.FilteredArticlesConvertor

@TypeConverters(FilteredArticlesConvertor::class)
@Database(entities = [News::class], version = 1, exportSchema = false )
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_table"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}