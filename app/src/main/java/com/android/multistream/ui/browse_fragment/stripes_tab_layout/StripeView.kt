package com.android.multistream.ui.browse_fragment.stripes_tab_layout

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import com.android.multistream.R

class StripeView : View {

    lateinit var rect: Rect

    lateinit var stripePaint: Paint
    lateinit var textPaint: TextPaint
    var selected = 0
    var selectedAlpha = 30
    set(value) {
        field = value
        invalidate()
    }

    var defaultAlpha = 30

    val stripes = mutableListOf<Stripe>()

    lateinit var underLine: Rect
    lateinit var underLinePaint: Paint


    var textMargin: Int = 650
    var marginStart = 0f
    var marginTop = 0f
    var headerTextSize: Float = 35f
    var headerText: String? = null
    var textColor: Int = Color.BLACK


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    fun init() {
        setWillNotDraw(false)
        underLinePaint = Paint().apply {
            this.alpha = 15
        }
        underLine = Rect()
        rect = Rect()
        stripePaint = Paint().apply {
            isAntiAlias = true


        }

        textPaint = TextPaint().apply {
            this.textSize = headerTextSize * resources.displayMetrics.density
            this.color = textColor
            this.isAntiAlias = true
            this.isFakeBoldText = true
            this.isLinearText = false
            this.typeface = ResourcesCompat.getFont(context, R.font.header_font)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSize(width, widthMeasureSpec)
        val height = resolveSize(height, heightMeasureSpec)
        marginStart = width * 0.10f
        marginTop = height * 0.23f
        setMeasuredDimension(width, height)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawStripes(canvas)
//        drawHeaderText(canvas)
    }

    fun addStripe(colorString: String) {
        stripes.add(Stripe(colorString))
        invalidate()

    }

    fun addStripes(stripes: Collection<Stripe>) {
        this.stripes.addAll(stripes)
    }

    fun drawStripes(canvas: Canvas?) {
        val stripeWidth = if (!stripes.isNullOrEmpty()) width / stripes.count() else return
        stripes.forEachIndexed { index, stripe ->
            rect.apply {
                top = 0
                bottom = this@StripeView.height
                left = index * stripeWidth
                right = index * stripeWidth + stripeWidth
            }

            underLine.apply {
                top = this@StripeView.height - 50
                bottom = this@StripeView.height - 45
                left = index * stripeWidth
                right = index * stripeWidth + stripeWidth
            }
            val paintColor = Color.parseColor(stripe.colorString)
            stripePaint.apply {

                this.alpha = if (selected == index) selectedAlpha else defaultAlpha
                shader = LinearGradient(
                    0f,
                    canvas?.height?.toFloat()!!,
                    0f,
                    0f,
                    Color.TRANSPARENT,
                    paintColor,
                    Shader.TileMode.CLAMP
                )
            }
            underLinePaint.apply {
                color = Color.parseColor(stripe.colorString)

            }

            canvas?.drawRect(rect, stripePaint)
            canvas?.drawRect(underLine, underLinePaint)
        }

    }

    private fun drawHeaderText(canvas: Canvas?) {
        if (!headerText.isNullOrBlank()) {
            if (headerTextSize * resources.displayMetrics.density + 450 > height) return

            canvas?.drawText(headerText!!,marginStart, marginTop, textPaint)

        }
    }

}