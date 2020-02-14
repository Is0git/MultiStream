package com.android.multistream.ui.widgets.hide_scroll_view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.android.multistream.R
import java.lang.IllegalStateException
import kotlin.math.absoluteValue


class HideScrollView : ScrollView {

    companion object {
        val LEFT = 1
        val RIGHT = 2
    }

    val hiddenViews: MutableList<HiddenView> by lazy { mutableListOf<HiddenView>() }
    var topHideLength = 0f
    var bottomHideLength = 0f
    val array = IntArray(2)
    var yPerX = 0f
    var initialBottomHeight = 0f
    var initialTopHeight = 0f

    constructor(context: Context?) : super(context) {init(context)}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {init(context, attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {init(context, attrs)}


    private fun init(context: Context?, attrs: AttributeSet? = null) {
        val typeArray = context?.obtainStyledAttributes(attrs,R.styleable.HideScrollView)
        if (typeArray != null) {
            initialTopHeight = typeArray.getDimension(R.styleable.HideScrollView_topHideHeight, 0f)
            initialBottomHeight = typeArray.getDimension(R.styleable.HideScrollView_bottomHideHeight, 0f)
        }
        if (initialTopHeight <= 0f || initialBottomHeight <= 0f) throw IllegalStateException("hide height can't be negative or equal to zero")
        typeArray?.recycle()


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        topHideLength = initialTopHeight + marginTop
        bottomHideLength = initialBottomHeight + marginBottom
        yPerX = resources.displayMetrics.widthPixels / topHideLength
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun addHiddenView(view: View?, direction: Int = LEFT) {
        view?.let {
            val hiddenView =
                HiddenView(
                    view,
                    direction
                )
            hiddenViews.add(hiddenView)
        }
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        Log.d("SCROLLV", "y: $t oldy: $oldt")
        hiddenViews.forEach {
            if (!it.view.isShown) return@forEach
            it.view.getLocationInWindow(array)
            when {
                array[1] < topHideLength -> {
                    if ((t - oldt).absoluteValue > 100) it.view.translationX = 0f
                    else {
                        it.view.translationX = topHideLength.toFloat() * 3 - array[1] * 3
                    }
                }
                array[1] < height - bottomHideLength && array[1] > topHideLength -> {
                    it.view.translationX = 0f
                }
                array[1] > height - bottomHideLength -> {
                    if ((t - oldt).absoluteValue > 100) it.view.translationX = 1800f
                    else {
                        Log.d("SCROLLV", "${bottomHideLength.toFloat() - array[1]}")
                        it.view.translationX =
                            if (-(height * 3 - array[1].toFloat() * 3) < 0) 0f else -(height * 3 - array[1].toFloat() * 3)
                    }
                }
            }
            super.onScrollChanged(l, t, oldl, oldt)
        }
    }


    data class HiddenView(var view: View, var direction: Int = LEFT)
}


