package com.android.multistream.ui.widgets.hide_scroll_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import androidx.core.view.marginTop
import com.android.multistream.R
import com.android.multistream.utils.ScreenUnit


class HideScrollView : ScrollView {


    val animationHandler = AnimationHandler(this)

    companion object {
        //for translationAnimation
        val LEFT = 1
        val RIGHT = 2
    }

    val hiddenViews: MutableList<Animation.AnimView> by lazy { mutableListOf<Animation.AnimView>() }
    var bottomHeightMargin = animationHandler.topDivider
    var topHeightMargin =  animationHandler.bottomDivider

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
            topHeightMargin = typeArray.getDimension(R.styleable.HideScrollView_topHideHeight, 0f).also { animationHandler.topDivider = it }
            bottomHeightMargin =  (typeArray.getDimension(R.styleable.HideScrollView_bottomHideHeight, 0f) + ScreenUnit.convertDpToPixel(55f)).also {   animationHandler.bottomDivider = it }
            typeArray.recycle()
        }

        if (topHeightMargin<= 0f || bottomHeightMargin <= 0f) throw IllegalStateException("hide height can't be negative or equal to zero")
    }


    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    fun addHiddenView(view: View?, direction: Int = LEFT, animation: Animation) {
        view?.let {
            val hiddenView =
                Animation.AnimView(
                    view,
                    direction,
                    animation
                )
            hiddenViews.add(hiddenView)
        }


    }

    fun addHiddenView(view: View?, animation: Animation) {
        view?.let {
            val hiddenView =
                Animation.AnimView(
                    view,
                    animation
                )
            hiddenViews.add(hiddenView)
        }


    }

    fun addHiddenView(views: Collection<Animation.AnimView>) {
        hiddenViews.addAll(views)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        hiddenViews.forEachIndexed { index, it ->
            animationHandler.animate(it, t, oldt)
        }
    }


}


