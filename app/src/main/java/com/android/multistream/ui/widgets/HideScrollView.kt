package com.android.multistream.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView

class HideScrollView : ScrollView {

    val hiddenViews: MutableList<View> by lazy { mutableListOf<View>() }
    val hideLength = 100
    val array = IntArray(2)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun addHiddenView(view: View?) {
        view?.let { hiddenViews.add(it) }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        hiddenViews.forEach {
            val windowPosition = it.getLocationInWindow(array)
            when {
                array[1] < hideLength -> {
                    it.visibility = View.INVISIBLE
                }
                array[1] - 50 > height - hideLength -> {
                    it.visibility = View.VISIBLE
                }
                array[1] > hideLength && array[1] < height - hideLength -> {
                    it.visibility = View.VISIBLE
                }
            }
        }

    }
}