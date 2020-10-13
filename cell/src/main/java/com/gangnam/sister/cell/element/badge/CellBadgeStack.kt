package com.gangnam.sister.cell.element.badge

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.gangnam.sister.cell.util.DisplayManager
import com.gangnam.sister.cell.util.OffsetDividerDecoration
import com.google.android.flexbox.FlexboxLayoutManager

// TODO: Horizontal ViewType 일 때 보이는 뷰 카운트 체크하기
class CellBadgeStack @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val badgeStackAdapter = CellBadgeStackAdapter(arrayListOf())

    var badgeStyle: CellBadge.BadgeStyle = CellBadge.BadgeStyle.LIGHT_GRAY
        set(value) {
            field = value
            badgeStackAdapter.notifyDataSetChanged()
        }
    var badgeSize: CellBadge.BadgeSize = CellBadge.BadgeSize.MEDIUM
        set(value) {
            field = value
            badgeStackAdapter.badgeSize = this@CellBadgeStack.badgeSize
        }
    var badgeStackViewType: BadgeStackViewType = BadgeStackViewType.BASE
        set(value) {
            field = value
            setBadgeStackViewHeight()
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadgeStack, defStyleAttr, 0)
            .use {
                if (it.hasValue(R.styleable.CellBadgeStack_badgeStackStyle)) {
                    badgeStyle = CellBadge.BadgeStyle.fromId(
                        it.getInt(
                            R.styleable.CellBadgeStack_badgeStackStyle,
                            0
                        )
                    )
                }
                if (it.hasValue(R.styleable.CellBadgeStack_badgeStackSize)) {
                    badgeSize = CellBadge.BadgeSize.fromId(
                        it.getInt(
                            R.styleable.CellBadgeStack_badgeStackSize,
                            0
                        )
                    )
                }
                val dividerSize = it.getDimensionPixelSize(
                    R.styleable.CellBadgeStack_dividerSize,
                    DisplayManager.dpToPx(context, 8)
                )
                badgeStackViewType = BadgeStackViewType.fromId(
                    it.getInt(
                        R.styleable.CellBadgeStack_badgeStackViewType,
                        0
                    )
                )
                adapter = badgeStackAdapter
                layoutManager = FlexboxLayoutManager(context)
                isNestedScrollingEnabled = false
                setPadding(0, 0, 0, -dividerSize)
                addItemDecoration(OffsetDividerDecoration(0, 0, dividerSize, dividerSize))
                if (it.hasValue(R.styleable.CellBadgeStack_badges)) {
                    val badges = it.getTextArray(R.styleable.CellBadgeStack_badges)
                    setData(badges.map { charSequence -> charSequence.toString() }.distinct())
                }
            }
    }

    private fun setBadgeStackViewHeight() {
        post {
            if (badgeStackViewType == BadgeStackViewType.HORIZONTAL) {
                if (children.toCollection(arrayListOf()).isNotEmpty())
                    updateLayoutParams { height = children.first().height }
            } else updateLayoutParams { height = LayoutParams.WRAP_CONTENT }
        }
    }

    fun setData(list: List<String>) {
        badgeStackAdapter.apply {
            badgeSize = this@CellBadgeStack.badgeSize
            setData(list.map {Item(it, this@CellBadgeStack.badgeStyle) }.distinct())
        }
    }

    // Badge 하나하나마다 스타일 지정하고 싶을 때 사용
    fun setDataWithItem(list: List<Item>) {
        badgeStackAdapter.apply {
            badgeSize = this@CellBadgeStack.badgeSize
            setData(list.distinct())
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        badgeStackAdapter.setOnItemClickListener(itemClickListener)
    }

    fun removeView(index: Int) = badgeStackAdapter.removeItem(index)

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
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
        val style: CellBadge.BadgeStyle,
        val startIconDrawable: Int = 0,
        val endIconDrawable: Int = 0
    )
}