package com.android.multistream.ui.browse_fragment.view_pager_fragments.twitch_fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.util.Log
import android.view.*
import android.view.animation.BounceInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.databinding.SingleTopGamesListBinding
import com.android.multistream.databinding.TwitchTopGamesListBinding
import com.android.multistream.di.MainActivity.browse_fragment.view_pager_fragments.twitch_fragment.TwitchFragmentGamesScope
import com.android.multistream.network.twitch.models.v5.TopItem
import com.android.multistream.ui.MainActivity
import com.android.multistream.utils.MIXER
import com.android.multistream.utils.TWITCH
import kotlinx.android.synthetic.main.twitch_top_games_list.view.*
import javax.inject.Inject

@TwitchFragmentGamesScope
class TwitchTopGamesAdapter @Inject constructor() :
    RecyclerView.Adapter<TwitchTopGamesAdapter.MyViewHolder>() {
    lateinit var clickListener: OnGameCategoryListener
    var list: List<TopItem>? = null
        set(value) {
            val begin = if (field == null) 0 else field?.count()!! - 1
            field = value
            notifyItemRangeChanged(begin, value?.size!! - 1)
        }

    class MyViewHolder(val binding: SingleTopGamesListBinding) :
        RecyclerView.ViewHolder(binding.root), GestureDetector.OnGestureListener,
        View.OnTouchListener {
        lateinit var scaleYAnimation: ObjectAnimator
        lateinit var scaleXAnimation: ObjectAnimator
        var gestureListener: GestureDetector
        lateinit var animatorSet: AnimatorSet
        lateinit var elevationAnim: ObjectAnimator

        init {
            setupAnimators()
            gestureListener = GestureDetector(binding.root.context, this)
            binding.root.setOnTouchListener(this)
        }

        private fun setupAnimators() {

            scaleXAnimation = ObjectAnimator.ofFloat(binding.root, "scaleX", 1f,1.20f)
            scaleYAnimation = ObjectAnimator.ofFloat(binding.root, "scaleY", 1f, 1.20f)
            elevationAnim = ObjectAnimator.ofFloat(binding.root, "elevation", 1f, 10f)
            animatorSet = AnimatorSet().apply {
                interpolator = FastOutSlowInInterpolator()
                playTogether(
                    scaleYAnimation, scaleXAnimation, elevationAnim
                )
                duration = 250
            }
        }

        override fun onShowPress(e: MotionEvent?) {
            if (e?.action != MotionEvent.ACTION_SCROLL)     animatorSet.start()


        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            binding.onGameCategoryListener?.onGameClick(0, 0 , null, "sds", "sdsd", "29595")
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
            Log.d("TOUCHTEST", event?.action.toString())
            gestureListener.onTouchEvent(event)
            if (event?.action == 1 || event?.action == MotionEvent.ACTION_CANCEL) {
                animatorSet.reverse()
            }
            return true
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            SingleTopGamesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { it.onGameCategoryListener = this.clickListener }
        return MyViewHolder(binding)
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
}
