package com.android.multistream.ui.widgets

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.android.multistream.R
import com.google.android.material.card.MaterialCardView

class PlaceHolderMaterialCardView : MaterialCardView {
    val placeHolderWidth = (250 * resources.displayMetrics.density).toInt()
    lateinit var placeHolderAnimator: ObjectAnimator
    lateinit var placeHolderView: View
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
       placeHolderView = View(context).apply {
            this.layoutParams = LayoutParams(placeHolderWidth, MATCH_PARENT)
            this.background = resources.getDrawable(R.drawable.placeholder_gradient)

            this.alpha = 0.15f


        }

        addView(placeHolderView)
       placeHolderAnimator = ObjectAnimator.ofFloat(placeHolderView, "translationX", -placeHolderWidth.toFloat(), 1500f).apply {
            duration = 2000
            repeatCount = INFINITE
            start()
        }
    }

    fun cancelAnimation() {
        if (placeHolderAnimator.isRunning) placeHolderAnimator.cancel()
    }
}