package com.gangnam.sister.cell.element.badge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.extension.containsWithSensitivity
import com.gangnam.sister.cell.util.DisplayManager

// TODO: CompoundDrawable Tint 및 Size 먹이기
class CellBadge @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    var styleType: BadgeStyleType = BadgeStyleType.GRAY
        set(value) {
            field = value
            updateBadgeStyle(value.style(context), isEnabled, isBadgeClickable)
        }

    var appearanceType: BadgeAppearanceType = BadgeAppearanceType.MEDIUM
        set(value) {
            field = value
            updateBadgeSize(styleType.style(context), appearanceType)
        }

    /**
     * Badge 전체가 클릭가능한지 여부
     */
    var isBadgeClickable: Boolean = true
        set(value) {
            field = value
            updateBadgeStyle(styleType.style(context), isEnabled, isBadgeClickable)
        }

    private var badgeStyle: CellBadgeStyle? = null
    private var drawableClickListener: OnDrawableClickListener? = null
    private val dp4 = DisplayManager.dpToPx(context, 4)

    init {
        initView(attrs, defStyleAttr)
    }

    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        var style: CellBadgeStyle
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadge, defStyleAttr, 0)
                .use {
                    if (it.hasValue(R.styleable.CellBadge_cellBadgeClickable)) {
                        isBadgeClickable =
                                it.getBoolean(R.styleable.CellBadge_cellBadgeClickable, false)
                    }
                    if (it.hasValue(R.styleable.CellBadge_cellBadgeAppearance)) {
                        appearanceType = BadgeAppearanceType.fromId(
                                it.getInt(
                                        R.styleable.CellBadge_cellBadgeAppearance,
                                        0
                                )
                        )
                    }
                    if (it.hasValue(R.styleable.CellBadge_cellBadgeStyle)) {
                        styleType =
                                BadgeStyleType.fromId(it.getInt(R.styleable.CellBadge_cellBadgeStyle, 0))
                    }
                    gravity = Gravity.CENTER
                    compoundDrawablePadding = dp4

                    style = CellBadgeStyle.createFromAttribute(context, it, styleType.style(context))
                    applyStyle(style)
                }
    }

    private fun applyStyle(style: CellBadgeStyle) {
        this.badgeStyle = style
        updateBadgeSize(style, appearanceType)
        updateBadgeStyle(style, isEnabled, isBadgeClickable)
    }

    private fun updateBadgeStyle(
            badgeStyle: CellBadgeStyle,
            isEnabled: Boolean,
            isBadgeClickable: Boolean
    ) {
        background = badgeStyle.getBadgeBackground(isEnabled, isBadgeClickable)
        setTextColor(badgeStyle.getTextColor(isEnabled))
    }

    private fun updateBadgeSize(badgeStyle: CellBadgeStyle, appearanceType: BadgeAppearanceType) {
        val topBottomPadding = badgeStyle.getBadgeTopBottomPadding(appearanceType)
        val startEndPadding = badgeStyle.getBadgeStartEndPadding(appearanceType)
        setPadding(startEndPadding, topBottomPadding, startEndPadding, topBottomPadding)
        setTextAppearance(badgeStyle.getTextStyle(appearanceType))
    }

    /**
     * Badge 전체가 클릭가능하면 무시됨.
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isBadgeClickable || drawableClickListener == null || event.action != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event)
        }
        val actionX = event.x.toInt()
        val actionY = event.y.toInt()
        val drawableStart: Drawable? = compoundDrawables[0]
        val drawableEnd: Drawable? = compoundDrawables[2]
        drawableStart?.let {
            val bounds = drawableStart.bounds
            val horizontalSensitivity = 12
            val verticalSensitivity = 60
            var x = actionX - bounds.right
            var y = actionY
            if (x <= 0) x = actionX
            if (bounds.containsWithSensitivity(x, y, horizontalSensitivity, verticalSensitivity)) {
                drawableClickListener?.onClick(OnDrawableClickListener.DrawablePosition.START)
                event.action = MotionEvent.ACTION_CANCEL
                return false
            }
        }
        drawableEnd?.let {
            val bounds = drawableEnd.bounds
            val horizontalSensitivity = 12
            val verticalSensitivity = 60
            var x = width - bounds.right - actionX
            var y = actionY
            if (x <= 0) x = actionX
            if (bounds.containsWithSensitivity(x, y, horizontalSensitivity, verticalSensitivity)) {
                drawableClickListener?.onClick(OnDrawableClickListener.DrawablePosition.END)
                event.action = MotionEvent.ACTION_CANCEL
                return false
            }
        }
        return super.onTouchEvent(event)
    }

    fun setDrawableClickListener(listener: OnDrawableClickListener) {
        this.drawableClickListener = listener
    }

    enum class BadgeStyleType(val style: (Context) -> CellBadgeStyle) {
        GRAY(CellBadgeStyles.Gray),
        ORANGE(CellBadgeStyles.Orange),
        YELLOW(CellBadgeStyles.Yellow);

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