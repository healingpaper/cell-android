package com.gangnam.sister.cell.component.selectset

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.jakewharton.rxbinding3.InitialValueObservable
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.cell_select_dropdown_view.view.*
import kotlinx.android.synthetic.main.cell_select_dropdown_view.view.titleTxt
import kotlinx.android.synthetic.main.cell_select_set_view.view.*


class CellSelectDropdown @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
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
    var inputType: Int = InputType.TYPE_TEXT_VARIATION_NORMAL
        set(value) {
            field = value
            mainInput.inputType = value
        }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_select_dropdown_view, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellSelectDropdown, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellSelectDropdown_android_text)) {
                        text = it.getString(R.styleable.CellSelectDropdown_android_text)
                    }
                    if (it.hasValue(R.styleable.CellSelectDropdown_android_hint)) {
                        hint = it.getString(R.styleable.CellSelectDropdown_android_hint)
                    }
                    if (it.hasValue(R.styleable.CellSelectDropdown_android_inputType)) {
                        inputType = it.getInt(R.styleable.CellSelectDropdown_android_inputType, InputType.TYPE_TEXT_VARIATION_NORMAL)
                    }
                    if (it.hasValue(R.styleable.CellSelectDropdown_cellTitleText)) {
                        titleText = it.getString(R.styleable.CellSelectDropdown_cellTitleText)
                    }
                }
    }

    fun setTextBoxClickListener(listener: OnClickListener) {
        mainInput.setOnClickListener(listener)
    }

    fun setTextBoxClickListener(listener: (View) -> Unit) {
        mainInput.setOnClickListener { listener.invoke(it) }
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        mainInput.addTextChangedListener(textWatcher)
    }

    fun textChanges(): InitialValueObservable<CharSequence> {
        return mainInput.textChanges()
    }
}