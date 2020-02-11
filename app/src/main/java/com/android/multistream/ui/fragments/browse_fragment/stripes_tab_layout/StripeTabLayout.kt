package com.android.multistream.ui.fragments.browse_fragment.stripes_tab_layout

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
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.android.multistream.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textview.MaterialTextView

class StripeTabLayout : ConstraintLayout, TabLayout.OnTabSelectedListener {
    lateinit var typedArray: TypedArray

    var showStripes: Boolean = true

    lateinit var tabLayout: TabLayout

    lateinit var stripe: StripeView

    lateinit var headerTextView: MaterialTextView

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
            this.background = resources.getDrawable(R.drawable.main_background_gradient)
            this.tabRippleColor = ColorStateList.valueOf(Color.TRANSPARENT)
            this.setSelectedTabIndicatorHeight(5)
            this.setSelectedTabIndicatorColor(indicatorColor)
            this.addOnTabSelectedListener(this@StripeTabLayout)
        }

//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.default_tab))
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.default_tab))
//        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.default_tab))


        headerTextView = MaterialTextView(context).apply {
            this.id = R.id.headerText
            this.setTypeface(ResourcesCompat.getFont(context, R.font.header_font))
            this.textSize = 17 * resources.displayMetrics.density
            this.layoutParams = LayoutParams(MATCH_PARENT, ConstraintSet.MATCH_CONSTRAINT)
            this.setTextColor(Color.BLACK)
            this.text = headerText
        }

        stripe = StripeView(context).apply {
            this.id = R.id.stripeView
            this.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            this.headerText = this@StripeTabLayout.headerText
        }


        addView(tabLayout)
        addView(stripe)
        addView(headerTextView)

        stripesSelectionAnimator = ObjectAnimator.ofInt(stripe, "selectedAlpha", 50, 200).apply {
            duration = 300
        }

        setConstraints()
        val stripes = mutableListOf<Stripe>(Stripe("#fe0000"), Stripe("#172450"), Stripe("#5e4eba"))
        addStripes(stripes)
    }


    private fun setConstraints() {

        val constraintSet = ConstraintSet().apply {
            clone(this@StripeTabLayout)

            connect(tabLayout.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM, (50).toInt())
            connect(tabLayout.id, ConstraintSet.TOP, R.id.headerText, ConstraintSet.BOTTOM)
            connect(R.id.headerText, ConstraintSet.TOP, id, ConstraintSet.TOP, (120* resources.displayMetrics.density).toInt())
            connect(R.id.headerText, ConstraintSet.START, id, ConstraintSet.START, 50)
            connect(R.id.headerText, ConstraintSet.END, id, ConstraintSet.END, 50)
            setVerticalBias(R.id.tabLayout, 1f)

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