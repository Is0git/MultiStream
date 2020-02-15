package com.android.multistream.ui.decorations

import android.graphics.Rect
import android.view.View
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView

class ListTwoDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = 145


    }
}