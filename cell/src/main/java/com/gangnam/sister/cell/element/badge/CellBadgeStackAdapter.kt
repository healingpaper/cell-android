package com.gangnam.sister.cell.element.badge

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.listener.OnItemClickListener

internal class CellBadgeStackAdapter(val list: ArrayList<CellBadgeStack.Item>) :
    RecyclerView.Adapter<CellBadgeStackAdapter.CellBadgeStackViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    internal var styleType: CellBadge.BadgeStyleType = CellBadge.BadgeStyleType.GRAY
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var appearanceType: CellBadge.BadgeAppearanceType =
        CellBadge.BadgeAppearanceType.MEDIUM
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var hasBadgeRipple: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var drawableStart: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var drawableEnd: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellBadgeStackViewHolder {
        return CellBadgeStackViewHolder(CellBadge(parent.context), itemClickListener)
    }

    override fun onBindViewHolder(holder: CellBadgeStackViewHolder, position: Int) {
        holder.badge.apply {
            val item = list[position]
            text = item.text
            hasBadgeRipple = this@CellBadgeStackAdapter.hasBadgeRipple
            appearanceType = this@CellBadgeStackAdapter.appearanceType
            styleType = item.style ?: this@CellBadgeStackAdapter.styleType
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                item.startIconDrawable ?: drawableStart,
                0,
                item.endIconDrawable ?: drawableEnd,
                0
            )
        }
    }

    override fun getItemCount() = list.size

    fun setData(list: List<CellBadgeStack.Item>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(index: Int) {
        this.list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    internal class CellBadgeStackViewHolder(
        val badge: CellBadge,
        private val itemClickListener: OnItemClickListener?
    ) : RecyclerView.ViewHolder(badge) {
        init {
            badge.setOnClickListener { itemClickListener?.onItemClick(adapterPosition) }
        }
    }
}