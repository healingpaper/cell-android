package com.gangnam.sister.cell.element.badge

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager
import com.gangnam.sister.cell.util.DrawableManager

class CellBadgeStyle(
    private val context: Context,
    @ColorInt private val backgroundColor: Int,
    @ColorInt private val textColor: Int,
    @ColorInt private val borderColor: Int,
    @ColorInt private val disabledBackgroundColor: Int,
    @ColorInt private val disabledTextColor: Int,
    @Px private val borderWidth: Int,
    @Px private val radius: Int
) {
    private val smallTopBottomPadding = DisplayManager.dpToPx(context, 2)
    private val smallStartEndPadding = DisplayManager.dpToPx(context, 6)
    private val mediumTopBottomPadding = DisplayManager.dpToPx(context, 6)
    private val mediumStartEndPadding = DisplayManager.dpToPx(context, 8)

    fun getTextColor(enabled: Boolean): Int {
        return if (enabled) textColor else disabledTextColor
    }

    fun getBadgeBackground(enabled: Boolean, clickable: Boolean): Drawable {
        return if (clickable) {
            getBadgeRippleBackground(enabled)
        } else getBadgeBackground(enabled)
    }

    private fun getBadgeRippleBackground(enabled: Boolean): RippleDrawable {
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

    private fun getBadgeBackground(enabled: Boolean): Drawable {
        return if (enabled) {
            DrawableManager.roundRectDrawable(
                color = backgroundColor,
                borderColor = borderColor,
                borderWidth = borderWidth,
                radius = radius
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

    fun getBadgeTopBottomPadding(appearanceType: CellBadge.BadgeAppearanceType): Int {
        return when (appearanceType) {
            CellBadge.BadgeAppearanceType.MEDIUM -> mediumTopBottomPadding
            CellBadge.BadgeAppearanceType.SMALL -> smallTopBottomPadding
        }
    }

    fun getBadgeStartEndPadding(appearanceType: CellBadge.BadgeAppearanceType): Int {
        return when (appearanceType) {
            CellBadge.BadgeAppearanceType.MEDIUM -> mediumStartEndPadding
            CellBadge.BadgeAppearanceType.SMALL -> smallStartEndPadding
        }
    }

    fun getTextStyle(appearanceType: CellBadge.BadgeAppearanceType): Int {
        return when (appearanceType) {
            CellBadge.BadgeAppearanceType.MEDIUM -> R.style.T04Label12MediumCenterBlack
            CellBadge.BadgeAppearanceType.SMALL -> R.style.T05Tiny9MediumCenterBlack
        }
    }

    companion object {

        fun create(
            context: Context,
            @ColorRes backgroundColorRes: Int,
            @ColorRes textColorRes: Int,
            @ColorRes borderColorRes: Int = android.R.color.transparent,
            @ColorRes disabledBackgroundColorRes: Int,
            @ColorRes disabledTextColorRes: Int,
            @DimenRes borderWidthRes: Int = R.dimen.zero,
            @DimenRes radiusRes: Int = R.dimen.badgeRadius
        ): CellBadgeStyle {
            return CellBadgeStyle(
                context = context,
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

        fun createFromAttribute(
            context: Context,
            typedArray: TypedArray?,
            originalStyle: CellBadgeStyle
        ): CellBadgeStyle {
            var backgroundColor = originalStyle.backgroundColor
            var textColor = originalStyle.textColor
            var borderColor = originalStyle.borderColor
            var borderWidth = originalStyle.borderWidth
            typedArray?.let {
                backgroundColor =
                    it.getColor(R.styleable.CellBadge_cellBadgeBackgroundColor, backgroundColor)
                textColor = it.getColor(R.styleable.CellBadge_cellBadgeTextColor, textColor)
                borderColor = it.getColor(R.styleable.CellBadge_cellBadgeBorderColor, borderColor)
                borderWidth = it.getDimension(
                    R.styleable.CellBadge_cellBadgeBorderWidth,
                    borderWidth.toFloat()
                ).toInt()
            }
            return CellBadgeStyle(
                context = context,
                backgroundColor = backgroundColor,
                textColor = textColor,
                borderColor = borderColor,
                disabledBackgroundColor = originalStyle.disabledBackgroundColor,
                disabledTextColor = originalStyle.disabledTextColor,
                borderWidth = borderWidth,
                radius = originalStyle.radius
            )
        }
    }
}