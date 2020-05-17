package com.android.multistream.ui.widgets.place_holder_material_card

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.LinearInterpolator
import com.android.multistream.R
import com.android.multistream.ui.widgets.place_holder_material_card.place_holder_view.StreamPlaceHolder
import com.google.android.material.card.MaterialCardView

class PlaceHolderMaterialCardView : MaterialCardView {
    val placeHolderWidth = (250 * resources.displayMetrics.density).toInt()
    lateinit var placeHolderAnimator: ValueAnimator
    lateinit var placeHolderView: View
    var addGradient = false

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
        context?.obtainStyledAttributes(attrs, R.styleable.PlaceHolderMaterialCardView)?.apply {
            addGradient = getBoolean(R.styleable.PlaceHolderMaterialCardView_addGradient, true)
            recycle()
        }
        placeHolderView = StreamPlaceHolder(context).also { streamPlaceHolder ->
            streamPlaceHolder.id = R.id.placeholder
            streamPlaceHolder.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)

            val alphaProperty = PropertyValuesHolder.ofInt("alpha", 220, 50, 220)
            placeHolderAnimator = ValueAnimator().apply {
                duration = 2000
                repeatCount = INFINITE
                setValues(alphaProperty)
                addUpdateListener {
                    streamPlaceHolder.placeHolderAlpha = it.getAnimatedValue("alpha") as Int
                    streamPlaceHolder.invalidate()
                }
                interpolator = LinearInterpolator()
                start()
            }
        }
        addView(placeHolderView)
    }

    fun cancelAnimation() {
        if (placeHolderAnimator.isRunning) placeHolderAnimator.cancel()
    }
}