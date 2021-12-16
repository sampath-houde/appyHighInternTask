package com.example.appyhighnewsapptask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.appyhighnewsapptask.R
import com.example.appyhighnewsapptask.api.NewsApi
import com.example.appyhighnewsapptask.database.NewsDatabase
import com.example.appyhighnewsapptask.database.entities.News
import com.example.appyhighnewsapptask.model.NewsRepo
import com.task.newsapp.utils.RetrofitInstance
import java.util.*
import com.example.appyhighnewsapptask.databinding.ActivityMainBinding
import com.example.appyhighnewsapptask.databinding.ViewAdBinding
import com.example.appyhighnewsapptask.model.FilteredArticle
import com.example.appyhighnewsapptask.utils.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var onlineViewModel: OnlineNewsViewModel
    private lateinit var offlineViewModel: OfflineNewsViewModel
    private lateinit var adapter: NewsAdapter
    private lateinit var countryCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        //Changing status bar color and disabling night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window?.statusBarColor = ContextCompat.getColor(applicationContext, R.color.light_grey5)


        MobileAds.initialize(this)


        initViewModel()
        initRecyclerView()
        countryCode = onlineViewModel.getCountryCode(this)
    }

    override fun onStart() {
        super.onStart()

        //When the app starts, will check if locally data is available, if not will make api call
        offlineViewModel.readAllNews.observe(this, {
            if(it == null) makeApiCall(countryCode)
            else {
                binding.recyclerView.adapter = adapter
                Log.d("SAMPATH", it.news.toString())
                setDataToAdapter(it.news)
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            makeApiCall(countryCode)
        }
    }

    private fun initRecyclerView() {

        //Initialize adapter class for the news feed
        adapter = NewsAdapter(this)
    }

    private fun initViewModel() {

        //Initialize viewmodel which make api calls
        val api = RetrofitInstance.buildApi(NewsApi::class.java)
        val onlineRepo = NewsRepo(api)
        val factory = ViewModelFactory(onlineRepo)
        onlineViewModel = ViewModelProvider(this, factory).get(OnlineNewsViewModel::class.java)

        //Initialize viewmodel which gives data from local source
        val dataSource = NewsDatabase.getDatabase(application).newsDao()
        val offlineRepo = com.example.appyhighnewsapptask.database.repo.NewsRepo(dataSource)
        val viewModelFactory = DatabaseViewModelFactory(offlineRepo)
        offlineViewModel = ViewModelProvider(this, viewModelFactory).get(OfflineNewsViewModel::class.java)

    }

    private fun makeApiCall(countryCode: String) {

        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.swipeRefresh.isRefreshing = true

        onlineViewModel.getNews(countryCode, Constants.API_KEY)
        onlineViewModel.newsList.observe(this@MainActivity, {response->
            binding.swipeRefresh.isRefreshing = false
            when(response) {
                is ApiResponseHandler.Success -> {
                    response.value.articles.also {articles->
                        if(articles.isNotEmpty()) {
                            val filteredArticles = filterArticles(articles)
                            saveDataToDatabase(filteredArticles)
                            setDataToAdapter(filteredArticles.news)

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

    private fun setDataToAdapter(filteredArticles: List<FilteredArticle>) {
        binding.banner.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.recyclerView.also {
            it.adapter = adapter
            adapter.setData(filteredArticles)
        }
    }

    private fun saveDataToDatabase(filteredArticles: News) {
        offlineViewModel.readAllNews.removeObservers(this)
        offlineViewModel.addAllNews(filteredArticles)
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