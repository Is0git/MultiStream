package com.android.multistream.ui.main.fragments.game_category_fragment

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.android.multistream.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import kotlin.math.absoluteValue

class CategoryAppBar : MotionLayout, AppBarLayout.OnOffsetChangedListener {

    lateinit var card: MaterialCardView

    var defaultCornerRadius = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val position = verticalOffset.absoluteValue / appBarLayout?.totalScrollRange?.toFloat()!!
        progress = position
        card.radius = defaultCornerRadius * (1 - position)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val appBar = (parent as AppBarLayout).also { it.addOnOffsetChangedListener(this) }
        card = (appBar.parent as ViewGroup).findViewById(R.id.channelsCard)
        defaultCornerRadius = card.radius
    }
}