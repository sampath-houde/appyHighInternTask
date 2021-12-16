package com.example.appyhighnewsapptask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.appyhighnewsapptask.database.entities.News
import com.example.appyhighnewsapptask.model.Article
import com.example.appyhighnewsapptask.model.FilteredArticle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun isInternetAvailable(context: Context): Boolean {
    var isConnected: Boolean = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

fun filterArticles(articles: List<Article>): News {

    val news : News
    val list = mutableListOf<FilteredArticle>()
    articles.forEach { article->
        val filteredArticle = FilteredArticle(article.author, article.description, article.title, article.url, article.urlToImage)
        list.add(filteredArticle)
    }
    news = News(0, list)

    return news

}

class FilteredArticlesConvertor {
    @TypeConverter
    fun fromFilteredArticle(data: List<FilteredArticle>) : String {

        return Gson().toJson(data)
    }

    @TypeConverter
    fun toFilteredArticle(data: String) : List<FilteredArticle> {
        val listType: Type = object : TypeToken<List<FilteredArticle>>() {}.type
        return Gson().fromJson(data, listType)
    }

}