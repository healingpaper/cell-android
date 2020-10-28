package com.gangnam.sister.cell.component.badge

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.element.badge.CellBadge
import com.gangnam.sister.cell.element.badge.OnDrawableClickListener
import com.gangnam.sister.cell.listener.OnItemClickListener
import com.gangnam.sister.cell.listener.OnItemDrawableClickListener

internal class CellBadgeStackAdapter(val list: ArrayList<CellBadgeStack.Item>) :
        RecyclerView.Adapter<CellBadgeStackAdapter.CellBadgeStackViewHolder>() {
    internal var itemClickListener: OnItemClickListener? = null
    internal var itemDrawableClickListener: OnItemDrawableClickListener? = null

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

    internal var isBadgeStackClickable: Boolean = false
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
        return CellBadgeStackViewHolder(CellBadge(parent.context), itemClickListener, itemDrawableClickListener)
    }

    override fun onBindViewHolder(holder: CellBadgeStackViewHolder, position: Int) {
        holder.badge.apply {
            val item = list[position]
            isBadgeClickable = this@CellBadgeStackAdapter.isBadgeStackClickable
            appearanceType = this@CellBadgeStackAdapter.appearanceType
            styleType = item.style ?: this@CellBadgeStackAdapter.styleType
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                    item.startIconDrawable ?: drawableStart,
                    0,
                    item.endIconDrawable ?: drawableEnd,
                    0
            )
            text = item.text
        }
    }

    override fun getItemCount() = list.size

    fun setData(list: List<CellBadgeStack.Item>) {
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeChanged(0, this.list.size)
    }

    fun removeItem(index: Int) {
        this.list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    fun setOnItemDrawableClickListener(itemDrawableClickListener: OnItemDrawableClickListener?) {
        this.itemDrawableClickListener = itemDrawableClickListener
    }

    internal class CellBadgeStackViewHolder(
            val badge: CellBadge,
            private val itemClickListener: OnItemClickListener?,
            private val itemDrawableClickListener: OnItemDrawableClickListener?
    ) : RecyclerView.ViewHolder(badge) {
        init {
            badge.setOnClickListener { itemClickListener?.onItemClick(adapterPosition) }
            badge.setDrawableClickListener(object : OnDrawableClickListener {
                override fun onClick(position: OnDrawableClickListener.DrawablePosition) {
                    itemDrawableClickListener?.onItemClick(adapterPosition, position)
                }
            })
        }
    }
}