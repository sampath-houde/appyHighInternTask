package com.example.appyhighnewsapptask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appyhighnewsapptask.model.Article
import com.example.appyhighnewsapptask.model.NewsRepo
import com.example.appyhighnewsapptask.model.NewsResponse
import com.example.appyhighnewsapptask.utils.ApiResponseHandler
import kotlinx.coroutines.launch



class NewsViewModel(private val repository: NewsRepo): ViewModel() {


    private val _news_list: MutableLiveData<ApiResponseHandler<NewsResponse>> =
        MutableLiveData()

    val newsList: LiveData<ApiResponseHandler<NewsResponse>>
        get() = _news_list

    fun getNews(countryCode: String, apiKey: String) = viewModelScope.launch {
        _news_list.value = repository.getNews(countryCode, apiKey)
    }

    fun setAdsToList(list: List<Article>): MutableList<Article?> {
        val listWithAds = mutableListOf<Article?>()

        for(i in list.indices) {
            if(i%3==0) listWithAds.add(null)
            listWithAds.add(i, list[i])
        }
        return listWithAds
    }

}

