package com.android.multistream.ui.main.fragments.profile_fragment

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.material.appbar.AppBarLayout

class ProfileMotionLayout : MotionLayout, AppBarLayout.OnOffsetChangedListener {

    lateinit var appBarLayout: AppBarLayout

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
       val position = -verticalOffset/appBarLayout?.totalScrollRange?.toFloat()!!
        progress = position * 3
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        appBarLayout = parent as AppBarLayout
        appBarLayout.addOnOffsetChangedListener(this)
    }
}