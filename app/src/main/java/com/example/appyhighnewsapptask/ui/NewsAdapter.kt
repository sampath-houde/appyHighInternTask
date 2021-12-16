package com.example.appyhighnewsapptask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.appyhighnewsapptask.R
import com.example.appyhighnewsapptask.databinding.ViewAdBinding
import com.example.appyhighnewsapptask.databinding.ViewNewsBinding
import com.example.appyhighnewsapptask.model.FilteredArticle
import com.example.appyhighnewsapptask.utils.OnClickListener
import com.example.appyhighnewsapptask.utils.RemoteConfigUtils


class NewsAdapter(private val listner: OnClickListener): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private var list: List<FilteredArticle> = emptyList()
    private val AD_TYPE = 1
    private val CONTENT = 0
    private val isAdsEnabled = RemoteConfigUtils.isAdmobsEnabled()


    class MyViewHolder : RecyclerView.ViewHolder {
        var adBinding: ViewAdBinding? = null
        var newsBinding: ViewNewsBinding? = null

        constructor(binding: ViewAdBinding) : super(binding.root) {
            adBinding = binding
        }

        constructor(binding: ViewNewsBinding) : super(binding.root) {
            newsBinding = binding
        }
    }

    fun setData(list: List<FilteredArticle>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val viewType: Int
        viewType = if(isAdsEnabled)
            if (position % 4 == 2) AD_TYPE else CONTENT        //Every 4th position ad will be shown and 1st ad will be shown at position 3 because postion starts from 0
        else
            CONTENT
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MyViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding
        return if(viewType == AD_TYPE) {
            binding = DataBindingUtil.inflate(inflater, R.layout.view_ad, parent, false)
            MyViewHolder(binding as ViewAdBinding)
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.view_news, parent, false)
            MyViewHolder(binding as ViewNewsBinding)
        }

    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(isAdsEnabled){
            if(position % 4 != 2) {
                holder.newsBinding?.data = list[position]
                holder.newsBinding?.btnRead?.setOnClickListener {
                    listner.onItemClicked(list[position].url)
                }
            } else {
                listner.loadAd(holder.adBinding)
            }
        } else {
            holder.newsBinding?.data = list[position]
            holder.newsBinding?.btnRead?.setOnClickListener {
                listner.onItemClicked(list[position].url)
            }
        }



    }

    override fun getItemCount(): Int = list.size

}