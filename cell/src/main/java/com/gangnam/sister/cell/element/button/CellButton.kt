package com.gangnam.sister.cell.element.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.use
import com.gangnam.sister.cell.R


class CellButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    var styleType: ButtonStyleType = ButtonStyleType.PRIMARY
        set(value) {
            field = value
            applyStyle(value.style(context))
        }

    var appearanceType: ButtonAppearanceType = ButtonAppearanceType.LARGE
        set(value) {
            field = value
            update()
        }

    var isButtonEnabled: Boolean = true
        set(value) {
            field = value
            update()
        }

    private var buttonStyle: ButtonStyle? = null

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellButton, defStyleAttr, 0)
            .use {
                isButtonEnabled = it.getBoolean(R.styleable.CellButton_cellButtonEnabled, true)
                appearanceType = ButtonAppearanceType.fromId(it.getInt(R.styleable.CellButton_cellButtonAppearance, 0))
                styleType = ButtonStyleType.fromId(it.getInt(R.styleable.CellButton_cellButtonStyle, 0))
                stateListAnimator = null
                isClickable = true
                isFocusable = true
                gravity = Gravity.CENTER
                applyStyle(styleType.style(context))
            }
    }

    fun applyStyle(style: ButtonStyle) {
        this.buttonStyle = style
        update()
    }

    private fun update() {
        buttonStyle?.let {
            height = it.getButtonHeight(appearanceType)
            background = it.getButtonBackground(isButtonEnabled)
            setTextAppearance(it.getCellButtonTextStyle(isButtonEnabled, appearanceType))
            setTextColor(it.getTextColor(isButtonEnabled))
        }
    }

    override fun setEnabled(enabled: Boolean) =
        throw IllegalAccessException("enabled cannot be set in this element.")

    enum class ButtonStyleType(val style: (Context) -> ButtonStyle) {
        PRIMARY(ButtonStyles.Primary),
        SECONDARY(ButtonStyles.Secondary),
        TERTIARY(ButtonStyles.Tertiary),
        ACTION(ButtonStyles.Action),
        ABOVE_KEYBOARD(ButtonStyles.AboveKeyboard); // Use Only with ButtonStack

        companion object {
            fun fromId(id: Int): ButtonStyleType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set button style among \"primary, secondary, tertiary, disabled, action\".")
            }
        }
    }

    enum class ButtonAppearanceType {
        LARGE, MEDIUM, SMALL;

        companion object {
            fun fromId(id: Int): ButtonAppearanceType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set button size among \"large, medium, small\".")
            }
        }
    }
}