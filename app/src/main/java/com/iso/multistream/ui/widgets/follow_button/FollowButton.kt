package com.iso.multistream.ui.widgets.follow_button

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.google.android.material.button.MaterialButton
import com.iso.multistream.R

class FollowButton : MaterialButton {

    private var selectedColor: ColorStateList? = null
    private var unselectedColor: ColorStateList? = null
    var animDuration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        context.obtainStyledAttributes(attrs, R.styleable.FollowButton).apply {
            selectedColor = this.getColorStateList(R.styleable.FollowButton_selectedColor)
                ?: ResourcesCompat.getColorStateList(resources, R.color.colorAccent, null)
            unselectedColor = this.getColorStateList(R.styleable.FollowButton_unSelectedColor)
                ?: ResourcesCompat.getColorStateList(resources, R.color.colorAccent, null)
            val select = getBoolean(R.styleable.FollowButton_isSelected, false)
            isSelected = select
            recycle()
        }
    }

    override fun setSelected(selected: Boolean) {
        val pastSelection = isSelected
        super.setSelected(selected)
        if (pastSelection == selected) return else {
            iconTint = if (selected) {
                onSelectAnimate()
                backgroundTintList = unselectedColor
                selectedColor
            } else {
                onDeselectAnimate()
                backgroundTintList = selectedColor
                unselectedColor
            }
        }
    }

    private fun onSelectAnimate() {
        this.animate()
            .scaleX(1.2f)
            .scaleY(1.2f)
            .translationY(10f)
            .setDuration(animDuration)
            .setInterpolator(FastOutLinearInInterpolator())
            .start()
    }

    private fun onDeselectAnimate() {
        this.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .translationY(0f)
            .setDuration(animDuration)
            .setInterpolator(FastOutLinearInInterpolator())
            .start()
    }
}