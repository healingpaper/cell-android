package com.gangnam.sister.cell.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class GridSpacingItemDecoration(private val spanCount: Int, private val columnSpacing: Int, private val rowSpacing: Int, private val includeEdge: Boolean = false) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        if (includeEdge) {
            outRect.left = columnSpacing - column * columnSpacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * columnSpacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = rowSpacing
            }
            outRect.bottom = rowSpacing // item bottom
        } else {
            outRect.left = column * columnSpacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = columnSpacing - (column + 1) * columnSpacing / spanCount // spacing - (column + 1) * ((1f / spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = rowSpacing // item top
            }
        }
    }
}