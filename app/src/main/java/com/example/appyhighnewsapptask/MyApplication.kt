package com.example.appyhighnewsapptask

import android.app.Application
import com.example.appyhighnewsapptask.utils.RemoteConfigUtils

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        RemoteConfigUtils.init()
    }
}