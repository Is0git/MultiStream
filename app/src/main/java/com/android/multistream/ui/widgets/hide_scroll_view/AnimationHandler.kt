package com.android.multistream.ui.widgets.hide_scroll_view

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import java.lang.reflect.Method

class AnimationHandler(val rootView: ScrollView) {

    var topDivider = 0f
    var bottomDivider = 0f

    companion object {
        const val VERTICAL_ORIENTATION = 1
        const val HORIZONTAL_ORIENTATION = 2

        fun setAttribute(value: Float, method: Method, view: View) {
            method.invoke(view, value)
        }

        fun getViewPositionInLayout(view: View, rootView: ViewGroup, orientation: Int): Float {
            val locationInScreen = IntArray(2) // view's position in scrren
            val parentLocationInScreen = IntArray(2) // parent view's position in screen
            view.getLocationOnScreen(locationInScreen)
            rootView.getLocationOnScreen(parentLocationInScreen)
            return when (orientation) {
                VERTICAL_ORIENTATION -> locationInScreen[1] - parentLocationInScreen[1].toFloat()
                HORIZONTAL_ORIENTATION -> locationInScreen[2] - parentLocationInScreen[2].toFloat()
                else -> 0f
            }
        }
    }

    fun animate(
        animView: Animation.AnimView,
        t: Int,
        oldt: Int
    ) {
        animView.apply {
            val relativeCoordinate = getViewPositionInLayout(view, rootView, VERTICAL_ORIENTATION)
            val value = when {
                relativeCoordinate < animation.topDivider && relativeCoordinate > 0 -> animation.onScrollTop(
                    this,
                    relativeCoordinate,
                    t,
                    oldt
                )
                relativeCoordinate < rootView.height - animation.bottomDivider && relativeCoordinate > animation.topDivider -> animation.onScrollMiddle(
                    this,
                    relativeCoordinate,
                    t,
                    oldt
                )
                relativeCoordinate > rootView.height - animation.bottomDivider && relativeCoordinate < rootView.height -> animation.onScrollBottom(
                    this,
                    relativeCoordinate,
                    t,
                    oldt
                )
                else -> return
            }
            setAttribute(value, attributeMethod, view)
        }
    }

}
