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
            ButtonStyle.PRIMARY -> R.drawable.rect_fill_orange_radius8_btn
            ButtonStyle.SECONDARY -> R.drawable.rect_fill_lemonde_radius8_btn
            ButtonStyle.TERTIARY -> R.drawable.rect_fill_gray_radius8_btn
            ButtonStyle.DISABLED -> R.drawable.rect_fill_gray_radius8_btn
            ButtonStyle.ACTION -> R.drawable.rect_fill_gray_radius8_btn
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
            ButtonStyle.PRIMARY -> R.color.white
            ButtonStyle.SECONDARY -> R.color.confident_orange
            ButtonStyle.TERTIARY -> R.color.black
            ButtonStyle.DISABLED -> R.color.light_gray
            ButtonStyle.ACTION -> R.color.black
        }
        return ContextCompat.getColor(context, colorRes)
    }

    enum class ButtonStyle {
        PRIMARY, SECONDARY, TERTIARY, DISABLED, ACTION;

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