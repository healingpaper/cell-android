package com.gangnam.sister.cell.component.selectset

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.listener.OnItemSelectedListener
import com.gangnam.sister.cell.util.DisplayManager
import com.gangnam.sister.cell.util.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.cell_select_set_view.view.*


class CellSelectSet @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val selectSetAdapter = CellSelectSetAdapter(arrayListOf())
    private val dp8 = DisplayManager.dpToPx(context, 8)
    private var itemSelectedListener: OnItemSelectedListener? = null
    private var itemSelectedFunction: ((ViewGroup, View, Int) -> Unit)? = null

    var text: String? = null
        set(value) {
            field = value
            titleTxt.text = value
            titleTxt.visibility = if (value.isNullOrBlank()) View.GONE else View.VISIBLE
        }

    var selectedIndex: Int? = null
        set(value) {
            field = value
            value?.let { select(it) }
        }

    init {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_select_set_view, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellSelectSet, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellSelectSet_titleText)) {
                        text = it.getString(R.styleable.CellSelectSet_titleText)
                    }
                    if (it.hasValue(R.styleable.CellSelectSet_selectedIndex)) {
                        selectedIndex = it.getInt(R.styleable.CellSelectSet_selectedIndex, 0)
                    }
                    if (it.hasValue(R.styleable.CellSelectSet_itemNameArray)) {
                        val nameArray = it.getTextArray(R.styleable.CellSelectSet_itemNameArray)
                        nameArray?.let { arr -> setData(arr.map { charSequence -> charSequence.toString() }) }
                    }
                    recyclerView.apply {
                        adapter = selectSetAdapter
                        isNestedScrollingEnabled = false
                        addItemDecoration(GridSpacingItemDecoration(2, dp8, dp8))
                    }
                }
    }

    fun setData(nameArray: List<String>) {
        selectSetAdapter.setItems(nameArray)
    }

    private fun select(index: Int) {
        selectSetAdapter.select(index)
    }

    fun clearSelection() {
        selectSetAdapter.clearSelection()
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        selectSetAdapter.setOnItemSelectedListener(listener)
    }

    fun setOnItemSelectedListener(function: (ViewGroup, View, Int) -> Unit) {
        selectSetAdapter.setOnItemSelectedListener(function)
    }
}