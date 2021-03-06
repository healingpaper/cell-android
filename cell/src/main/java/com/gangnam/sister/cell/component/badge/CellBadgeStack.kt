package com.gangnam.sister.cell.component.badge

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.res.use
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.element.badge.CellBadge
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.gangnam.sister.cell.listener.OnItemDrawableClickListener
import com.gangnam.sister.cell.util.*

// TODO: Horizontal ViewType 일 때 보이는 뷰 카운트 체크하기
class CellBadgeStack @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val badgeStackAdapter = CellBadgeStackAdapter(arrayListOf())
    private var hideLastDecorator: HideLastDecorator? = null

    var styleType: CellBadge.BadgeStyleType = CellBadge.BadgeStyleType.GRAY
        set(value) {
            field = value
            badgeStackAdapter.styleType = value
        }
    var appearanceType: CellBadge.BadgeAppearanceType = CellBadge.BadgeAppearanceType.MEDIUM
        set(value) {
            field = value
            badgeStackAdapter.appearanceType = value
        }
    var badgeStackViewType: BadgeStackViewType = BadgeStackViewType.BASE
        set(value) {
            field = value
            setBadgeStackViewHeight()
        }
    var isBadgeStackClickable: Boolean = true
        set(value) {
            field = value
            badgeStackAdapter.isBadgeStackClickable = value
        }
    var drawableStart: Int = 0
        set(value) {
            field = value
            badgeStackAdapter.drawableStart = value
        }
    var drawableEnd: Int = 0
        set(value) {
            field = value
            badgeStackAdapter.drawableEnd = value
        }
    private var dividerSize: Int = DisplayManager.dpToPx(context, 8)
        set(value) {
            field = value
            hideLastDecorator = HideLastDecorator(value)
        }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadgeStack, defStyleAttr, 0)
                .use {
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadgeStackClickable)) {
                        isBadgeStackClickable = it.getBoolean(R.styleable.CellBadgeStack_cellBadgeStackClickable, true)
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadgeStackDrawableStart)) {
                        drawableStart = it.getResourceId(R.styleable.CellBadgeStack_cellBadgeStackDrawableStart, 0)
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadgeStackDrawableEnd)) {
                        drawableEnd = it.getResourceId(R.styleable.CellBadgeStack_cellBadgeStackDrawableEnd, 0)
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadgeStackStyle)) {
                        styleType = CellBadge.BadgeStyleType.fromId(it.getInt(R.styleable.CellBadgeStack_cellBadgeStackStyle, 0))
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadgeStackAppearance)) {
                        appearanceType = CellBadge.BadgeAppearanceType.fromId(it.getInt(R.styleable.CellBadgeStack_cellBadgeStackAppearance, 0))
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_cellDividerSize)) {
                        dividerSize = it.getDimensionPixelSize(R.styleable.CellBadgeStack_cellDividerSize, DisplayManager.dpToPx(context, 8))
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadgeStackViewType)) {
                        badgeStackViewType = BadgeStackViewType.fromId(it.getInt(R.styleable.CellBadgeStack_cellBadgeStackViewType, 0))
                    }
                    hideLastDecorator = HideLastDecorator(dividerSize)
                    initAdapter()
                    if (it.hasValue(R.styleable.CellBadgeStack_cellBadges)) {
                        val badges = it.getTextArray(R.styleable.CellBadgeStack_cellBadges)
                        setData(badges.map { charSequence -> charSequence.toString() }.distinct())
                    }
                }
    }

    private fun initAdapter() {
        adapter = badgeStackAdapter
        isNestedScrollingEnabled = false
        setPadding(0, 0, 0, -dividerSize)
        addItemDecoration(OffsetDividerDecoration(0, 0, dividerSize, dividerSize))
        setBadgeStackViewHeight()
    }

    private fun setBadgeStackViewHeight() {
        layoutManager = if (badgeStackViewType == BadgeStackViewType.HORIZONTAL) {
            NonScrollLinearLayoutManager(context, HORIZONTAL, false)
        } else NonScrollFlexboxLayoutManager(context)
        if (badgeStackViewType == BadgeStackViewType.HORIZONTAL) hideLastDecorator?.let { setItemDecoration(it) }
    }

    private fun setItemDecoration(decoration: ItemDecoration) {
        removeItemDecoration(decoration)
        addItemDecoration(decoration)
    }


    fun setData(list: List<String>) {
        badgeStackAdapter.apply {
            appearanceType = this@CellBadgeStack.appearanceType
            setData(list.map { Item(it) }.distinct())
        }
    }

    // Badge 하나하나마다 스타일 지정하고 싶을 때 사용
    fun setDataWithItem(list: List<Item>) {
        badgeStackAdapter.apply {
            appearanceType = this@CellBadgeStack.appearanceType
            setData(list.distinct())
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        if (badgeStackAdapter.itemDrawableClickListener != null) {
            throw IllegalStateException("OnItemClickListener and OnItemDrawableClickListener cannot be set at the same time.")
        }
        badgeStackAdapter.setOnItemClickListener(itemClickListener)
    }

    fun setOnItemDrawableClickListener(itemDrawableClickListener: OnItemDrawableClickListener?) {
        if (badgeStackAdapter.itemClickListener != null) {
            throw IllegalStateException("OnItemClickListener and OnItemDrawableClickListener cannot be set at the same time.")
        }
        badgeStackAdapter.setOnItemDrawableClickListener(itemDrawableClickListener)
    }

    fun removeView(index: Int) = badgeStackAdapter.removeItem(index)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return !isBadgeStackClickable
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return isBadgeStackClickable
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        // TODO: 외부 setAdapter 막기
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        // TODO: 외부 setLayoutManager 막기
    }

    enum class BadgeStackViewType {
        BASE, HORIZONTAL;

        companion object {
            fun fromId(id: Int): BadgeStackViewType {
                values().forEach { if (it.ordinal == id) return it }
                throw IllegalArgumentException("Please set badge type among \"base, horizontal\".")
            }
        }
    }

    data class Item(
            val text: String,
            val style: CellBadge.BadgeStyleType? = null,
            val startIconDrawable: Int? = null,
            val endIconDrawable: Int? = null
    )
}