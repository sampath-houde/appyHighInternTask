package com.example.appyhighnewsapptask.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.appyhighnewsapptask.R
import com.example.appyhighnewsapptask.databinding.ActivityNewsBinding
import com.example.appyhighnewsapptask.utils.Constants

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.light_grey5)


    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onStart() {

        super.onStart()



        val link = intent.getStringExtra(Constants.KEY_ARTICLE)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                binding.progress.visibility = View.VISIBLE
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)

                binding.progress.visibility = View.GONE
            }
        }

        if (link != null) {
            binding.webView.loadUrl(link)
        }
    }
}