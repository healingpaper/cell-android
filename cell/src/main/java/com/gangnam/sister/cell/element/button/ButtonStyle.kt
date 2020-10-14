package com.gangnam.sister.cell.element.button

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager
import com.gangnam.sister.cell.util.DrawableManager

class ButtonStyle(
    private val context: Context,
    private val style: ButtonStyles,
    @ColorInt private val backgroundColor: Int,
    @ColorInt private val textColor: Int,
    @ColorInt private val borderColor: Int,
    @ColorInt private val disabledBackgroundColor: Int,
    @ColorInt private val disabledTextColor: Int,
    @Px private val borderWidth: Int,
    @Px private val radius: Int
) {
    private val smallHeight = DisplayManager.dpToPx(context, 32)
    private val mediumHeight = DisplayManager.dpToPx(context, 36)
    private val largeHeight = DisplayManager.dpToPx(context, 52)

    fun getTextColor(enabled: Boolean): Int {
        return if (enabled) textColor else disabledTextColor
    }

    fun getButtonBackground(enabled: Boolean): Drawable {
        return if (enabled) {
            DrawableManager.rippleDrawable(
                mask = DrawableManager.roundRectDrawable(
                    color = ContextCompat.getColor(context, R.color.black_30_percent),
                    radius = radius
                ),
                content = DrawableManager.roundRectDrawable(
                    color = backgroundColor,
                    borderColor = borderColor,
                    borderWidth = borderWidth,
                    radius = radius
                ),
                rippleColor = ContextCompat.getColor(context, R.color.black_30_percent)
            )
        } else {
            DrawableManager.rippleDrawable(
                mask = DrawableManager.roundRectDrawable(
                    color = ContextCompat.getColor(context, R.color.black_30_percent),
                    radius = radius
                ),
                content = DrawableManager.roundRectDrawable(
                    color = disabledBackgroundColor,
                    borderColor = borderColor,
                    borderWidth = borderWidth,
                    radius = radius
                ),
                rippleColor = ContextCompat.getColor(context, R.color.black_30_percent)
            )
        }
    }

    fun getButtonHeight(appearanceType: CellButton.ButtonAppearanceType): Int {
        return when (appearanceType) {
            CellButton.ButtonAppearanceType.LARGE -> largeHeight
            CellButton.ButtonAppearanceType.MEDIUM -> mediumHeight
            CellButton.ButtonAppearanceType.SMALL -> smallHeight
        }
    }

    fun getCellButtonTextStyle(appearanceType: CellButton.ButtonAppearanceType): Int {
        return when (appearanceType) {
            CellButton.ButtonAppearanceType.LARGE -> R.style.T02H216BoldCenterBlack
            CellButton.ButtonAppearanceType.MEDIUM -> R.style.T03Body14BoldCenterBlack
            CellButton.ButtonAppearanceType.SMALL -> {
                when (style) {
                    ButtonStyles.Primary, ButtonStyles.Secondary -> R.style.T04Label12BoldCenterBlack
                    else -> R.style.T04Label12MediumCenterBlack
                }
            }
        }
    }

    companion object {

        fun create(
            context: Context,
            style: ButtonStyles,
            @ColorRes backgroundColorRes: Int,
            @ColorRes textColorRes: Int,
            @ColorRes borderColorRes: Int = android.R.color.transparent,
            @ColorRes disabledBackgroundColorRes: Int,
            @ColorRes disabledTextColorRes: Int,
            @DimenRes borderWidthRes: Int = R.dimen.zero,
            @DimenRes radiusRes: Int = R.dimen.tiny
        ): ButtonStyle {
            return ButtonStyle(
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
                borderWidth = context.resources.getDimensionPixelSize(borderWidthRes),
                radius = context.resources.getDimensionPixelSize(radiusRes)
            )
        }
    }
}