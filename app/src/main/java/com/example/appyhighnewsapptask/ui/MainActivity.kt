package com.example.appyhighnewsapptask.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appyhighnewsapptask.R
import com.example.appyhighnewsapptask.api.NewsApi
import com.example.appyhighnewsapptask.model.NewsRepo
import com.task.newsapp.utils.RetrofitInstance
import java.util.*
import android.telephony.TelephonyManager
import com.example.appyhighnewsapptask.databinding.ActivityMainBinding
import com.example.appyhighnewsapptask.databinding.ViewAdBinding
import com.example.appyhighnewsapptask.model.Article
import com.example.appyhighnewsapptask.utils.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings


class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var countryCode: String
    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.white)


        MobileAds.initialize(this)

        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        countryCode = tm.networkCountryIso

        initViewModel()
        initRecyclerView()
        makeApiCall(countryCode)
    }

    private fun initRecyclerView() {
        adapter = NewsAdapter(this)
    }

    private fun initViewModel() {
        val api = RetrofitInstance.buildApi(NewsApi::class.java)
        val repo = NewsRepo(api)
        val factory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
        binding.lifecycleOwner = this
    }

    private fun makeApiCall(countryCode: String) {

        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

                viewModel.getNews(countryCode, Constants.API_KEY)
                viewModel.newsList.observe(this@MainActivity, {response->
                    binding.progressBar.visibility = View.GONE
                    when(response) {
                        is ApiResponseHandler.Success -> {
                            response.value.articles.also {articles->
                                if(articles.isNotEmpty()) {
                                    binding.banner.visibility = View.GONE
                                    binding.recyclerView.visibility = View.VISIBLE
                                    //val adList = viewModel.setAdsToList(articles)
                                    binding.recyclerView.also {
                                        it.adapter = adapter
                                        adapter.setData(articles as MutableList<Article>)
                                    }
                                } else {
                                    binding.recyclerView.visibility = View.GONE
                                    binding.banner.visibility = View.VISIBLE
                                }
                            }

                        }
                        is ApiResponseHandler.Failure -> {}
                    }
                })
    }

    override fun onItemClicked(link: String?) {
        if(link!=null) {
            val intent = Intent(this, NewsActivity::class.java)
            intent.putExtra(Constants.KEY_ARTICLE, link)
            startActivity(intent)
        } else Toast.makeText(this, "There is no article", Toast.LENGTH_SHORT).show()

    }

    override fun loadAd(adBinding: ViewAdBinding?) {
        val adRequest = AdRequest.Builder().build()
        adBinding?.adView?.loadAd(adRequest)
    }

}