package com.gangnam.sister.cell.component.button

import android.content.Context
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

    var buttonCount = 3
        set(value) {
            field = value
            if (buttonCount > 3) throw IllegalArgumentException("buttonCount must be lower or same with 3.")
            updateButtonVisibility(value)
            when (buttonCount) {
                2 -> {
                    updateFirstButton(CellButton.ButtonStyleType.SECONDARY)
                    updateSecondButton(CellButton.ButtonStyleType.PRIMARY)
                }
            }
        }

    var isAboveKeyboard = false
        set(value) {
            field = value
            if (buttonCount > 1) throw IllegalArgumentException("isAboveKeyboard can be set only with buttonCount == 1.")
            updateAboveKeyboardMode(value)
        }

    val dp8 = DisplayManager.dpToPx(context, 8)

    init {
        LayoutInflater.from(context).inflate(R.layout.cell_button_stack, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        initView(attrs, defStyleAttr)
    }


    private fun initView(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CellButtonStack, defStyleAttr, 0)
            .use {
                firstButtonStyle = CellButton.ButtonStyleType.fromId(it.getInt(R.styleable.CellButtonStack_firstButtonStyle, 0))
                secondButtonStyle = CellButton.ButtonStyleType.fromId(it.getInt(R.styleable.CellButtonStack_secondButtonStyle, 1))
                thirdButtonStyle = CellButton.ButtonStyleType.fromId(it.getInt(R.styleable.CellButtonStack_thirdButtonStyle, 2))
                buttonCount = it.getInt(R.styleable.CellButtonStack_buttonCount, 3)
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

    private fun updateButtonVisibility(buttonCount: Int) {
        firstBtn.visibility = View.VISIBLE
        secondBtn.visibility = if (buttonCount >= 2) View.VISIBLE else View.GONE
        thirdBtn.visibility = if (buttonCount >= 3) View.VISIBLE else View.GONE
    }

    private fun updateAboveKeyboardMode(aboveKeyboard: Boolean) {
        if (aboveKeyboard) {
            firstBtn.styleType = CellButton.ButtonStyleType.ABOVE_KEYBOARD
            root.setPadding(0, 0, 0, 0)
        } else {
            firstBtn.styleType = originalFirstButtonStyle
            root.setPadding(dp8, dp8, dp8, dp8)
        }
    }

    fun setButtonItemClickListener(listener: (view: View, index: Int) -> Unit) {
        firstBtn.setOnClickListener { listener.invoke(firstBtn, 0) }
        secondBtn.setOnClickListener { listener.invoke(secondBtn, 1) }
        thirdBtn.setOnClickListener { listener.invoke(thirdBtn, 2) }
    }
}