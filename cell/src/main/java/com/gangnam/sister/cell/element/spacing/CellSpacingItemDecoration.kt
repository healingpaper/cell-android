package com.gangnam.sister.cell.element.spacing

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.util.DisplayManager

class CellSpacingItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    var type = CellSpacing.SpacingType.TINY
        set(value) {
            field = value
            height = DisplayManager.dpToPx(context, value.space)
        }

    private var height = DisplayManager.dpToPx(context, type.space)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val bounds = Rect(0, 0, 0, 0)
        bounds.left = parent.paddingLeft
        bounds.right = parent.width - parent.paddingRight

        val dividerHeight = height
        val childCount: Int = parent.childCount
        for (i in 0 until childCount - 1) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            bounds.top = child.bottom + params.topMargin + dividerHeight / 2
            bounds.bottom = bounds.top

            val paint = Paint()
            paint.color = Color.TRANSPARENT
            paint.strokeWidth = dividerHeight.toFloat()
            c.drawLine(bounds.left.toFloat(), bounds.top.toFloat(), bounds.right.toFloat(), bounds.bottom.toFloat(), paint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        setItemOffsets(outRect, position, parent)
    }

    private fun setItemOffsets(outRect: Rect, position: Int, parent: RecyclerView) {
        val size: Int = height / 2
        if (position == 0) {
            outRect.set(0, 0, 0, size)
        } else {
            val lastItemSize: Int = height / 2
            if (position == parent.layoutManager!!.itemCount - 1) {
                outRect.set(0, lastItemSize, 0, 0)
            } else {
                outRect.set(0, lastItemSize, 0, size)
            }
        }
    }
}