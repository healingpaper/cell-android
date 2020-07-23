package com.gangnam.sister.cell.component.uicontroller

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.res.use
import androidx.core.view.children
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.listener.OnItemSelectedListener
import com.gangnam.sister.cell.util.DisplayManager
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.cell_segment_controller_view.view.*

/**
 * Component: SegmentController
 */
class CellSegmentController @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val dp16 = DisplayManager.dpToPx(context, 16)
    private var itemSelectedListener: OnItemSelectedListener? = null
    private var itemSelectedFunction: ((ViewGroup, View, Int) -> Unit)? = null
    private var itemSize = 0
    private val boldTypeface by lazy { ResourcesCompat.getFont(context, R.font.notosans_bold_hestia) }
    private val regularTypeface by lazy { ResourcesCompat.getFont(context, R.font.notosans_regular_hestia) }

    var selectedIndex: Int? = null
        set(value) {
            field = value
            value?.let { select(it) }
        }

    var text: String? = null
        set(value) {
            field = value
            titleTxt.text = value
            titleTxt.visibility = if (value.isNullOrBlank()) View.GONE else View.VISIBLE
        }

    init {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        LayoutInflater.from(context).inflate(R.layout.cell_segment_controller_view, this, true)
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellSegmentController, 0, 0)
                .use {
                    if (it.hasValue(R.styleable.CellSegmentController_titleText)) {
                        text = it.getString(R.styleable.CellSegmentController_titleText)
                    }
                    if (it.hasValue(R.styleable.CellSegmentController_selectedIndex)) {
                        selectedIndex = it.getInt(R.styleable.CellSegmentController_selectedIndex, 0)
                    }
                    if (it.hasValue(R.styleable.CellSegmentController_itemNameArray)) {
                        val nameArray = it.getTextArray(R.styleable.CellSegmentController_itemNameArray)
                        nameArray?.let { arr -> setData(arr.map { charSequence -> charSequence.toString() }) }
                    }
                }
    }

    private fun setData(nameArray: List<String>) {
        itemSize = nameArray.size
        controllerLayout.removeAllViewsInLayout()
        nameArray.forEachIndexed { index, charSequence ->
            val textView = inflateTextView(index).apply {
                text = charSequence
            }
            controllerLayout.addView(textView)
        }
    }

    private fun inflateTextView(index: Int): TextView {
        return TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            gravity = Gravity.CENTER
            setBackgroundResource(getChildBackgroundResId(index, itemSize - 1))
            setPadding(0, dp16, 0, dp16)
            setTextAppearance(R.style.T03Body14RegularCenterOrange)
            setTextColor(ContextCompat.getColorStateList(context, R.color.selector_cell_color_white_orange))
            setSelectedIfNeeded(this, index)
            setOnClickListener {
                select(index)
                itemSelectedFunction?.invoke(this@CellSegmentController.controllerLayout, it, index)
                itemSelectedListener?.onItemSelected(this@CellSegmentController.controllerLayout, it, index)
            }
        }
    }

    private fun getChildBackgroundResId(childIndex: Int, lastIndex: Int): Int {
        return when (childIndex) {
            0 -> R.drawable.selector_segment_controller_left
            lastIndex -> R.drawable.selector_segment_controller_right
            else -> R.drawable.selector_segment_controller_middle
        }
    }

    private fun setSelectedIfNeeded(textView: TextView, index: Int) {
        if (index == selectedIndex) {
            textView.isSelected = true
            if (!isInEditMode) {
                textView.typeface = boldTypeface
            }
        }
    }

    fun select(index: Int) {
        clearSelection()
        (controllerLayout.children.elementAtOrNull(index) as? TextView)?.apply {
            isSelected = true
            typeface = boldTypeface
        }
    }

    private fun clearSelection() {
        controllerLayout.children.map { it as TextView }.forEach {
            it.isSelected = false
            it.typeface = regularTypeface
        }
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        this.itemSelectedListener = listener
    }

    fun setOnItemSelectedListener(function: (ViewGroup, View, Int) -> Unit) {
        this.itemSelectedFunction = function
    }

    fun itemSelects(): Observable<Int> {
        val obs = children.mapIndexed { index, view -> view.clicks().map { index } }
        return Observable.merge(obs.toList()).share()
    }
}