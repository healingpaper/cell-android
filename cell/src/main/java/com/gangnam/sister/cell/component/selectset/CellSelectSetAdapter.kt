package com.gangnam.sister.cell.component.selectset

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gangnam.sister.cell.element.selectbox.CellSelectBox
import com.gangnam.sister.cell.listener.OnItemSelectedListener

internal class CellSelectSetAdapter(val list: ArrayList<String>) : RecyclerView.Adapter<CellSelectSetAdapter.CellSelectSetViewHolder>() {
    var selectedIndex: Int? = null
    private var itemSelectedListener: OnItemSelectedListener? = null
    private var itemSelectedFunction: ((ViewGroup, View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellSelectSetViewHolder {
        val selectBox = CellSelectBox(parent.context)
        val holder = CellSelectSetViewHolder(selectBox)
        selectBox.setOnClickListener { view ->
            holder.adapterPosition.let {
                itemSelectedListener?.onItemSelected(parent, view, it)
                itemSelectedFunction?.invoke(parent, view, it)
                select(it)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: CellSelectSetViewHolder, index: Int) {
        holder.selectBox.apply {
            text = list[index]
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            isSelected = index == selectedIndex
        }
    }

    override fun getItemCount() = list.size

    fun setItems(list: List<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun select(index: Int) {
        clearSelection()
        selectedIndex = index
        notifyItemChanged(index)
    }

    fun clearSelection() {
        selectedIndex = null
        notifyDataSetChanged()
    }

    fun setOnItemSelectedListener(itemSelectedListener: OnItemSelectedListener?) {
        this.itemSelectedListener = itemSelectedListener
    }

    fun setOnItemSelectedListener(itemSelectedFunction: (ViewGroup, View, Int) -> Unit) {
        this.itemSelectedFunction = itemSelectedFunction
    }

    internal class CellSelectSetViewHolder(val selectBox: CellSelectBox) : RecyclerView.ViewHolder(selectBox)
}