package com.example.appyhighnewsapptask.ui

import android.content.Context
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appyhighnewsapptask.model.Article
import com.example.appyhighnewsapptask.model.NewsRepo
import com.example.appyhighnewsapptask.model.NewsResponse
import com.example.appyhighnewsapptask.utils.ApiResponseHandler
import kotlinx.coroutines.launch



class OnlineNewsViewModel(private val repository: NewsRepo): ViewModel() {


    private val _news_list: MutableLiveData<ApiResponseHandler<NewsResponse>> =
        MutableLiveData()

    val newsList: LiveData<ApiResponseHandler<NewsResponse>>
        get() = _news_list

    fun getNews(countryCode: String, apiKey: String) = viewModelScope.launch {
        _news_list.value = repository.getNews(countryCode, apiKey)
    }

    fun getCountryCode(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkCountryIso!!
    }

}

