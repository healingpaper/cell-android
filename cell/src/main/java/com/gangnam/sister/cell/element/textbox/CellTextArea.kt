package com.gangnam.sister.cell.element.textbox

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellTextArea @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs, androidx.appcompat.R.attr.editTextStyle) {
    var hasError = false
        set(value) {
            field = value
            refreshDrawableState()
        }
    var maxLength = Int.MAX_VALUE
        set(value) {
            field = value
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(value))
        }

    private val dp16 = DisplayManager.dpToPx(context, 16)
    private val dp180 = DisplayManager.dpToPx(context, 180)

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellTextArea, 0, 0)
            .use {
                val padding = it.getDimensionPixelSize(R.styleable.CellTextArea_android_padding, 0)
                val paddingStart = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingStart, dp16)
                val paddingEnd = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingEnd, dp16)
                val paddingTop = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingTop, 0)
                val paddingBottom = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingBottom, 0)
                hasError = it.getBoolean(R.styleable.CellTextArea_hasError, false)
                maxLength = it.getInt(R.styleable.CellTextArea_android_maxLength, Int.MAX_VALUE)
                gravity = it.getInt(R.styleable.CellTextArea_android_gravity, Gravity.TOP)
                compoundDrawablePadding = dp16
                isVerticalScrollBarEnabled = true
                inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                setTextAppearance(R.style.T03Body14RegularLeftBlack)
                setTextColor(ContextCompat.getColorStateList(context, R.color.selector_text_box))
                setHintTextColor(ContextCompat.getColorStateList(context, R.color.selector_text_box_hint))
                setBackgroundResource(R.drawable.selector_text_box)
                if (padding > 0) {
                    setPadding(padding, padding, padding, padding)
                } else {
                    setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)
                }
                height = dp180
            }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        return if (hasError) {
            val drawableState = super.onCreateDrawableState(extraSpace + 1)
            drawableState.apply { View.mergeDrawableStates(this, intArrayOf(R.attr.state_error_cell)) }
        } else super.onCreateDrawableState(extraSpace)
    }
}