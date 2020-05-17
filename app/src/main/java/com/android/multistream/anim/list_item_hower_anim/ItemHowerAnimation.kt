package com.android.multistream.anim.list_item_hower_anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView

abstract class ItemHoverViewHolder<T : ViewDataBinding>(
    val binding: T,
    val spanCout: Int = 0,
    val onShowPress: Boolean = true
) :
    GestureDetector.OnGestureListener,
    View.OnTouchListener, RecyclerView.ViewHolder(binding.root) {

    lateinit var scaleYAnimation: ObjectAnimator
    lateinit var scaleXAnimation: ObjectAnimator
    var gestureListener: GestureDetector? = null
    lateinit var animatorSet: AnimatorSet
    lateinit var elevationAnim: ObjectAnimator
    lateinit var translationAnimation: ObjectAnimator
    lateinit var translationY: ObjectAnimator

    init {
        if (onShowPress) {
            setupAnimators()
            gestureListener = GestureDetector(binding.root.context, this)
            binding.root.setOnTouchListener(this)
        }
    }

    private fun setupAnimators() {
        scaleXAnimation = ObjectAnimator.ofFloat(binding.root, "scaleX", 1f, 1.20f)
        scaleYAnimation = ObjectAnimator.ofFloat(binding.root, "scaleY", 1f, 1.20f)
        elevationAnim = ObjectAnimator.ofFloat(binding.root, "elevation", 1f, 10f)
        translationAnimation = ObjectAnimator.ofFloat(binding.root, "translationX", 0f, 0f)
        translationY = ObjectAnimator.ofFloat(binding.root, "translationY", 0f, 80f)
        animatorSet = AnimatorSet().apply {
            interpolator = FastOutSlowInInterpolator()
            playTogether(
                scaleYAnimation, scaleXAnimation, elevationAnim, translationAnimation, translationY
            )
            duration = 250
        }
    }

    override fun onShowPress(e: MotionEvent?) {
        if (e?.action != MotionEvent.ACTION_SCROLL) focusGame(adapterPosition + 1)
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        navigate(binding)
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureListener?.onTouchEvent(event)
        if (event?.action == 1 || event?.action == MotionEvent.ACTION_CANCEL) {
            animatorSet.reverse()
        }
        return true
    }

    private fun focusGame(position: Int) {
        backgroundAnimation()
        if (spanCout != 0)
            when (position % spanCout) {
                1 -> translationAnimation.setFloatValues(0f, 50f)
                0 -> translationAnimation.setFloatValues(0f, -50f)
                else -> translationAnimation.setFloatValues(0f, 0f)
            }
        animatorSet.start()
    }

    abstract fun backgroundAnimation()
    abstract fun navigate(binding: T)
}
