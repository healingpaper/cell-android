package com.gangnam.sister.cell.component.inputset

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.widget.addTextChangedListener
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager
import com.jakewharton.rxbinding3.InitialValueObservable
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.cell_big_text_box_input_set_view.view.*
import kotlinx.android.synthetic.main.cell_big_text_box_input_set_view.view.titleTxt


class CellBigTextBoxInputSet @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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

    var titleText: String? = null
        set(value) {
            field = value
            titleTxt.text = value
            titleTxt.visibility = if (value.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    var text: String? = null
        set(value) {
            field = value
            mainInput.setText(value)
        }
    var hint: String? = null
        set(value) {
            field = value
            mainInput.hint = value
        }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_big_text_box_input_set_view, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBigTextBoxInputSet, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellBigTextBoxInputSet_android_text)) {
                        text = it.getString(R.styleable.CellBigTextBoxInputSet_android_text)
                    }
                    if (it.hasValue(R.styleable.CellBigTextBoxInputSet_android_hint)) {
                        hint = it.getString(R.styleable.CellBigTextBoxInputSet_android_hint)
                    }
                    if (it.hasValue(R.styleable.CellBigTextBoxInputSet_titleText)) {
                        titleText = it.getString(R.styleable.CellBigTextBoxInputSet_titleText)
                    }
                    setTextBox(it).run {
                        textCountTxt.text = String.format(context.getString(R.string.min_max_length_format), 0, mainInput.maxLength)
                    }
                }
    }

    private fun setTextBox(typedArray: TypedArray) {
        with(mainInput) {
            layoutParams = layoutParams.apply {
                height = typedArray.getDimensionPixelSize(R.styleable.CellBigTextBoxInputSet_textBoxHeight, DisplayManager.dpToPx(context, 180))
            }
            maxLength = typedArray.getInt(R.styleable.CellBigTextBoxInputSet_android_maxLength, 500)
            addTextChangedListener(afterTextChanged = {
                textCountTxt.text = String.format(context.getString(R.string.min_max_length_format), it.toString().length, mainInput.maxLength)
            })
            setOnTouchListener(textBoxTouchListener)
        }
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        mainInput.addTextChangedListener(textWatcher)
    }

    fun textChanges(): InitialValueObservable<CharSequence> {
        return mainInput.textChanges()
    }
}