package com.gangnam.sister.cell.element.textbox

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    private var textBoxStyle: CellTextBoxStyle? = null
    private val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    private val dp16 = DisplayManager.dpToPx(context, 16)
    private val dp180 = DisplayManager.dpToPx(context, 180)
    @SuppressLint("ClickableViewAccessibility")
    private val textBoxTouchListener = OnTouchListener { v, event ->
        if (v.hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return@OnTouchListener true
                }
            }
        } else inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        false
    }

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        var style: CellTextBoxStyle = CellTextBoxStyles.Base(context)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellTextArea, 0, 0)
            .use {
                val padding = it.getDimensionPixelSize(R.styleable.CellTextArea_android_padding, 0)
                val paddingStart = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingStart, dp16)
                val paddingEnd = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingEnd, dp16)
                val paddingTop = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingTop, dp16)
                val paddingBottom = it.getDimensionPixelSize(R.styleable.CellTextArea_android_paddingBottom, dp16)
                hasError = it.getBoolean(R.styleable.CellTextArea_hasError, false)
                maxLength = it.getInt(R.styleable.CellTextArea_android_maxLength, Int.MAX_VALUE)
                gravity = it.getInt(R.styleable.CellTextArea_android_gravity, Gravity.TOP)
                compoundDrawablePadding = dp16
                isVerticalScrollBarEnabled = true
                inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                isSingleLine = false
                setTextAppearance(R.style.T03Body14RegularLeftBlack)
                setTextColor(ContextCompat.getColorStateList(context, R.color.selector_text_box))
                setHintTextColor(ContextCompat.getColorStateList(context, R.color.selector_text_box_hint))
                setBackgroundResource(R.drawable.selector_text_box)
                setOnTouchListener(textBoxTouchListener)
                if (padding > 0) {
                    setPadding(padding, padding, padding, padding)
                } else {
                    setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)
                }
                height = dp180

                style = CellTextBoxStyle.createFromAttribute(context, it, style)
                applyStyle(style)
            }
    }

    private fun applyStyle(style: CellTextBoxStyle) {
        this.textBoxStyle = style
        update(style)
    }

    private fun update(style: CellTextBoxStyle) {
        setTextColor(style.getTextColorStateList())
        setHintTextColor(style.getHintColorStateList())
        background = style.getBackground()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        return if (hasError) {
            val drawableState = super.onCreateDrawableState(extraSpace + 1)
            drawableState.apply { View.mergeDrawableStates(this, intArrayOf(R.attr.state_error_cell)) }
        } else super.onCreateDrawableState(extraSpace)
    }
}