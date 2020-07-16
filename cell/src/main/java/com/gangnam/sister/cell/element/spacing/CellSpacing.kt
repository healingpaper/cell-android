package com.gangnam.sister.cell.element.spacing

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellSpacing @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var type: SpacingType = SpacingType.TINY
        set(value) {
            field = value
            requestLayout()
        }

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellSpacing, defStyleAttr, 0)
                .use {
                    if (it.hasValue(R.styleable.CellSpacing_spacingType)) {
                        type = SpacingType.fromId(it.getInt(R.styleable.CellSpacing_spacingType, SpacingType.TINY.ordinal))
                    }
                }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spacingHeight = DisplayManager.dpToPx(context, type.space)
        setMeasuredDimension(widthMeasureSpec, spacingHeight)
    }

    enum class SpacingType(val space: Int) {
        TINY(8), SMALL(16), BASE(24), MEDIUM(32), LARGE(48);

        companion object {
            fun fromId(id: Int): SpacingType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set spacing type among \"large, medium, base, small, tiny\".")
            }
        }
    }
}