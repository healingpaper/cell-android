package com.gangnam.sister.cell.element.toggle

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellToggle @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : SwitchCompat(wrapContext(context, attrs), attrs, defStyleAttr) {

    init {
        initialize(attrs, defStyleAttr)
    }

    fun initialize(attrs: AttributeSet?, defStyleAttr: Int) {

        context.theme.obtainStyledAttributes(attrs, R.styleable.CellToggle, defStyleAttr, 0).use {
            val thumbColorStateList = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf(android.R.attr.state_checked), intArrayOf()), intArrayOf(
                    ContextCompat.getColor(context, R.color.ash),
                    ContextCompat.getColor(context, R.color.confident_orange),
                    ContextCompat.getColor(context, R.color.ash)
            ))

            val trackColorStateList = ColorStateList(arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf(android.R.attr.state_checked), intArrayOf()), intArrayOf(
                    ContextCompat.getColor(context, R.color.gray),
                    ContextCompat.getColor(context, R.color.confident_orange_alpha_50),
                    ContextCompat.getColor(context, R.color.gray)
            ))
            thumbTintList = thumbColorStateList
            trackTintList = trackColorStateList
            switchMinWidth = DisplayManager.dpToPx(context, 52)
        }
    }
}

private fun wrapContext(context: Context, attrs: AttributeSet?): Context {
    val withBaseStyle = createContextThemeWrapper(context, attrs, androidx.appcompat.R.attr.switchStyle)
    return createContextThemeWrapper(withBaseStyle, attrs, 0)
}

private fun createContextThemeWrapper(context: Context, attributeSet: AttributeSet?, styleAttr: Int): Context {
    val a = context.obtainStyledAttributes(attributeSet, intArrayOf(styleAttr), 0, 0)
    val themeId = a.getResourceId(0, 0)
    a.recycle()
    return ContextThemeWrapper(context, themeId)
}