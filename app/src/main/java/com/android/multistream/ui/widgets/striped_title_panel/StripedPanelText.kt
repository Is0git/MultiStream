package com.android.multistream.ui.widgets.striped_title_panel

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.android.multistream.R
import com.android.multistream.utils.ScreenUnit
import com.google.android.material.textview.MaterialTextView
import kotlin.reflect.KClass

class StripedPanelText : MaterialTextView {
    companion object {
        val ALIGNMENT_NONE = 0
        val ALIGNMENT_LEFT = 1
        val ALIGNMENT_RIGHT = 2
    }
    val view2: View = RecyclerView(context)
    var materialTextAlignment = 0
    var sideMargin = 0f
    var viewsMargin = 0f
    var titleText: String? = "TITLE"
    var titleTextSize = 0f
    var titleTextColor = 0
    lateinit var titleTextView: MaterialTextView

    constructor(context: Context?) : super(context!!) {

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context!!, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }


    private fun init(context: Context?, attrs: AttributeSet?) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.StripedPanelText)

        if (typedArray != null) {
            materialTextAlignment = typedArray.getInteger(R.styleable.StripedPanelText_alignment, ALIGNMENT_NONE)
            sideMargin = typedArray.getDimension(R.styleable.StripedPanelText_sideMargin, 0f)
            titleText = typedArray.getString(R.styleable.StripedPanelText_titleText)
            viewsMargin = typedArray.getDimension(R.styleable.StripedPanelText_viewsMargin, 0f)
            titleTextSize = typedArray.getDimension(R.styleable.StripedPanelText_titleTextSize, 0f)
            titleTextColor = typedArray.getColor(R.styleable.StripedPanelText_titleTextColor, Color.BLACK)
            typedArray.recycle()
        }

        this.apply {
            textAlignment = when (materialTextAlignment) {
                ALIGNMENT_NONE -> View.TEXT_ALIGNMENT_VIEW_START
                ALIGNMENT_LEFT -> View.TEXT_ALIGNMENT_VIEW_END
                ALIGNMENT_RIGHT -> View.TEXT_ALIGNMENT_VIEW_START
                else -> View.TEXT_ALIGNMENT_CENTER
            }
            typeface = ResourcesCompat.getFont(context!!, R.font.header_font)
            textSize = titleTextSize
            val drawableId = if (materialTextAlignment == ALIGNMENT_LEFT) R.drawable.left_stripe_gradient else R.drawable.right_stripe_gradient
            background = context.getDrawable(drawableId)
            text = titleText
            setTextColor(titleTextColor)
        }

    }

//    private fun setConstraints() {
//      val constraintSet = ConstraintSet().apply {
//            clone(this@StripedPanelLayout)
//            connect(titleTextView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 8)
//            when(materialTextAlignment) {
//                ALIGNMENT_LEFT -> {
//                    connect(titleTextView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
//                    connect(titleTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, sideMargin.toInt())
//                }
//                ALIGNMENT_RIGHT -> {
//                    connect(titleTextView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, sideMargin.toInt())
//                    connect(titleTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
//                }
//                ALIGNMENT_NONE -> {
//                    connect(titleTextView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
//                    connect(titleTextView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
//                }
//            }
//
//        }
//        setConstraintSet(constraintSet)
//    }

}