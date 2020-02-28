package com.android.multistream.ui.widgets.hide_scroll_view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.view.marginTop
import com.android.multistream.R
import com.android.multistream.utils.ScreenUnit
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
    var bottomHeight = 0f
    var topHeightMargin = 0f

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }


    private fun init(context: Context?, attrs: AttributeSet? = null) {
        isSmoothScrollingEnabled = true
        val typeArray = context?.obtainStyledAttributes(attrs, R.styleable.HideScrollView)
        if (typeArray != null) {
            topHeightMargin = typeArray.getDimension(R.styleable.HideScrollView_topHideHeight, 0f)
            bottomHeight =
                typeArray.getDimension(R.styleable.HideScrollView_bottomHideHeight, 0f)
        }
        if (topHeightMargin <= 0f || bottomHeight <= 0f) throw IllegalStateException("hide height can't be negative or equal to zero")
        typeArray?.recycle()


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        topHideLength = topHeightMargin + marginTop
        bottomHideLength = bottomHeight
        yPerX = resources.displayMetrics.widthPixels / topHideLength
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

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

    fun addHiddenView(views: Collection<HiddenView>) {
        hiddenViews.addAll(views)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        hiddenViews.forEachIndexed {index, it ->
            if (!it.view.isShown) return@forEachIndexed
            it.view.getLocationInWindow(array)
            when {
                array[1] < topHideLength && array[1] > marginTop -> {

                    when(index) {

                        0 -> {
                            if ((t - oldt).absoluteValue > 100) it.view.translationX = 0f
                            else {
                                it.view.alpha = 1 * ((array[1] - marginTop.toFloat()) / topHeightMargin)
                            }
                        }


                        else ->   {
                            if ((t - oldt).absoluteValue > 100) it . view . translationX =
                                0f
                            else {
                                it.view.translationX =
                                    if (it.direction == RIGHT) width * (1 - (array[1] - marginTop.toFloat()) / topHeightMargin) else -width * (1 - (array[1] - marginTop.toFloat()) / topHeightMargin)
                            }
                        }

                    }
                }
                array[1] < height -(bottomHideLength + ScreenUnit.convertDpToPixel(56f))&& array[1] > topHideLength -> {
                    it.view.translationX = 0f
                }

                array[1] > height - (bottomHideLength  + ScreenUnit.convertDpToPixel(56f))&& array[1] < height - ScreenUnit.convertDpToPixel(56f) -> {
                    if ((t - oldt).absoluteValue > 100) it.view.translationX = width.toFloat()
                    else {
                        val translationX =
                            if (it.direction == RIGHT) width * ((array[1] - (height - bottomHideLength - ScreenUnit.convertDpToPixel(56f)) ) / bottomHideLength ) else -width * ((array[1] - (height - bottomHideLength-ScreenUnit.convertDpToPixel(56f) )) / bottomHideLength )
                        it.view.translationX = translationX
                    }
                }
            }
            super.onScrollChanged(l, t, oldl, oldt)
        }
    }


    data class HiddenView(var view: View, var direction: Int = LEFT)
}


