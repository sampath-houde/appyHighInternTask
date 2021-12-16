package com.example.appyhighnewsapptask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil
import com.example.appyhighnewsapptask.R
import com.example.appyhighnewsapptask.databinding.ViewNewsBinding
import com.example.appyhighnewsapptask.model.Article
import com.example.appyhighnewsapptask.utils.OnClickListener


class NewsAdapter(private val listner: OnClickListener): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var list: List<Article> = emptyList()

    inner class ViewHolder(val binding: ViewNewsBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(list: List<Article>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
         ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_news, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = list[position]
        holder.binding.btnRead.setOnClickListener {
            listner.onItemClicked(list[position].url)
        }
    }

    override fun getItemCount(): Int = list.size

}