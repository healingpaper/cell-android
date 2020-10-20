package com.gangnam.sister.cell.util

import android.content.Context
import com.google.android.flexbox.FlexboxLayoutManager

class NonScrollFlexboxLayoutManager(context: Context) : FlexboxLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}