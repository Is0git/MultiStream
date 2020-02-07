package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.view.*
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.utils.TWITCH
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchTopGamesAdapter @Inject constructor() :
    RecyclerView.Adapter<TwitchTopGamesAdapter.MyViewHolder>() {
    lateinit var clickListener: OnGameCategoryListener
    var spanCount = 0
    var list: List<TopItem>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { it.onGameCategoryListener = this.clickListener }
        return MyViewHolder(binding, spanCount)
    }

    override fun getItemCount(): Int = list?.count() ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            backgroundUrl = list?.get(position)?.game?.box?.large
            id = list?.get(position)?.game?._id.toString()
            gameName = list?.get(position)?.game?.name
            platformType = TWITCH
            viewersCurrent = list?.get(position)?.viewers
        }

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.spanCount = (recyclerView.layoutManager as GridLayoutManager).spanCount
    }

    class MyViewHolder(val binding: SingleTopGamesListBinding, var spanCount: Int) :
        RecyclerView.ViewHolder(binding.root), GestureDetector.OnGestureListener,
        View.OnTouchListener {
        lateinit var scaleYAnimation: ObjectAnimator
        lateinit var scaleXAnimation: ObjectAnimator
        var gestureListener: GestureDetector
        lateinit var animatorSet: AnimatorSet
        lateinit var elevationAnim: ObjectAnimator
        lateinit var translationAnimation: ObjectAnimator
        val boundsDimensions: IntArray by lazy {
            IntArray(2).also {
                it[0] = spanCount
                it[1] = spanCount
            }
        }

        init {
            setupAnimators()
            gestureListener = GestureDetector(binding.root.context, this)
            binding.root.setOnTouchListener(this)
        }

        private fun setupAnimators() {

            scaleXAnimation = ObjectAnimator.ofFloat(binding.root, "scaleX", 1f, 1.20f)
            scaleYAnimation = ObjectAnimator.ofFloat(binding.root, "scaleY", 1f, 1.20f)
            elevationAnim = ObjectAnimator.ofFloat(binding.root, "elevation", 1f, 10f)
            translationAnimation = ObjectAnimator.ofFloat(binding.root, "translationX", 0f, 0f)

            animatorSet = AnimatorSet().apply {
                interpolator = FastOutSlowInInterpolator()
                playTogether(
                    scaleYAnimation, scaleXAnimation, elevationAnim, translationAnimation
                )
                duration = 250
            }
        }

        override fun onShowPress(e: MotionEvent?) {
            if (e?.action != MotionEvent.ACTION_SCROLL) focusGame(adapterPosition+1)


        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            binding.onGameCategoryListener?.onGameClick(0, 0, null, "sds", "sdsd", "29595")
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
            gestureListener.onTouchEvent(event)
            if (event?.action == 1 || event?.action == MotionEvent.ACTION_CANCEL) {
                animatorSet.reverse()
            }
            return true
        }

        private fun focusGame(position: Int) {
            when(position  % spanCount) {
                1 -> translationAnimation.setFloatValues(0f, 50f)
                0 -> translationAnimation.setFloatValues(0f, -50f)
                else -> translationAnimation.setFloatValues(0f, 0f)
            }
            animatorSet.start()
        }
    }


}
