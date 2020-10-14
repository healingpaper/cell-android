package com.gangnam.sister.cell

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.gangnam.sister.cell.data.Header
import com.gangnam.sister.cell.data.Item
import com.gangnam.sister.cell.util.DisplayManager

class MainAdapter :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private val HEADER_TYPE = 100
    private val ITEM_TYPE = 200
    private val DUMMY_TYPE = 0

    private var itemList = listOf<Any>()
    private var listener: ((text: String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val padding = DisplayManager.dpToPx(parent.context, 8)
        return when (viewType) {
            HEADER_TYPE -> HeaderViewHolder(TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                setTextAppearance(R.style.T02H216BoldCenterBlack)
                setPadding(padding, padding, padding, padding)
            })
            ITEM_TYPE -> ItemViewHolder(TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                setPadding(padding, padding, padding, padding)
                setTextAppearance(R.style.T03Body14RegularCenterOrange)
                setOnClickListener { listener?.invoke(text.toString()) }
            })
            else -> DummyViewHolder(View(parent.context))
        }
    }

    override fun onBindViewHolder(
        vh: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        position: Int
    ) {
        when (vh) {
            is HeaderViewHolder -> vh.v.text = (itemList[position] as Header).title
            is ItemViewHolder -> vh.v.text = (itemList[position] as Item).title
        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is Header -> HEADER_TYPE
            is Item -> ITEM_TYPE
            else -> DUMMY_TYPE
        }
    }

    fun setData(list: List<Any>) {
        itemList = list
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (text: String) -> Unit) {
        this.listener = listener
    }

    /**
     * ViewHolder
     */

    private class HeaderViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val v = itemView as TextView
    }

    private class ItemViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val v = itemView as TextView
    }

    private class DummyViewHolder(itemView: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}