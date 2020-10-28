package com.gangnam.sister.cell.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class NonScrollLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean ): LinearLayoutManager(context, orientation, reverseLayout) {
    override fun canScrollHorizontally() = false
}