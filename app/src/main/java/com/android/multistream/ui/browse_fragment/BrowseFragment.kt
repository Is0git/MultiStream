package com.android.multistream.ui.browse_fragment

import android.animation.ObjectAnimator
import android.content.Context
import android.gesture.GestureOverlayView
import android.os.Bundle
import android.transition.*
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.utils.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class BrowseFragment : DaggerFragment(), View.OnTouchListener, GestureDetector.OnGestureListener {

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var browseViewModel: BrowseFragmentViewModel
    lateinit var binding: BrowseFragmentBinding
    lateinit var navController: NavController
    lateinit var gestureDetector: GestureDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TWITCHFRAGMENT", "onCreateView2")
        browseViewModel =
            ViewModelProviders.of(this, factory).get(BrowseFragmentViewModel::class.java)
        binding = BrowseFragmentBinding.inflate(inflater, container, false)
        gestureDetector = GestureDetector(context, this)
        binding.root.apply {

            setOnTouchListener(this@BrowseFragment)
        }
        setupViewPager()
        handleTabLayout()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }


    private fun setupViewPager() {
        binding.topGamesViewPager.adapter =
            GamesFragmentsViewPagerAdapter(childFragmentManager, lifecycle)
    }

    private fun handleTabLayout() {
        binding.stripeTabLayout.setupWithViewPager(binding.topGamesViewPager) { tab, i ->
            val id = when (i) {
                0 -> R.drawable.youtube
                1 -> R.drawable.twitch
                2 -> R.drawable.mixer
                else -> return@setupWithViewPager
            }

            tab.setCustomView(R.layout.default_tab)
            tab.customView?.findViewById<ImageView>(R.id.icon)
                ?.setImageDrawable(getDrawable(activity?.baseContext!!, id))
        }
    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean { return true}


        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d("FLINGTEST", "FLINGED")
            if (e1?.y!! > e2?.y!!) {
                val transition = TransitionInflater.from(context).inflateTransition(R.transition.games_list_expand_transition)
                TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)
                binding.stripeTabLayout.layoutParams =
                    ConstraintLayout.LayoutParams(MATCH_PARENT, 350)


                binding.viewPagerCard.radius = 0f

                ObjectAnimator.ofFloat(binding.stripeTabLayout.headerTextView, "alpha", 1f, 0f).start()

            } else {
                TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
                binding.stripeTabLayout.layoutParams =
                    ConstraintLayout.LayoutParams(MATCH_PARENT, (400 * context?.resources?.displayMetrics?.density!!).toInt())

                binding.viewPagerCard.radius = 25f
                ObjectAnimator.ofFloat(binding.stripeTabLayout.headerTextView, "alpha", 0f, 1f).start()
            }
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