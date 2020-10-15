package com.gangnam.sister.cell.element.badge

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

// TODO: CompoundDrawable Tint 및 Size 먹이기
class CellBadge @JvmOverloads constructor(
context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    var styleType: BadgeStyleType = BadgeStyleType.GRAY
        set(value) {
            field = value
            updateBadgeStyle(value.style(context), isEnabled, hasBadgeRipple)
        }

    var appearanceType: BadgeAppearanceType = BadgeAppearanceType.MEDIUM
        set(value) {
            field = value
            updateBadgeSize(styleType.style(context), appearanceType)
        }

    var hasBadgeRipple: Boolean = true
        set(value) {
            field = value
            updateBadgeStyle(styleType.style(context), isEnabled, hasBadgeRipple)
        }

    private var badgeStyle: BadgeStyle? = null
    private val dp4 = DisplayManager.dpToPx(context, 4)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        var style: BadgeStyle
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadge, defStyleAttr, 0)
            .use {
                if (it.hasValue(R.styleable.CellBadge_hasCellBadgeRipple)){
                    hasBadgeRipple = it.getBoolean(R.styleable.CellBadge_hasCellBadgeRipple, false)
                }
                if (it.hasValue(R.styleable.CellBadge_cellBadgeAppearance)){
                    appearanceType = BadgeAppearanceType.fromId(it.getInt(R.styleable.CellBadge_cellBadgeAppearance, 0))
                }
                if (it.hasValue(R.styleable.CellBadge_cellBadgeStyle)){
                    styleType = BadgeStyleType.fromId(it.getInt(R.styleable.CellBadge_cellBadgeStyle, 0))
                }
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
                gravity = Gravity.CENTER
                compoundDrawablePadding = dp4

                style = BadgeStyle.createFromAttribute(context, it, styleType.style(context))
                applyStyle(style)
            }
    }

    private fun applyStyle(style: BadgeStyle) {
        this.badgeStyle = style
        updateBadgeSize(style, appearanceType)
        updateBadgeStyle(style, isEnabled, hasBadgeRipple)
    }

    private fun updateBadgeStyle(
        badgeStyle: BadgeStyle,
        isEnabled: Boolean,
        hasBadgeRipple: Boolean
    ) {
        background = badgeStyle.getBadgeBackground(isEnabled, hasBadgeRipple)
        setTextColor(badgeStyle.getTextColor(isEnabled))
    }

    private fun updateBadgeSize(badgeStyle: BadgeStyle, appearanceType: BadgeAppearanceType) {
        val topBottomPadding = badgeStyle.getBadgeTopBottomPadding(appearanceType)
        val startEndPadding = badgeStyle.getBadgeStartEndPadding(appearanceType)
        setPadding(startEndPadding, topBottomPadding, startEndPadding, topBottomPadding)
        setTextAppearance(badgeStyle.getTextStyle(appearanceType))
    }

    enum class BadgeStyleType(val style: (Context) -> BadgeStyle) {
        GRAY(BadgeStyles.Gray),
        ORANGE(BadgeStyles.Orange),
        YELLOW(BadgeStyles.Yellow);

        companion object {
            fun fromId(id: Int): BadgeStyleType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge style among \"gray, orange, yellow\".")
            }
        }
    }

    enum class BadgeAppearanceType {
        MEDIUM,
        SMALL;

        companion object {
            fun fromId(id: Int): BadgeAppearanceType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge size among \"medium, small\".")
            }
        }
    }
}