package com.iso.multistream.utils.data_binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

const val HEIGHT = "460"
const val WIDTH = "710"

object ImageLoader {

    @JvmStatic
    @BindingAdapter("app:loadImage")
    fun loadImageTwitch(imageView: ImageView, url: String?) {
        val newUrl = addHeightAndWidth(url)
        Glide.with(imageView.context).load(newUrl).centerCrop().into(imageView)
    }

    fun loadImageWithProgressBar(imageView: ImageView, progressBar: ProgressBar, url: String?) {
        progressBar.visibility = View.VISIBLE
        Glide.with(imageView)
            .load(url)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.INVISIBLE
                    return false
                }

            }).into(imageView)

    }

    fun loadImageTwitchWithParams(imageView: ImageView, url: String?, height: Int, width: Int) {
        val newUrl = addHeightAndWidth(url, height.toString(), width.toString())
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