package com.android.multistream.util.data_binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
const val HEIGHT = "400"
const val WIDTH = "200"
object ImageLoader {

    @JvmStatic
    @BindingAdapter("app:loadImage")
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).centerCrop().into(imageView)
    }

    @JvmStatic
    fun addHeightAndWidth(url: String?) = url?.replace("{width}", WIDTH)?.replace("{height}", HEIGHT)

}