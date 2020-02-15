package com.android.multistream.ui.widgets.place_holder_material_card

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.android.multistream.R
import com.google.android.material.card.MaterialCardView

class PlaceHolderMaterialCardView : MaterialCardView {
    val placeHolderWidth = (250 * resources.displayMetrics.density).toInt()
    lateinit var placeHolderAnimator: ObjectAnimator
    lateinit var placeHolderView: View
    var addGradient = false

    constructor(context: Context?) : super(context) {init(context)}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {init(context, attrs)}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {init(context, attrs)}

    private fun init(context: Context?, attrs: AttributeSet? = null) {
        context?.obtainStyledAttributes(attrs, R.styleable.PlaceHolderMaterialCardView)?.apply {
            addGradient = getBoolean(R.styleable.PlaceHolderMaterialCardView_addGradient, true)
            recycle()
        }

        placeHolderView = View(context).apply {
            this.layoutParams = LayoutParams(placeHolderWidth, MATCH_PARENT)
            this.background = resources.getDrawable(R.drawable.placeholder_gradient)
            this.alpha = 0.15f

        }
        if (addGradient) {
            val gradientView = View(context).apply {
                this.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
                this.setBackgroundResource(R.drawable.main_background_gradient)
            }

            addView(gradientView)

        }

//        addView(placeHolderView)
        placeHolderAnimator = ObjectAnimator.ofFloat(
            placeHolderView,
            "translationX",
            -placeHolderWidth.toFloat(),
            1500f
        ).apply {
            duration = 2000
            repeatCount = INFINITE
            start()
        }
    }

    fun cancelAnimation() {
        if (placeHolderAnimator.isRunning) placeHolderAnimator.cancel()
    }
}