package com.gangnam.sister.cell.element.badge

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.listener.OnItemClickListener

internal class CellBadgeStackAdapter(val list: ArrayList<String>) : RecyclerView.Adapter<CellBadgeStackAdapter.CellBadgeStackViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null
    internal var badgeColor: CellBadge.BadgeColor = CellBadge.BadgeColor.LIGHT_GRAY
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    internal var badgeType: CellBadge.BadgeType = CellBadge.BadgeType.BASE
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellBadgeStackViewHolder {
        return CellBadgeStackViewHolder(CellBadge(parent.context), itemClickListener)
    }

    override fun onBindViewHolder(holder: CellBadgeStackViewHolder, position: Int) {
        holder.badge.apply {
            text = list[position]
            badgeColor = this@CellBadgeStackAdapter.badgeColor
            badgeType = this@CellBadgeStackAdapter.badgeType
        }
    }

    override fun getItemCount() = list.size

    fun setItems(list: List<String>) {
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