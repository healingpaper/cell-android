package com.gangnam.sister.cell.element.checkbox

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellCheckBox @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : AppCompatCheckBox(context, attrs, androidx.appcompat.R.attr.checkboxStyle) {

    private val dp8 = DisplayManager.dpToPx(context, 8)

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellCheckBox, 0, 0)
                .use {
                    setTextAppearance(R.style.T03Body14RegularLeftBlack)
                    setPadding(dp8, 0, 0, 0)
                    includeFontPadding = false
                    update()
                }
    }

    private fun update() {
        buttonDrawable = ContextCompat.getDrawable(context, R.drawable.selector_checkbox_primary)
    }
}