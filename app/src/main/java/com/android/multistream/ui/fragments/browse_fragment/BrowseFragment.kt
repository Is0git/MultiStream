package com.android.multistream.ui.fragments.browse_fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.multistream.R
import com.android.multistream.databinding.BrowseFragmentBinding
import com.android.multistream.utils.ScreenUnit
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

    val transition by lazy {
        TransitionInflater.from(context)
            .inflateTransition(R.transition.games_list_expand_transition)
    }
    val headerAlphaAnim by lazy {
        ObjectAnimator.ofFloat(
            binding.stripeTabLayout.headerTextView,
            "alpha",
            1f,
            0f
        )
    }

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

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }


    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
//        Log.d("FLINGTEST", "FLINGED")
        if (e1?.y!! > e2?.y!!) {

            extendViewPager()

        } else {
            deExtendViewPager()
        }
        return true
    }


    fun extendViewPager() {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup, transition)
        binding.stripeTabLayout.layoutParams =
            ConstraintLayout.LayoutParams(MATCH_PARENT, ScreenUnit.convertDpToPixel(130f))


        binding.viewPagerCard.apply {
//            layoutParams = ConstraintLayout.LayoutParams(layoutParams.width, layoutParams.height)
//                .also { it.setMargins(0, 0, 0, 0) }
            radius = 0f
        }
        headerAlphaAnim.apply {
            startDelay = 0L
            setFloatValues(1f, 0f)
            start()
        }

    }

    fun deExtendViewPager() {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup)
        binding.stripeTabLayout.layoutParams = ConstraintLayout.LayoutParams(
            MATCH_PARENT,
            (400 * context?.resources?.displayMetrics?.density!!).toInt()
        )
        binding.viewPagerCard.radius = 90f
        headerAlphaAnim.setFloatValues(0f, 1f)
        headerAlphaAnim.apply {
            startDelay = 500L
            start()
        }
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