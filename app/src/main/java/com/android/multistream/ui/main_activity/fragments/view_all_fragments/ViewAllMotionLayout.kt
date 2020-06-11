package com.android.multistream.ui.main_activity.fragments.view_all_fragments

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.google.android.material.appbar.AppBarLayout

class ViewAllMotionLayout : MotionLayout, AppBarLayout.OnOffsetChangedListener {

    lateinit var appBar: AppBarLayout
    lateinit var list: RecyclerView
    var defaultScaleX = 0.8f
    var defaultScaleY = 0.8f
    var defaultRotationX = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val position = -verticalOffset / appBarLayout?.totalScrollRange?.toFloat()!!
        progress = position
        Log.d("PROGRESSW", "$progress")
        list.scaleX = (defaultScaleX + (1f - defaultScaleX) * progress)
        list.scaleY = (defaultScaleY + (1f - defaultScaleY) * progress)
        list.rotationY = defaultRotationX * (1-progress)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        appBar = parent as AppBarLayout
        list = (appBar.parent as CoordinatorLayout).findViewById(R.id.viewAllList)
        defaultScaleX = list.scaleX
        defaultScaleY = list.scaleY
        defaultRotationX = list.rotationY
        appBar.addOnOffsetChangedListener(this)
    }
}