package com.android.multistream.util.data_binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
const val HEIGHT = "200"
const val WIDTH = "400"
object ImageLoader {

    @JvmStatic
    @BindingAdapter("app:loadImage")
    fun loadImage(imageView: ImageView, url: String?) {
        val newUrl = addHeightAndWidth(url)
        Glide.with(imageView.context).load(newUrl).centerCrop().into(imageView)
    }

    @JvmStatic
    fun addHeightAndWidth(url: String?) = url?.replace("{width}", WIDTH)?.replace("{height}", HEIGHT)

}