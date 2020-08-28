package com.gangnam.sister.cell.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class OffsetDividerDecoration(private val left: Int, private val top: Int, private val right: Int, private val bottom: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(left, top, right, bottom)
    }
}