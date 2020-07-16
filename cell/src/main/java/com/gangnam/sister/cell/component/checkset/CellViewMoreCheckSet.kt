package com.gangnam.sister.cell.component.checkset

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import kotlinx.android.synthetic.main.cell_view_more_check_set_view.view.*

class CellViewMoreCheckSet @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var text: String? = null
        set(value) {
            field = value
            checkbox.text = value
        }
    var isChecked: Boolean = false
        set(value) {
            field = value
            checkbox.isChecked = value
        }

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_view_more_check_set_view, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellViewMoreCheckSet, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellViewMoreCheckSet_android_text)) {
                        text = it.getString(R.styleable.CellViewMoreCheckSet_android_text)
                    }
                    if (it.hasValue(R.styleable.CellViewMoreCheckSet_android_checked)) {
                        isChecked = it.getBoolean(R.styleable.CellViewMoreCheckSet_android_checked, false)
                    }
                }
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }

    fun setOnSeeMoreButtonClickListener(listener: OnClickListener) {
        seeMoreTxt.setOnClickListener(listener)
    }
}