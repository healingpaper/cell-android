package com.gangnam.sister.cell.component.textbox

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.widget.addTextChangedListener
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager
import com.jakewharton.rxbinding3.InitialValueObservable
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.cell_text_area_component.view.*


class CellTextAreaComponent @JvmOverloads constructor(
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
    var state = State.NORMAL
        set(value) {
            if (!errorEnabled && value != State.NORMAL) {
                throw IllegalAccessException("state can be only set as NORMAL, when errorEnabled is false.")
            }
            field = value
            updateState(value, errorEnabled)
        }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_text_area_component, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellTextAreaComponent, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellTextAreaComponent_errorEnabled)) {
                        errorEnabled =
                            it.getBoolean(R.styleable.CellTextAreaComponent_errorEnabled, true)
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_errorText)) {
                        errorText = it.getString(R.styleable.CellTextAreaComponent_errorText)
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_state)) {
                        state =
                            State.fromId(it.getInt(R.styleable.CellTextAreaComponent_state, 0))
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_android_text)) {
                        text = it.getString(R.styleable.CellTextAreaComponent_android_text)
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_android_hint)) {
                        hint = it.getString(R.styleable.CellTextAreaComponent_android_hint)
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_android_enabled)) {
                        mainInput.isEnabled = it.getBoolean(R.styleable.CellTextAreaComponent_android_enabled, true)
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_titleText)) {
                        titleText = it.getString(R.styleable.CellTextAreaComponent_titleText)
                    }
                    if (it.hasValue(R.styleable.CellTextAreaComponent_titleTextStyle)) {
                        titleTextStyle = it.getResourceId(
                            R.styleable.CellTextAreaComponent_titleTextStyle,
                            R.style.T03Body14BoldLeftBlack
                        )
                    }
                    setTextBox(it).run {
                        textCountTxt.text = String.format(context.getString(R.string.min_max_length_format), 0, mainInput.maxLength)
                    }
                }
    }

    private fun setTextBox(typedArray: TypedArray) {
        with(mainInput) {
            layoutParams = layoutParams.apply {
                height = typedArray.getDimensionPixelSize(R.styleable.CellTextAreaComponent_textBoxHeight, DisplayManager.dpToPx(context, 180))
            }
            maxLength = typedArray.getInt(R.styleable.CellTextAreaComponent_android_maxLength, 500)
            addTextChangedListener(afterTextChanged = {
                this@CellTextAreaComponent.textCountTxt.text = String.format(context.getString(R.string.min_max_length_format), it.toString().length, mainInput.maxLength)
            })
        }
    }

    private fun updateState(
        state: State,
        errorEnabled: Boolean
    ) {
        setErrorTextVisibility(errorEnabled, state)
        mainInput.apply {
            hasError = state.hasError
        }
    }

    private fun setErrorTextVisibility(errorEnabled: Boolean, state: State) {
        errorTxt.visibility = when {
            errorEnabled && state == State.ERROR -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        mainInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                textWatcher.beforeTextChanged(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textWatcher.onTextChanged(s, start, before, count)
            }

            override fun afterTextChanged(s: Editable?) {
                textWatcher.afterTextChanged(s)
            }

        })
    }

    fun textChanges(): InitialValueObservable<CharSequence> {
        return mainInput.textChanges()
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