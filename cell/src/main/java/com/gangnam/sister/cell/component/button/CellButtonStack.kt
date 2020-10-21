package com.gangnam.sister.cell.component.button

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.gangnam.sister.cell.R
import com.gangnam.sister.cell.element.button.CellButton
import com.gangnam.sister.cell.util.DisplayManager
import kotlinx.android.synthetic.main.cell_button_stack.view.*

class CellButtonStack @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var firstButtonStyle = CellButton.ButtonStyleType.PRIMARY
        set(value) {
            field = value
            originalFirstButtonStyle = value
            updateFirstButton(value)
        }

    private var originalFirstButtonStyle = CellButton.ButtonStyleType.PRIMARY

    var secondButtonStyle = CellButton.ButtonStyleType.SECONDARY
        set(value) {
            field = value
            updateSecondButton(value)
        }
    var thirdButtonStyle = CellButton.ButtonStyleType.TERTIARY
        set(value) {
            field = value
            updateThirdButton(value)
        }
    var firstButtonText: String? = null
        set(value) {
            field = value
            firstBtn?.text = value
        }
    var secondButtonText: String? = null
        set(value) {
            field = value
            secondBtn?.text = value
        }
    var thirdButtonText: String? = null
        set(value) {
            field = value
            thirdBtn?.text = value
        }
    var firstButtonVisibility: Int = View.VISIBLE
        set(value) {
            field = value
            firstBtn?.visibility = value
            checkAndUpdateMargin()
        }

    var secondButtonVisibility: Int = View.VISIBLE
        set(value) {
            field = value
            secondBtn?.visibility = value
            checkAndUpdateMargin()
        }
    var thirdButtonVisibility: Int = View.VISIBLE
        set(value) {
            field = value
            thirdBtn?.visibility = value
            checkAndUpdateMargin()
        }

    var isAboveKeyboard = false
        set(value) {
            field = value
            updateAboveKeyboardMode(value)
        }

    val dp8 = DisplayManager.dpToPx(context, 8)

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_button_stack, this, true)
        layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initView(attrs, defStyleAttr)
    }


    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellButtonStack, defStyleAttr, 0)
                .use {
                    firstButtonStyle = CellButton.ButtonStyleType.fromId(it.getInt(R.styleable.CellButtonStack_cellFirstButtonStyle, 0))
                    secondButtonStyle = CellButton.ButtonStyleType.fromId(it.getInt(R.styleable.CellButtonStack_cellSecondButtonStyle, 1))
                    thirdButtonStyle = CellButton.ButtonStyleType.fromId(it.getInt(R.styleable.CellButtonStack_cellThirdButtonStyle, 2))
                    firstButtonText = it.getString(R.styleable.CellButtonStack_cellFirstButtonText)
                    secondButtonText = it.getString(R.styleable.CellButtonStack_cellSecondButtonText)
                    thirdButtonText = it.getString(R.styleable.CellButtonStack_cellThirdButtonText)
                    firstButtonVisibility = getButtonVisibility(it.getInt(R.styleable.CellButtonStack_cellFirstButtonVisibility, 0))
                    secondButtonVisibility = getButtonVisibility(it.getInt(R.styleable.CellButtonStack_cellSecondButtonVisibility, 0))
                    thirdButtonVisibility = getButtonVisibility(it.getInt(R.styleable.CellButtonStack_cellThirdButtonVisibility, 0))
                    setBackgroundColor(Color.WHITE)
                }
    }

    private fun getButtonVisibility(visibility: Int): Int {
        return when (visibility) {
            1 -> View.INVISIBLE
            2 -> View.GONE
            else -> View.VISIBLE
        }
    }

    private fun updateFirstButton(styleType: CellButton.ButtonStyleType) {
        firstBtn.styleType = styleType
    }

    private fun updateSecondButton(styleType: CellButton.ButtonStyleType) {
        secondBtn.styleType = styleType
    }

    private fun updateThirdButton(styleType: CellButton.ButtonStyleType) {
        thirdBtn.styleType = styleType
    }

    private fun updateAboveKeyboardMode(aboveKeyboard: Boolean) {
        val padding = if (aboveKeyboard) 0 else dp8
        root.setPadding(padding, padding, padding, padding)
        firstBtn.isAboveKeyboard = aboveKeyboard
        secondBtn.isAboveKeyboard = aboveKeyboard
        thirdBtn.isAboveKeyboard = aboveKeyboard
    }

    fun setButtonItemClickListener(listener: (view: View, index: Int) -> Unit) {
        firstBtn.setOnClickListener { listener.invoke(firstBtn, 0) }
        secondBtn.setOnClickListener { listener.invoke(secondBtn, 1) }
        thirdBtn.setOnClickListener { listener.invoke(thirdBtn, 2) }
    }

    private fun checkAndUpdateMargin() {
        val buttons = getVisibleButtons(firstBtn, secondBtn, thirdBtn)
        if (buttons.size != 1) return
        val layoutParams: LayoutParams = buttons.single().layoutParams as LayoutParams
        buttons.single().layoutParams = layoutParams.apply {
            marginStart = 0
            marginEnd = 0
        }
    }

    fun getVisibleButtons(firstBtn: CellButton, secondBtn: CellButton, thirdBtn: CellButton): List<CellButton> {
        val list = arrayListOf<CellButton>()
        if (firstBtn.visibility == View.VISIBLE) list.add(firstBtn)
        if (secondBtn.visibility == View.VISIBLE) list.add(secondBtn)
        if (thirdBtn.visibility == View.VISIBLE) list.add(thirdBtn)
        return list
    }
}