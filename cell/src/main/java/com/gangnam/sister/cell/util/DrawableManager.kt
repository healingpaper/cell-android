package com.gangnam.sister.cell.util

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.ColorInt
import androidx.annotation.Px

object DrawableManager {

    fun roundRectDrawable(
        @ColorInt color: Int,
        @ColorInt borderColor: Int = Color.TRANSPARENT,
        @Px borderWidth: Int = 0,
        @Px radius: Int = 0
    ): Drawable = GradientDrawable().apply {
        setColor(color)
        setStroke(borderWidth, borderColor)
        cornerRadius = radius.toFloat()
    }

    fun rippleDrawable(
        content: Drawable,
        mask: Drawable,
        @ColorInt rippleColor: Int = 0
    ): RippleDrawable = RippleDrawable(ColorStateList.valueOf(rippleColor), content, mask)

}