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
    var buttonType: ButtonType = ButtonType.NORMAL
        set(value) {
            field = value
            checkButtonCondition()
            update()
        }

    var buttonColor: ButtonColor = ButtonColor.ORANGE
        set(value) {
            field = value
            checkButtonCondition()
            update()
        }
    var buttonButtonTextAppearance: ButtonTextAppearance = ButtonTextAppearance.BIG
        set(value) {
            field = value
            checkButtonCondition()
            update()
        }
    private val tinyHeight = DisplayManager.dpToPx(context, 28)
    private val smallHeight = DisplayManager.dpToPx(context, 32)
    private val mediumHeight = DisplayManager.dpToPx(context, 36)
    private val bigHeight = DisplayManager.dpToPx(context, 52)
    private val bigBlockHeight = DisplayManager.dpToPx(context, 68)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellButton, defStyleAttr, 0)
                .use {
                    if (it.hasValue(R.styleable.CellButton_buttonType)) {
                        buttonType = ButtonType.fromId(it.getInt(R.styleable.CellButton_buttonType, 0))
                    }
                    if (it.hasValue(R.styleable.CellButton_buttonColor)) {
                        buttonColor = ButtonColor.fromId(buttonType, it.getInt(R.styleable.CellButton_buttonColor, 0))
                    }
                    if (it.hasValue(R.styleable.CellButton_buttonTextAppearance)) {
                        buttonButtonTextAppearance = ButtonTextAppearance.fromId(buttonType, it.getInt(R.styleable.CellButton_buttonTextAppearance, 0))
                    }
                    isSelected = it.getBoolean(R.styleable.CellButton_buttonSelected, false)
                    stateListAnimator = null
                    isClickable = true
                    isFocusable = true
                    gravity = Gravity.CENTER
                    update()
                }
    }

    private fun update() {
        height = getCellButtonHeight(buttonType, buttonButtonTextAppearance)
        setBackgroundResource(getCellButtonBackgroundResource(buttonType, buttonColor))
        setTextAppearance(getCellButtonTextAppearance(buttonButtonTextAppearance, buttonColor))
    }

    private fun getCellButtonHeight(buttonType: ButtonType, buttonButtonTextAppearance: ButtonTextAppearance): Int {
        return when (buttonType) {
            ButtonType.NORMAL -> {
                when (buttonButtonTextAppearance) {
                    ButtonTextAppearance.BIG -> bigHeight
                    ButtonTextAppearance.MEDIUM -> mediumHeight
                    ButtonTextAppearance.SMALL -> smallHeight
                    ButtonTextAppearance.TINY -> tinyHeight
                }
            }
            ButtonType.BIG_BLOCK -> bigBlockHeight
            ButtonType.FULL_WIDTH -> bigHeight
        }
    }

    private fun getCellButtonBackgroundResource(buttonType: ButtonType, buttonColor: ButtonColor): Int {
        return when (buttonType) {
            ButtonType.NORMAL -> {
                when (buttonColor) {
                    ButtonColor.ORANGE -> R.drawable.selector_button_normal_disabled_orange
                    ButtonColor.LEMONADE -> R.drawable.selector_button_normal_disabled_lemonade
                    ButtonColor.LIGHT_GRAY -> R.drawable.selector_button_normal_disabled_light_gray
                }
            }
            ButtonType.FULL_WIDTH -> {
                when (buttonColor) {
                    ButtonColor.ORANGE -> R.drawable.selector_button_full_width_disabled_orange
                    ButtonColor.LEMONADE -> R.drawable.selector_button_full_width_disabled_lemonade
                    ButtonColor.LIGHT_GRAY -> R.drawable.selector_button_full_width_light_gray
                }
            }
            ButtonType.BIG_BLOCK -> {
                when (buttonColor) {
                    ButtonColor.ORANGE -> R.drawable.selector_button_big_block_disabled_orange
                    ButtonColor.LEMONADE -> R.drawable.selector_button_big_block_disabled_lemonade
                    ButtonColor.LIGHT_GRAY -> R.drawable.selector_button_big_block_disabled_light_gray
                }
            }
        }
    }

    private fun getCellButtonTextAppearance(buttonButtonTextAppearance: ButtonTextAppearance, buttonColor: ButtonColor): Int {
        return when (buttonButtonTextAppearance) {
            ButtonTextAppearance.BIG -> {
                when (buttonColor) {
                    ButtonColor.ORANGE -> R.style.T02H216BoldCenterWhite
                    ButtonColor.LEMONADE -> R.style.T02H216BoldCenterOrangeWhite
                    ButtonColor.LIGHT_GRAY -> R.style.T02H216BoldCenterBlackWhite
                }
            }
            ButtonTextAppearance.MEDIUM -> {
                when (buttonColor) {
                    ButtonColor.ORANGE -> R.style.T03Body14BoldCenterWhite
                    ButtonColor.LEMONADE -> R.style.T03Body14BoldCenterOrangeWhite
                    ButtonColor.LIGHT_GRAY -> R.style.T03Body14BoldCenterBlackWhite
                }
            }
            ButtonTextAppearance.SMALL, ButtonTextAppearance.TINY -> {
                when (buttonColor) {
                    ButtonColor.ORANGE -> R.style.T04Label12RegularCenterWhite
                    ButtonColor.LEMONADE -> R.style.T04Label12RegularCenterOrangeWhite
                    ButtonColor.LIGHT_GRAY -> R.style.T04Label12RegularCenterBlackWhite
                }
            }
        }
    }

    private fun checkButtonCondition() {
        if (buttonType != ButtonType.NORMAL)
            if (buttonColor != ButtonColor.ORANGE)
                throw IllegalAccessException("Button color can be set only with normal button type.")
            else if (buttonButtonTextAppearance != ButtonTextAppearance.BIG)
                throw IllegalAccessException("Button text appearance can be set only with normal button type.")
    }

    /**
     * NORMAL 일 때만 ButtonColor, TextAppearance 변경 가능
     */
    enum class ButtonType {
        NORMAL, FULL_WIDTH, BIG_BLOCK;

        companion object {
            fun fromId(id: Int): ButtonType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set button type among \"normal, fullWidth, bigBlock\".")
            }
        }
    }

    enum class ButtonColor {
        ORANGE, LEMONADE, LIGHT_GRAY;

        companion object {
            fun fromId(buttonType: ButtonType, id: Int): ButtonColor {
                if (buttonType != ButtonType.NORMAL) throw IllegalAccessException("Button color can be set only with normal button type.")
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set button color among \"orange, lemonade, light_gray\".")
            }
        }
    }

    enum class ButtonTextAppearance {
        BIG, MEDIUM, SMALL, TINY;

        companion object {
            fun fromId(buttonType: ButtonType, id: Int): ButtonTextAppearance {
                if (buttonType != ButtonType.NORMAL) throw IllegalAccessException("Button text appearance can be set only with normal button type.")
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set text appearance among \"big, medium, small, tiny\".")
            }
        }
    }
}