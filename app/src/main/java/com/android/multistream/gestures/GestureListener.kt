package com.android.multistream.gestures

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class GestureListener(context: Context) : View.OnTouchListener, GestureDetector.OnGestureListener {

    val gestureDetector = GestureDetector(context, this)
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
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
}