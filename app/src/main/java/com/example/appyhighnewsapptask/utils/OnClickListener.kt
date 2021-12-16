package com.example.appyhighnewsapptask.utils

import com.example.appyhighnewsapptask.databinding.ViewAdBinding

interface OnClickListener {
    fun onItemClicked(link: String?)

    fun loadAd(adBinding: ViewAdBinding?)
}