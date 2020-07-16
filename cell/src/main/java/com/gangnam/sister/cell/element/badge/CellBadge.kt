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
    var badgeColor: BadgeColor = BadgeColor.LIGHT_GRAY
        set(value) {
            field = value
            updateBadgeColor(value)
        }

    var badgeType: BadgeType = BadgeType.BASE
        set(value) {
            field = value
            updateBadgeType(value)
        }
    private val dp4 = DisplayManager.dpToPx(context, 4)
    private val dp8 = DisplayManager.dpToPx(context, 8)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadge, defStyleAttr, 0)
                .use {
                    badgeColor = BadgeColor.fromId(it.getInt(R.styleable.CellBadge_badgeColor, 0))
                    badgeType = BadgeType.fromId(it.getInt(R.styleable.CellBadge_badgeType, 0))
                    maxLines = 1
                    ellipsize = TextUtils.TruncateAt.END
                    includeFontPadding = false
                    gravity = Gravity.CENTER
                    setPadding(dp8, dp4, dp8, dp4)
                }
    }

    private fun updateBadgeColor(badgeColor: BadgeColor) {
        setBackgroundResource(badgeColor.background)
        setTextAppearance(badgeColor.textAppearance)
    }

    private fun updateBadgeType(badgeType: BadgeType) {
        val xBtnDrawable = if (badgeType == BadgeType.DELETABLE) R.drawable.ic_deletable_bage_x_btn else 0
        val hashTagDrawable = if (badgeType == BadgeType.CLICKABLE) R.drawable.ic_etc_hashtag else 0
        compoundDrawablePadding = dp4
        setCompoundDrawablesRelativeWithIntrinsicBounds(hashTagDrawable, 0, xBtnDrawable, 0)
        setClickableAndFocusable(badgeType)
    }

    private fun setClickableAndFocusable(badgeType: BadgeType) {
        if (badgeType != BadgeType.BASE) {
            foreground = ContextCompat.getDrawable(context, R.drawable.ripple_default_radius4)
            isClickable = true
            isFocusable = true
        } else {
            isClickable = false
            isFocusable = false
        }
    }

    enum class BadgeColor(val background: Int, val textAppearance: Int) {
        LEMONADE(R.drawable.rect_lemonade_radius4, R.style.T04Label12MediumLeftOrange),
        LIGHT_GRAY(R.drawable.rect_light_gray_radius4, R.style.T04Label12MediumLeftCharcoal);

        companion object {
            fun fromId(id: Int): BadgeColor {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge color among \"lemonade, light_gray\".")
            }
        }
    }

    enum class BadgeType {
        BASE, CLICKABLE, DELETABLE;

        companion object {
            fun fromId(id: Int): BadgeType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge type among \"base, clickable, deletable\".")
            }
        }
    }
}