package com.gangnam.sister.cell.util

import android.content.Context
import androidx.annotation.Dimension
import kotlin.math.roundToInt

object DisplayManager {

    fun dpToPx(context: Context, @Dimension dp: Int): Int {
        return dpToPx(context, dp.toFloat())
    }

    fun dpToPx(context: Context, @Dimension dp: Float): Int {
        return (dp * context.resources.displayMetrics.density).roundToInt()
    }
}