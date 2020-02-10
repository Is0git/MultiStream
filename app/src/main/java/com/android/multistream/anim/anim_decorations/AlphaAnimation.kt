package com.android.multistream.anim.anim_decorations

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.view.View
import android.view.animation.Animation
import com.android.multistream.R

fun alphaAnimate(view: View, fromValue: Float, toValue: Float, repeat: Boolean = false) {
   val animator = ObjectAnimator.ofFloat(view.findViewById(R.id.viewPagerCard), "alpha", 1f, 0.1f, 1f).apply {
       this.duration = 2000

   }
    if (repeat) animator.repeatCount = INFINITE
    animator.start()

}