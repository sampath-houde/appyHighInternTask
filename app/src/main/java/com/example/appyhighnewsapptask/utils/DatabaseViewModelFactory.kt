package com.example.appyhighnewsapptask.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appyhighnewsapptask.database.repo.NewsRepo
import com.example.appyhighnewsapptask.ui.OfflineNewsViewModel

class DatabaseViewModelFactory(
    private val repo: NewsRepo
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        when(repo) {
            else -> {
                if (modelClass.isAssignableFrom(OfflineNewsViewModel::class.java)) {
                    return OfflineNewsViewModel(repo) as T
                }
            }
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}