package com.gangnam.sister.cell.component.textbox

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.BigDotPasswordTransformationMethod
import kotlinx.android.synthetic.main.cell_text_field_component.view.*

class CellTextFieldComponent @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var titleText: String? = null
        set(value) {
            field = value
            titleTxt.text = value
            titleTxt.visibility = if (value.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    var titleTextStyle: Int = R.style.T03Body14BoldLeftBlack
        set(value) {
            field = value
            titleTxt.setTextAppearance(value)
        }
    var text: String = ""
        get() = mainInput.text.toString()
        set(value) {
            field = value
            mainInput.setText(value)
        }
    var hint: String? = null
        set(value) {
            field = value
            mainInput.hint = value
        }
    var inputType: Int
        get() = mainInput.inputType
        set(value) {
            mainInput.inputType = when (value) {
                0 -> InputType.TYPE_NULL
                1 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                2 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                3 -> InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                4 -> InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                else -> value
            }
            if (value == 2) {
                mainInput.typeface = ResourcesCompat.getFont(context, R.font.notosans_regular_hestia)
                mainInput.transformationMethod = BigDotPasswordTransformationMethod()
            }
        }

    var errorEnabled = true
        set(value) {
            field = value
            updateState(state, value, showDrawableEnd)
        }
    var errorText: String? = null
        set(value) {
            if (!errorEnabled) throw IllegalAccessException("errorText cannot be set, when errorEnabled is false.")
            field = value
            errorTxt.text = value
        }
    var state = State.NORMAL
        set(value) {
            if (!errorEnabled && value != State.NORMAL) {
                throw IllegalAccessException("state can be only set as NORMAL, when errorEnabled is false.")
            }
            field = value
            updateState(value, errorEnabled, showDrawableEnd)
        }
    var showDrawableEnd = false
        set(value) {
            field = value
            updateState(state, errorEnabled, value)
        }
    var normalStateDrawable: Int = 0
    var correctStateDrawable: Int = 0
    var errorStateDrawable: Int = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_text_field_component, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellTextFieldComponent, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellErrorEnabled)) {
                        errorEnabled = it.getBoolean(R.styleable.CellTextFieldComponent_cellErrorEnabled, true)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellShowDrawableEnd)) {
                        showDrawableEnd = it.getBoolean(R.styleable.CellTextFieldComponent_cellShowDrawableEnd, true)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellState)) {
                        state = State.fromId(it.getInt(R.styleable.CellTextFieldComponent_cellState, 0))
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellInputType)) {
                        inputType = it.getInt(R.styleable.CellTextFieldComponent_cellInputType, 0)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellTitleText)) {
                        titleText = it.getString(R.styleable.CellTextFieldComponent_cellTitleText)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellTitleTextStyle)) {
                        titleTextStyle = it.getResourceId(R.styleable.CellTextFieldComponent_cellTitleTextStyle, R.style.T03Body14BoldLeftBlack)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_android_text)) {
                        text = it.getString(R.styleable.CellTextFieldComponent_android_text) ?: ""
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_android_hint)) {
                        hint = it.getString(R.styleable.CellTextFieldComponent_android_hint)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_android_enabled)) {
                        mainInput.isEnabled = it.getBoolean(R.styleable.CellTextFieldComponent_android_enabled, true)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellErrorText)) {
                        errorText = it.getString(R.styleable.CellTextFieldComponent_cellErrorText)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellNormalDrawableEnd)) {
                        normalStateDrawable = it.getResourceId(R.styleable.CellTextFieldComponent_cellNormalDrawableEnd, 0)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellCorrectDrawableEnd)) {
                        correctStateDrawable = it.getResourceId(R.styleable.CellTextFieldComponent_cellCorrectDrawableEnd, 0)
                    }
                    if (it.hasValue(R.styleable.CellTextFieldComponent_cellErrorDrawableEnd)) {
                        errorStateDrawable = it.getResourceId(R.styleable.CellTextFieldComponent_cellErrorDrawableEnd, 0)
                    }
                }
    }

    private fun updateState(
            state: State,
            errorEnabled: Boolean,
            showDrawableEnd: Boolean
    ) {
        setErrorTextVisibility(errorEnabled, state)
        mainInput.apply {
            hasError = state.hasError
            if (showDrawableEnd) setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0,
                    0,
                    getDrawableEnd(state),
                    0
            )
        }
    }

    private fun getDrawableEnd(state: State): Int {
        return when (state) {
            State.NORMAL -> normalStateDrawable
            State.CORRECT -> correctStateDrawable
            State.ERROR -> errorStateDrawable
        }
    }

    private fun setErrorTextVisibility(errorEnabled: Boolean, state: State) {
        errorTxt.visibility = when {
            errorEnabled && state == State.ERROR -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun addTextChangedListener(listener: TextWatcher) {
        mainInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                listener.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener.onTextChanged(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) {
                listener.afterTextChanged(s)
            }

        })
    }

    enum class State(val hasError: Boolean) {
        NORMAL(false),
        CORRECT(false),
        ERROR(true);

        companion object {
            fun fromId(id: Int): State {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set State among \"normal, correct, error\".")
            }
        }
    }
}