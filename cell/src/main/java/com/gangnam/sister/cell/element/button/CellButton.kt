package com.gangnam.sister.cell.element.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager


class CellButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    var style: ButtonStyle = ButtonStyle.PRIMARY
        set(value) {
            field = value
            update()
        }

    var size: ButtonSize = ButtonSize.LARGE
        set(value) {
            field = value
            update()
        }

    var isButtonEnabled: Boolean = false
        set(value) {
            field = value
            update()
        }

    private val smallHeight = DisplayManager.dpToPx(context, 32)
    private val mediumHeight = DisplayManager.dpToPx(context, 36)
    private val largeHeight = DisplayManager.dpToPx(context, 52)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellButton, defStyleAttr, 0)
            .use {
                style = ButtonStyle.fromId(it.getInt(R.styleable.CellButton_cellButtonStyle, 0))
                size = ButtonSize.fromId(it.getInt(R.styleable.CellButton_cellButtonSize, 0))
                isButtonEnabled = it.getBoolean(R.styleable.CellButton_cellButtonEnabled, false)
                stateListAnimator = null
                isClickable = true
                isFocusable = true
                gravity = Gravity.CENTER
            }
    }

    private fun update() {
        height = getCellButtonHeight(size)
        setBackgroundResource(getCellButtonBackgroundResource(style))
        setTextAppearance(getCellButtonTextStyle(size))
        setTextColor(getCellButtonTextColor(style))
        isSelected = !isButtonEnabled
    }

    private fun getCellButtonHeight(buttonSize: ButtonSize): Int {
        return when (buttonSize) {
            ButtonSize.LARGE -> largeHeight
            ButtonSize.MEDIUM -> mediumHeight
            ButtonSize.SMALL -> smallHeight
        }
    }

    private fun getCellButtonBackgroundResource(buttonStyle: ButtonStyle): Int {
        return when (buttonStyle) {
            ButtonStyle.PRIMARY -> R.drawable.selector_rect_fill_orange_gray_radius8_btn
            ButtonStyle.SECONDARY -> R.drawable.selector_rect_fill_lemonde_gray_radius8_btn
            ButtonStyle.TERTIARY -> R.drawable.selector_rect_fill_gray_gray_radius8_btn
            ButtonStyle.ACTION -> R.drawable.selector_rect_fill_gray_gray_radius8_btn
            ButtonStyle.ABOVE_KEYBOARD -> R.drawable.selector_rect_fill_orange_gray_btn
        }
    }

    private fun getCellButtonTextStyle(buttonSize: ButtonSize): Int {
        return when (buttonSize) {
            ButtonSize.LARGE -> R.style.T02H216BoldCenterBlack
            ButtonSize.MEDIUM -> R.style.T03Body14BoldCenterBlack
            ButtonSize.SMALL -> R.style.T04Label12BoldCenterBlack
        }
    }

    private fun getCellButtonTextColor(buttonStyle: ButtonStyle): Int {
        val colorRes = when (buttonStyle) {
            ButtonStyle.PRIMARY -> R.color.selector_cell_color_white_gray
            ButtonStyle.SECONDARY -> R.color.selector_cell_color_orange_gray
            ButtonStyle.TERTIARY -> R.color.selector_cell_color_black_gray
            ButtonStyle.ACTION -> R.color.selector_cell_color_black_gray
            ButtonStyle.ABOVE_KEYBOARD -> R.color.selector_cell_color_white_gray
        }
        return ContextCompat.getColor(context, colorRes)
    }

    override fun setEnabled(enabled: Boolean) = throw IllegalAccessException("enabled cannot be set in this element.")

    enum class ButtonStyle {
        PRIMARY,
        SECONDARY,
        TERTIARY,
        ACTION,
        ABOVE_KEYBOARD; // Use Only with ButtonStack

        companion object {
            fun fromId(id: Int): ButtonStyle {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set button color among \"primary, secondary, tertiary, disabled, action\".")
            }
        }
    }

    enum class ButtonSize {
        LARGE, MEDIUM, SMALL;

        companion object {
            fun fromId(id: Int): ButtonSize {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set button color among \"large, medium, small\".")
            }
        }
    }
}