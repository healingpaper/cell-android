package com.gangnam.sister.cell.element.badge

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager

// TODO: Horizontal ViewType 일 때 보이는 뷰 카운트 체크하기
class CellBadgeStack @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val badgeStackAdapter = CellBadgeStackAdapter(arrayListOf())

    var badgeColor: CellBadge.BadgeColor = CellBadge.BadgeColor.LIGHT_GRAY
        set(value) {
            field = value
            badgeStackAdapter.badgeColor = this@CellBadgeStack.badgeColor
        }
    var badgeType: CellBadge.BadgeType = CellBadge.BadgeType.BASE
        set(value) {
            field = value
            badgeStackAdapter.badgeType = this@CellBadgeStack.badgeType
        }
    var badgeStackViewType: BadgeStackViewType = BadgeStackViewType.BASE
        set(value) {
            field = value
            setBadgeStackViewHeight()
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellBadgeStack, defStyleAttr, 0)
                .use {
                    if (it.hasValue(R.styleable.CellBadgeStack_badgeStackColor)) {
                        badgeColor = CellBadge.BadgeColor.fromId(it.getInt(R.styleable.CellBadgeStack_badgeStackColor, 0))
                    }
                    if (it.hasValue(R.styleable.CellBadgeStack_badgeStackType)) {
                        badgeType = CellBadge.BadgeType.fromId(it.getInt(R.styleable.CellBadgeStack_badgeStackType, 0))
                    }
                    val dividerDrawable = it.getDrawable(R.styleable.CellBadgeStack_dividerDrawable) ?: ContextCompat.getDrawable(context, R.drawable.divider_transparents_tiny)
                    badgeStackViewType = BadgeStackViewType.fromId(it.getInt(R.styleable.CellBadgeStack_badgeStackViewType, 0))
                    adapter = badgeStackAdapter
                    layoutManager = FlexboxLayoutManager(context)
                    isNestedScrollingEnabled = false
                    addItemDecoration(FlexboxItemDecoration(context).apply {
                        setDrawable(dividerDrawable)
                        setOrientation(FlexboxItemDecoration.BOTH)
                    })
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
            badgeColor = this@CellBadgeStack.badgeColor
            badgeType = this@CellBadgeStack.badgeType
            setItems(list.distinct())
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        badgeStackAdapter.setOnItemClickListener(itemClickListener)
    }

    fun removeView(index: Int) {
        if (badgeType != CellBadge.BadgeType.DELETABLE) throw IllegalAccessException("This method can be called only with \"BadgeType: Deletable.\"")
        badgeStackAdapter.removeItem(index)
    }

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
}