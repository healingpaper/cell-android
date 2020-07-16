package com.gangnam.sister.cell.element.divider


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellDivider @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val dp1 = DisplayManager.dpToPx(context, 1)

    var type: DividerType = DividerType.LIST
        set(value) {
            field = value
            requestLayout()
        }
    var dividerHeight: Int = dp1
        set(value) {
            field = value
            requestLayout()
        }

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellDivider, defStyleAttr, 0)
                .use {
                    if (it.hasValue(R.styleable.CellDivider_android_dividerHeight)) {
                        dividerHeight = it.getDimensionPixelSize(R.styleable.CellDivider_android_dividerHeight, dp1)
                    }
                    if (it.hasValue(R.styleable.CellDivider_dividerType)) {
                        type = DividerType.fromId(it.getInt(R.styleable.CellDivider_dividerType, DividerType.LIST.ordinal))
                    }
                }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, dividerHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(ContextCompat.getColor(context, type.colorResId))
    }

    override fun setBackgroundColor(color: Int) {
        throw IllegalAccessException("Background color can not be set through \"setBackgroundColor\".")
    }

    override fun setBackgroundResource(resid: Int) {
        throw IllegalAccessException("Background can not be set through \"setBackgroundResource\".")
    }

    override fun setBackground(background: Drawable?) {
        throw IllegalAccessException("Background can not be set through \"setBackground\".")
    }

    override fun setBackgroundTintList(tint: ColorStateList?) {
        throw IllegalAccessException("Background can not be set through \"setBackgroundTintList\".")
    }

    override fun setBackgroundTintMode(tintMode: PorterDuff.Mode?) {
        throw IllegalAccessException("Background can not be set through \"setBackgroundTintMode\".")
    }

    override fun setBackgroundDrawable(background: Drawable?) {
        throw IllegalAccessException("Background can not be set through \"setBackgroundDrawable\".")
    }

    enum class DividerType(@ColorRes val colorResId: Int) {
        LIST(R.color.gray), SECTION(R.color.light_gray);

        companion object {
            fun fromId(id: Int): DividerType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set divider type among \"list, section\".")
            }
        }
    }
}