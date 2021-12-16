package com.example.appyhighnewsapptask.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.appyhighnewsapptask.R

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String?) {
    if(url != null) {
        Glide.with(view)
            .load(url)
            .error(R.drawable.ic_baseline_error_24)
            .placeholder(R.drawable.ic_baseline_search_24)
            .transform(CenterCrop(), RoundedCorners(12))
            .into(view)
    }

}