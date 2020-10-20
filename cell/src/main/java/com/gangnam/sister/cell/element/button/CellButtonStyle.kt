package com.gangnam.sister.cell.element.button

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager
import com.gangnam.sister.cell.util.DrawableManager

class CellButtonStyle(
        private val context: Context,
        private val style: CellButtonStyles,
        @ColorInt private val backgroundColor: Int,
        @ColorInt private val textColor: Int,
        @ColorInt private val borderColor: Int,
        @ColorInt private val disabledBackgroundColor: Int,
        @ColorInt private val disabledTextColor: Int,
        @ColorInt private val rippleColor: Int,
        @Px private val borderWidth: Int,
        @Px private val radius: Int
) {
    private val smallMinHeight = DisplayManager.dpToPx(context, 32)
    private val mediumMinHeight = DisplayManager.dpToPx(context, 36)
    private val largeMinHeight = DisplayManager.dpToPx(context, 52)
    private val smallMinWidth = DisplayManager.dpToPx(context, 64)
    private val mediumMinWidth = DisplayManager.dpToPx(context, 76)
    private val largeMinWidth = DisplayManager.dpToPx(context, 148)

    fun getTextColor(enabled: Boolean): Int {
        return if (enabled) textColor else disabledTextColor
    }

    fun getButtonBackground(enabled: Boolean): Drawable {
        return if (enabled) {
            DrawableManager.rippleDrawable(
                mask = DrawableManager.roundRectDrawable(
                    color = Color.WHITE,
                    radius = radius
                ),
                content = DrawableManager.roundRectDrawable(
                    color = backgroundColor,
                    borderColor = borderColor,
                    borderWidth = borderWidth,
                    radius = radius
                ),
                rippleColor = rippleColor
            )
        } else {
            DrawableManager.roundRectDrawable(
                color = disabledBackgroundColor,
                borderColor = borderColor,
                borderWidth = borderWidth,
                radius = radius
            )
        }
    }

    fun getButtonMinHeight(appearanceType: CellButton.ButtonAppearanceType): Int {
        return when (appearanceType) {
            CellButton.ButtonAppearanceType.LARGE -> largeMinHeight
            CellButton.ButtonAppearanceType.MEDIUM -> mediumMinHeight
            CellButton.ButtonAppearanceType.SMALL -> smallMinHeight
        }
    }

    fun getButtonMinWidth(appearanceType: CellButton.ButtonAppearanceType): Int {
        return when (appearanceType) {
            CellButton.ButtonAppearanceType.LARGE -> largeMinWidth
            CellButton.ButtonAppearanceType.MEDIUM -> mediumMinWidth
            CellButton.ButtonAppearanceType.SMALL -> smallMinWidth
        }
    }


    fun getCellButtonTextStyle(
        enabled: Boolean,
        appearanceType: CellButton.ButtonAppearanceType
    ): Int {
        return if (enabled) {
            when (appearanceType) {
                CellButton.ButtonAppearanceType.LARGE -> R.style.T02H216BoldCenterBlack
                CellButton.ButtonAppearanceType.MEDIUM -> R.style.T03Body14BoldCenterBlack
                CellButton.ButtonAppearanceType.SMALL -> {
                    when (style) {
                        CellButtonStyles.Primary, CellButtonStyles.Secondary -> R.style.T04Label12BoldCenterBlack
                        else -> R.style.T04Label12MediumCenterBlack
                    }
                }
            }
        } else {
            when (appearanceType) {
                CellButton.ButtonAppearanceType.LARGE -> R.style.T02H216BoldCenterBlack
                CellButton.ButtonAppearanceType.MEDIUM -> R.style.T03Body14BoldCenterBlack
                CellButton.ButtonAppearanceType.SMALL -> R.style.T04Label12MediumCenterBlack
            }
        }
    }

    companion object {

        fun create(
                context: Context,
                style: CellButtonStyles,
                @ColorRes backgroundColorRes: Int,
                @ColorRes textColorRes: Int,
                @ColorRes borderColorRes: Int = android.R.color.transparent,
                @ColorRes disabledBackgroundColorRes: Int,
                @ColorRes disabledTextColorRes: Int,
                @ColorRes rippleColorRes: Int = android.R.color.transparent,
                @DimenRes borderWidthRes: Int = R.dimen.zero,
                @DimenRes radiusRes: Int = R.dimen.tiny
        ): CellButtonStyle {
            return CellButtonStyle(
                context = context,
                style = style,
                backgroundColor = ContextCompat.getColor(context, backgroundColorRes),
                textColor = ContextCompat.getColor(context, textColorRes),
                borderColor = ContextCompat.getColor(context, borderColorRes),
                disabledBackgroundColor = ContextCompat.getColor(
                    context,
                    disabledBackgroundColorRes
                ),
                disabledTextColor = ContextCompat.getColor(context, disabledTextColorRes),
                rippleColor = ContextCompat.getColor(context, rippleColorRes),
                borderWidth = context.resources.getDimensionPixelSize(borderWidthRes),
                radius = context.resources.getDimensionPixelSize(radiusRes)
            )
        }

        fun createFromAttribute(
            context: Context,
            typedArray: TypedArray?,
            originalStyle: CellButtonStyle
        ): CellButtonStyle {
            var backgroundColor = originalStyle.backgroundColor
            var textColor = originalStyle.textColor
            var borderColor = originalStyle.borderColor
            var borderWidth = originalStyle.borderWidth
            var rippleColor = originalStyle.rippleColor
            typedArray?.let {
                backgroundColor = it.getColor(R.styleable.CellButton_cellButtonBackgroundColor, backgroundColor)
                textColor = it.getColor(R.styleable.CellButton_cellButtonTextColor, textColor)
                rippleColor = it.getColor(R.styleable.CellButton_cellButtonRippleColor, rippleColor)
                borderColor = it.getColor(R.styleable.CellButton_cellButtonBorderColor, borderColor)
                borderWidth = it.getDimension(R.styleable.CellButton_cellButtonBorderWidth, borderWidth.toFloat()).toInt()
            }
            return CellButtonStyle(
                context = context,
                style = originalStyle.style,
                backgroundColor = backgroundColor,
                textColor = textColor,
                borderColor = borderColor,
                disabledBackgroundColor = originalStyle.disabledBackgroundColor,
                disabledTextColor = originalStyle.disabledTextColor,
                rippleColor = originalStyle.rippleColor,
                borderWidth = borderWidth,
                radius = originalStyle.radius
            )
        }
    }
}