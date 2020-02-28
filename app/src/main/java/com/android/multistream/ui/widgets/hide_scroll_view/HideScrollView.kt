package com.android.multistream.ui.widgets.hide_scroll_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.core.view.marginTop
import com.android.multistream.R
import com.android.multistream.utils.ScreenUnit
import java.lang.NullPointerException
import java.lang.reflect.Method
import java.util.*

import kotlin.math.absoluteValue


class HideScrollView : ScrollView {


    companion object {
        val LEFT = 1
        val RIGHT = 2
        val ALPHA_ATTRIBUTE = 577
        val TRANSITIONX_ATTRIBUTE = 813

        fun setAttribute(value: Float, method: Method, view: View) {
            method.invoke(view, value)
        }
    }

    val hiddenViews: MutableList<HiddenView> by lazy { mutableListOf<HiddenView>() }
    var topHideLength = 0f
    var bottomHideLength = 0f
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
                typeArray.getDimension(
                    R.styleable.HideScrollView_bottomHideHeight,
                    0f
                ) + ScreenUnit.convertDpToPixel(56f)
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

    fun addHiddenView(view: View?, direction: Int = LEFT, attribute: String) {
        view?.let {
            val hiddenView =
                HiddenView(
                    view,
                    direction,
                    attribute
                )
            hiddenViews.add(hiddenView)
        }
    }

    fun addHiddenView(views: Collection<HiddenView>) {
        hiddenViews.addAll(views)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        hiddenViews.forEachIndexed { index, it ->
            if (!it.view.isShown) return@forEachIndexed

            val locationInScreen = IntArray(2) // view's position in scrren

            val parentLocationInScreen = IntArray(2) // parent view's position in screen

            it.view.getLocationOnScreen(locationInScreen)

            this.getLocationOnScreen(parentLocationInScreen)

            val relativeTop = locationInScreen[1] - parentLocationInScreen[1].toFloat()

            val value = when {
                relativeTop < topHeightMargin -> {
                    when {
                        (t - oldt).absoluteValue > 100 -> 0f
                        it.direction == RIGHT -> width * (1 - relativeTop / topHeightMargin)
                        else -> -width * (1 - relativeTop / topHeightMargin)
                    }
                }

                relativeTop < height - bottomHeight && relativeTop > topHeightMargin -> 0f


                relativeTop > height - bottomHeight -> {
                    if ((t - oldt).absoluteValue > 100) width.toFloat()
                    else {
                        if (it.direction == RIGHT) width * ((relativeTop - (height - bottomHeight)) / bottomHeight) else -width * ((relativeTop - (height - bottomHeight)) / bottomHeight)
                    }
                }

                else -> 0f
            }
            setAttribute(value, it.attributeMethod, it.view)

            super.onScrollChanged(l, t, oldl, oldt)
        }
    }


    data class HiddenView(var view: View, var direction: Int = LEFT, var attribute: String) {

        @SuppressLint("DefaultLocale")
        private var attributeFullName = "set${attribute.capitalize()}"


        var attributeMethod = kotlin.run {
          view.javaClass.methods.find { it.name == attributeFullName} ?: throw NullPointerException("method was not found, make sure an attribute name is correct")
        }
    }
}


