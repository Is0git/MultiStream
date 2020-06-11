//package com.android.multistream.ui.player.fragments
//
//import android.content.Context
//import android.graphics.Rect
//import android.util.AttributeSet
//import android.util.Log
//import android.view.KeyEvent
//import android.view.MotionEvent
//import android.view.View
//import android.widget.FrameLayout
//import androidx.appcompat.app.AppCompatActivity
//import androidx.constraintlayout.motion.widget.MotionLayout
//import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
//import androidx.core.view.children
//import androidx.core.view.updateLayoutParams
//import androidx.fragment.app.FragmentContainerView
//import androidx.fragment.app.FragmentManager
//import com.android.player.R
//import com.android.player.ui.MultiStreamPlayerLayout
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
//import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
//
//class CustomMotion : MotionLayout, MotionLayout.TransitionListener {
//    companion object {
//        const val PLAYER_FULLSCREEN = 0
//        const val PLAYER_MINIMIZED = 1
//        const val PLAYER_HIDDEN = 2
//    }
//
//    var playerFragmentContainer: FrameLayout? = null
//    var initialResizeMode: Int? = null
//    var currentEnd: Int = R.id.end
//    var playerViewState =
//        PLAYER_FULLSCREEN
//        set(value) {
//            field = value
//            onStateChangeListener?.onStateChange(value)
//            when (value) {
//                PLAYER_FULLSCREEN -> {
//                    onFullScreen()
////                    val container = findViewById<FragmentContainerView>(com.android.multistream.R.id.nav_host_container)
////                    container.visibility = View.INVISIBLE
//                }
//                PLAYER_MINIMIZED -> {
//                    onMinimized()
//                }
//                PLAYER_HIDDEN -> {
//                    onHidden()
//                }
//            }
//        }
//    private var fragmentManager: FragmentManager? = null
//    private var transitionListener: TransitionListener? = null
//    private var onStateChangeListener: OnStateChangeListener? = null
//
//    constructor(context: Context?) : super(context)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    )
//
//    init {
//        this.setTransitionListener(this)
//        isFocusableInTouchMode = true
//        isFocusable = true
//    }
//
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        playerFragmentContainer = findViewById(R.id.player_fragment)
//    }
//
//
//    fun updateLayout() {
//        playerViewState = playerViewState
//    }
//
//
//
//
//
////    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
////        val playerLayout = getPlayerLayout() ?: return false
////        when (playerViewState) {
////            PLAYER_FULLSCREEN -> if (isOverLapping(
////                    playerLayout.playerView!!,
////                    event
////                )
////            ) {
////                if (playerLayout.settingsExpandAnimation != null && playerLayout.settingsExpandAnimation?.isExpanded!!) return false
////                if (playerLayout.emoticonPickerExpandAnimation != null && playerLayout.emoticonPickerExpandAnimation?.isExpanded!!) return false
////                onTouchEvent(event)
////            }
////            PLAYER_MINIMIZED -> return isOverLapping(
////                playerLayout.playerView!!,
////                event
////            )
////        }
////        return false
////    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d("SCROLLTAG", "CISTOMMONTOUCH: ${event?.action}")
//        return super.onTouchEvent(event)
//    }
//
//    fun setDefaultTransitionHandler(
//        fragmentManager: FragmentManager,
//        transitionListener: TransitionListener?
//    ) {
//        this.fragmentManager = fragmentManager
//        this.transitionListener = transitionListener
//    }
//
//
//
//
//}
//
