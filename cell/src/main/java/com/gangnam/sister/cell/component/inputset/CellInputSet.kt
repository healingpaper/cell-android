package com.gangnam.sister.cell.component.inputset

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
import kotlinx.android.synthetic.main.cell_input_set_view.view.*
import kotlinx.android.synthetic.main.cell_input_set_view.view.titleTxt

class CellInputSet @JvmOverloads constructor(
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
    var errorEnabled = true
        set(value) {
            field = value
            updateState(state, value)
        }

    var errorText: String? = null
        set(value) {
            if (!errorEnabled) throw IllegalAccessException("errorText cannot be set, when errorEnabled is false.")
            field = value
            errorTxt.text = value
        }
    var state = InputSetState.NORMAL
        set(value) {
            if (!errorEnabled && value != InputSetState.NORMAL) {
                throw IllegalAccessException("state can be only set as NORMAL, when errorEnabled is false.")
            }
            field = value
            updateState(value, errorEnabled)
        }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_input_set_view, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellInputSet, 0, 0)
                .use {
                    errorEnabled = it.getBoolean(R.styleable.CellInputSet_errorEnabled, true)
                    state = InputSetState.fromId(it.getInt(R.styleable.CellInputSet_state, 0))
                    inputType = it.getInt(R.styleable.CellInputSet_android_inputType, InputType.TYPE_TEXT_VARIATION_NORMAL)
                    if (it.hasValue(R.styleable.CellInputSet_android_text)) {
                        text = it.getString(R.styleable.CellInputSet_android_text)
                    }
                    if (it.hasValue(R.styleable.CellInputSet_android_hint)) {
                        hint = it.getString(R.styleable.CellInputSet_android_hint)
                    }
                    if (it.hasValue(R.styleable.CellInputSet_titleText)) {
                        titleText = it.getString(R.styleable.CellInputSet_titleText)
                    }
                    if (it.hasValue(R.styleable.CellInputSet_errorText)) {
                        errorText = it.getString(R.styleable.CellInputSet_errorText)
                    }
                }
    }

    private fun updateState(state: InputSetState, errorEnabled: Boolean) {
        setErrorTextVisibility(errorEnabled, state)
        mainInput.apply {
            hasError = state.hasError
            setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, state.compoundDrawableResId, 0)
        }
    }

    private fun setErrorTextVisibility(errorEnabled: Boolean, state: InputSetState) {
        errorTxt.visibility = when {
            !errorEnabled -> View.GONE
            errorEnabled && state == InputSetState.ERROR -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        mainInput.addTextChangedListener(textWatcher)
    }

    fun textChanges(): InitialValueObservable<CharSequence> {
        return mainInput.textChanges()
    }

    enum class InputSetState(val hasError: Boolean, val compoundDrawableResId: Int) {
        NORMAL(false, 0),
        CORRECT(false, R.drawable.ic_validation_success),
        ERROR(true, R.drawable.ic_validation_error);

        companion object {
            fun fromId(id: Int): InputSetState {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set InputSetState among \"normal, correct, error\".")
            }
        }
    }
}