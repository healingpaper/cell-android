package com.gangnam.sister.cell.element.badge

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.util.DisplayManager

class CellBadge @JvmOverloads constructor(
context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    var badgeStyle: BadgeStyle = BadgeStyle.LIGHT_GRAY
        set(value) {
            field = value
            updateBadgeStyle(value)
        }

    var badgeSize: BadgeSize = BadgeSize.MEDIUM
        set(value) {
            field = value
            updateBadgeSize(value)
        }
    private val dp4 = DisplayManager.dpToPx(context, 4)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadge, defStyleAttr, 0)
            .use {
                badgeSize = BadgeSize.fromId(it.getInt(R.styleable.CellBadge_badgeSize, 0))
                badgeStyle = BadgeStyle.fromId(it.getInt(R.styleable.CellBadge_badgeStyle, 0))
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
                gravity = Gravity.CENTER
                compoundDrawablePadding = dp4
            }
    }

    private fun updateBadgeStyle(badgeStyle: BadgeStyle) {
        setBackgroundResource(badgeStyle.background)
        setTextColor(ContextCompat.getColor(context, badgeStyle.textColor))
    }

    private fun updateBadgeSize(badgeSize: BadgeSize) {
        val topBottomPaddingSize = DisplayManager.dpToPx(context, badgeSize.topBottomPaddingSize)
        val startEndPaddingSize = DisplayManager.dpToPx(context, badgeSize.startEndPaddingSize)
        height = DisplayManager.dpToPx(context, badgeSize.height)
        setTextAppearance(badgeSize.textStyle)
        setPadding(startEndPaddingSize, topBottomPaddingSize, startEndPaddingSize, topBottomPaddingSize)
    }

    enum class BadgeStyle(val background: Int, val textColor: Int) {
        ORANGE(R.drawable.rect_fill_orange_radius14, R.color.latte),
        LEMONADE(R.drawable.rect_fill_lemonade_radius14, R.color.latte),
        LIGHT_GRAY(R.drawable.rect_fill_light_gray_radius14, R.color.black);

        companion object {
            fun fromId(id: Int): BadgeStyle {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge style among \"orange, lemonade, light_gray\".")
            }
        }
    }

    enum class BadgeSize(val height: Int, val textStyle: Int, val topBottomPaddingSize: Int, val startEndPaddingSize: Int) {
        MEDIUM(28, R.style.T04Label12MediumCenterBlack, 6, 8),
        SMALL(20, R.style.T05Tiny10MediumCenterBlack, 2, 6);

        companion object {
            fun fromId(id: Int): BadgeSize {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge size among \"medium, small\".")
            }
        }
    }
}