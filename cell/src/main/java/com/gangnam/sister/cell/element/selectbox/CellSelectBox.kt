package com.gangnam.sister.cell.element.selectbox

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellSelectBox @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    private val dp48 = DisplayManager.dpToPx(context, 48)
    private val dp50 = DisplayManager.dpToPx(context, 50)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellSelectBox, defStyleAttr, 0)
                .use {
                    isSelected = it.getBoolean(R.styleable.CellSelectBox_boxSelected, false)
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                    includeFontPadding = false
                    gravity = Gravity.CENTER
                    height = dp50
                    setPadding(dp48, 0, dp48, 0)
                    setBackgroundResource(R.drawable.selector_select_box)
                    setTextAppearance(R.style.T03Body14RegularCenterOrangeAsh)
                }
    }
}