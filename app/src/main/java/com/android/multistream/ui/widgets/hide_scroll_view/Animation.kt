package com.android.multistream.ui.widgets.hide_scroll_view

import android.annotation.SuppressLint
import android.view.View
import android.widget.ScrollView
import kotlin.math.absoluteValue

abstract class Animation(
    val rootView: ScrollView,
    val animationAttribute: String,
    var topDivider: Float = 0f,
    var bottomDivider: Float = 0f,
    var defaultAnimValue: Float,
    var endValue: Float
) {

    companion object {
        val MAX_SCROLL_DETECTION = 100
    }

    val height = rootView.height

    val width = rootView.width

    fun onScrollTop(animView: AnimView, relativeCoordinate: Float, t: Int, oldt: Int): Float {
        return if ((t - oldt).absoluteValue > MAX_SCROLL_DETECTION) defaultAnimValue else onTop(
            animView,
            relativeCoordinate
        )
    }

    fun onScrollMiddle(animView: AnimView, relativeCoordinate: Float, t: Int, oldt: Int): Float {
        return if ((t - oldt).absoluteValue > MAX_SCROLL_DETECTION) defaultAnimValue else onMiddle(
            animView,
            relativeCoordinate
        )
    }

    fun onScrollBottom(animView: AnimView, relativeCoordinate: Float, t: Int, oldt: Int): Float {
        return if ((t - oldt).absoluteValue > MAX_SCROLL_DETECTION) endValue else onBottom(
            animView,
            relativeCoordinate
        )
    }

    abstract fun onTop(view: AnimView, relativeCoordinate: Float): Float
    abstract fun onMiddle(view: AnimView, relativeCoordinate: Float): Float
    abstract fun onBottom(view: AnimView, relativeCoordinate: Float): Float

    data class AnimView(val view: View, var animation: Animation) {

        var direction = 0

        constructor(view: View, direction: Int = HideScrollView.LEFT, animation: Animation) : this(
            view,
            animation
        ) {
            this.direction = direction
        }

        @SuppressLint("DefaultLocale")
        private var attributeFullName = "set${animation.animationAttribute.capitalize()}"

        var attributeMethod = kotlin.run {
            view.javaClass.methods.find { it.name == attributeFullName }
                ?: throw NullPointerException("method was not found, make sure an attribute name is correct")
        }
    }
}