package com.gangnam.sister.cell.util

import android.graphics.Canvas
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class HideLastDecorator(private val dividerSize: Int) : RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val visibleChildCount = parent.childCount
        val visibleChildWidth = parent.children.sumOf { it.width + dividerSize }
        val isChildFullyVisible = parent.width >= visibleChildWidth
        if (!isChildFullyVisible) {
            for (i in 0 until visibleChildCount) {
                parent.getChildAt(i).visibility = if (visibleChildCount == i + 1) View.INVISIBLE else View.VISIBLE
            }
        }
    }
}