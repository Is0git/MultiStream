package com.android.multistream.ui.widgets.hide_scroll_view.animations

import android.widget.ScrollView
import com.android.multistream.ui.widgets.hide_scroll_view.Animation
import com.android.multistream.ui.widgets.hide_scroll_view.HideScrollView

class TranslationXAnimation(
    rootView: ScrollView,
    topDivider: Float = 0f,
    bottomDivider: Float = 0f
) : Animation(rootView, "translationX", topDivider, bottomDivider, 0f, rootView.width.toFloat()) {
    override fun onTop(view: AnimView, relativeCoordinate: Float): Float {
        return if (view.direction == HideScrollView.RIGHT) rootView.width * (1 - relativeCoordinate / topDivider) else -rootView.width * (1 - relativeCoordinate / topDivider)
    }

    override fun onMiddle(view: AnimView, relativeCoordinate: Float): Float {
        return defaultAnimValue
    }

    override fun onBottom(view: AnimView, relativeCoordinate: Float): Float {
     return   if (view.direction == HideScrollView.RIGHT) {
         rootView.width * ((relativeCoordinate - (rootView.height - bottomDivider)) / bottomDivider)
        } else {
            -rootView.width * ((relativeCoordinate - (rootView.height - bottomDivider)) / bottomDivider)
        }
    }
}