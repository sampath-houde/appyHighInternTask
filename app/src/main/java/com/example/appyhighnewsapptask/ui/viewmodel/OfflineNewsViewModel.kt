package com.example.appyhighnewsapptask.ui

import androidx.lifecycle.*
import com.example.appyhighnewsapptask.database.entities.News
import com.example.appyhighnewsapptask.database.repo.NewsRepo
import kotlinx.coroutines.launch

class OfflineNewsViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    var readAllNews: LiveData<News> = newsRepo.readAllNews

    fun addAllNews(crop: News) = viewModelScope.launch {
        newsRepo.addAllNews(crop)
    }

}