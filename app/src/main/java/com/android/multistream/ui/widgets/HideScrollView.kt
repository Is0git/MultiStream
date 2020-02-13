package com.android.multistream.ui.widgets

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import kotlinx.coroutines.channels.Channel
import kotlin.math.absoluteValue


class HideScrollView : ScrollView {

    val hiddenViews: MutableList<View> by lazy { mutableListOf<View>() }
    var hideLength = 0
    var bottomHideLength = 0
    val array = IntArray(2)
    var yPerX = 0f
    val channel = Channel<Unit>(Channel.BUFFERED)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        hideLength = 263 + marginTop
        bottomHideLength = 100 + marginBottom
        yPerX = resources.displayMetrics.widthPixels / hideLength.toFloat()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun addHiddenView(view: View?) {
        view?.let { hiddenViews.add(it) }
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        Log.d("SCROLLV", "y: $t oldy: $oldt")
        hiddenViews.forEach {

    if (!it.isShown) return@forEach
            val windowPosition = it.getLocationInWindow(array)
            when {
                array[1] < hideLength -> {
                    if ((t - oldt).absoluteValue > 100) it.translationX = 0f
                    else {
                        it.translationX = hideLength.toFloat() * 3 - array[1] * 3
                    }

                }
                array[1] < height - bottomHideLength && array[1] > hideLength -> {
                    it.translationX = 0f
                }
                array[1] > height - bottomHideLength -> {
                    if ((t - oldt).absoluteValue > 100) it.translationX = 1800f
                    else {
                        Log.d("SCROLLV", "${bottomHideLength.toFloat() - array[1]}")
                        it.translationX =
                            if (-(height * 3 - array[1].toFloat() * 3) < 0) 0f else -(height * 3 - array[1].toFloat() * 3)
                    }
                }
            }
            super.onScrollChanged(l, t, oldl, oldt)
        }
    }

    fun playAnimation(view: View) =
        ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {


        }
}


//hiddenViews.forEach {
//
//    val windowPosition = it.getLocationInWindow(array)
//    when {
//        array[1] < hideLength -> {
//                    it.x = array[1].toFloat() + 75 / resources.displayMetrics.density

//            it.translationX = hideLength.toFloat() *3 - array[1] * 3
//
//
//
//        }
//                array[1] - 75 / resources.displayMetrics.density - 50 > height - hideLength -> {
//                    it.visibility = View.VISIBLE
//                }
//                array[1] - 75 / resources.displayMetrics.density > hideLength && array[1] < height - hideLength -> {
//                    it.visibility = View.VISIBLE
//    }
//}
