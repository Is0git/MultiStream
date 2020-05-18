package com.android.multistream.utils.data_binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

const val HEIGHT = "460"
const val WIDTH = "610"

object ImageLoader {

    @JvmStatic
    @BindingAdapter("app:loadImage")
    fun loadImageTwitch(imageView: ImageView, url: String?) {
        val newUrl = addHeightAndWidth(url)
        Glide.with(imageView.context).load(newUrl).centerCrop().into(imageView)
    }

    //for image template url
    @JvmStatic
    fun addHeightAndWidth(url: String?, height: String = HEIGHT, width: String = WIDTH) =
        url?.replace("{width}", width)?.replace("{height}", height)

    fun getImageDrawableFromUrl(context: Context, url: String?) {
        Glide.with(context).load(addHeightAndWidth(url, "1000", "400"))
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                }
            })
    }

    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).centerCrop().into(imageView)
    }
}