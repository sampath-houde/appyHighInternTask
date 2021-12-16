package com.example.appyhighnewsapptask.utils

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigUtils {
    private const val TAG = "RemoteConfigUtils"

    private const val ADMOB_KEY = "Admobs"

    private lateinit var remoteConfig: FirebaseRemoteConfig

    private val DEFAULTS: HashMap<String, Any> = hashMapOf(ADMOB_KEY to true)

    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig() : FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }

        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(DEFAULTS)
            fetchAndActivate().addOnCompleteListener {
                Log.d(TAG, "Remote Config Completed")
            }
        }
        return remoteConfig
    }

    fun isAdmobsEnabled() : Boolean = remoteConfig.getBoolean(ADMOB_KEY)

}