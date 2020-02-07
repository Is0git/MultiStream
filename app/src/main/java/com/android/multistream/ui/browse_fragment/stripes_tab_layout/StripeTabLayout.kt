package com.android.multistream.ui.browse_fragment.stripes_tab_layout

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StripeTabLayout : ConstraintLayout, TabLayout.OnTabSelectedListener {
    lateinit var typedArray: TypedArray
    var showStripes: Boolean = true
    lateinit var tabLayout: TabLayout
    lateinit var stripe: StripeView
    var headerText: String? = null
    var indicatorColor: Int = 0

    lateinit var stripesSelectionAnimator: ObjectAnimator



    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet)
    }


    fun init(attributeSet: AttributeSet? = null) {
        typedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.StripeTabLayout).apply {
                showStripes = getBoolean(R.styleable.StripeTabLayout_showStripes, true)
                indicatorColor = getColor(R.styleable.StripeTabLayout_indicatorColor, Color.BLUE)
                this@StripeTabLayout.headerText =
                    this.getString(R.styleable.StripeTabLayout_headerText)
                recycle()
            }


        tabLayout = TabLayout(context, null, R.style.tabCustomStyle).apply {
            this.id = R.id.tabLayout
            this.tabMode = TabLayout.MODE_FIXED
            this.tabGravity = TabLayout.GRAVITY_FILL
            this.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            this.isInlineLabel = true
            this.elevation = 2f
            this.isTabIndicatorFullWidth = true
            this.minimumWidth = 0
            this.tabRippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
            this.setSelectedTabIndicatorHeight(15)
            this.setSelectedTabIndicatorColor(indicatorColor)
            this.addOnTabSelectedListener(this@StripeTabLayout)
        }

//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.default_tab))
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.default_tab))
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.default_tab))
        addView(tabLayout)

        stripe = StripeView(context).apply {
            this.id = R.id.stripeView
            this.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            this.headerText = this@StripeTabLayout.headerText
        }

        addView(stripe)

        stripesSelectionAnimator = ObjectAnimator.ofInt(stripe, "selectedAlpha", 50, 100).apply {
            duration = 300
        }

        setConstraints()
        val stripes = mutableListOf<Stripe>(Stripe("#fe0000"), Stripe("#172450"), Stripe("#5e4eba"))
        addStripes(stripes)
    }


    private fun setConstraints() {

        val constraintSet = ConstraintSet().apply {
            clone(this@StripeTabLayout)
            connect(tabLayout.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM, (650).toInt())
        }
        setConstraintSet(constraintSet)
    }


    fun addTab(title: String, color: Color, tabViewId: Int = R.layout.default_tab) {
        tabLayout.addTab(tabLayout.newTab().apply {

            setCustomView(tabViewId)
        })
    }

    fun addStripe(colorString: String) {
        stripe.addStripe(colorString)
    }


    fun addStripes(stripes: Collection<Stripe>) {
        this.stripe.addStripes(stripes)
    }

    fun setupWithViewPager(viewPager: ViewPager2, setupTabs: (TabLayout.Tab, Int) -> Unit) {
        TabLayoutMediator(tabLayout, viewPager, true, setupTabs).attach()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        stripe.selected = tab?.position!!


        stripesSelectionAnimator.start()



    }


}