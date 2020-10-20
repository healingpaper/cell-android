package com.gangnam.sister.cell.element.button

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager


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

    private var buttonStyle: CellButtonStyle? = null
    private val startEndPadding = DisplayManager.dpToPx(context, 16)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        var style: CellButtonStyle = styleType.style(context)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellButton, defStyleAttr, 0)
            .use {
                if (it.hasValue(R.styleable.CellButton_cellButtonEnabled)) {
                    isButtonEnabled = it.getBoolean(R.styleable.CellButton_cellButtonEnabled, true)
                }
                if (it.hasValue(R.styleable.CellButton_cellButtonAppearance)){
                    appearanceType = ButtonAppearanceType.fromId(it.getInt(R.styleable.CellButton_cellButtonAppearance, 0))
                }
                if (it.hasValue(R.styleable.CellButton_cellButtonStyle)){
                    styleType = ButtonStyleType.fromId(it.getInt(R.styleable.CellButton_cellButtonStyle, 0))
                    style = styleType.style(context)
                }
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
                stateListAnimator = null
                isClickable = true
                isFocusable = true
                isAllCaps = false
                gravity = Gravity.CENTER
                setPadding(startEndPadding, 0, startEndPadding, 0)

                style = CellButtonStyle.createFromAttribute(context, it, style)
                applyStyle(style)
            }
    }

    fun applyStyle(style: CellButtonStyle) {
        this.buttonStyle = style
        update()
    }

    private fun update() {
        buttonStyle?.let {
            minHeight = it.getButtonMinHeight(appearanceType)
            minWidth = it.getButtonMinWidth(appearanceType)
            background = it.getButtonBackground(isButtonEnabled)
            setTextAppearance(it.getCellButtonTextStyle(isButtonEnabled, appearanceType))
            setTextColor(it.getTextColor(isButtonEnabled))
        }
    }

    override fun setEnabled(enabled: Boolean) =
        throw IllegalAccessException("enabled cannot be set in this element.")

    enum class ButtonStyleType(val style: (Context) -> CellButtonStyle) {
        PRIMARY(CellButtonStyles.Primary),
        SECONDARY(CellButtonStyles.Secondary),
        TERTIARY(CellButtonStyles.Tertiary),
        ACTION(CellButtonStyles.Action),
        ABOVE_KEYBOARD(CellButtonStyles.AboveKeyboard); // Use Only with ButtonStack

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