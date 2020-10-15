package com.gangnam.sister.cell.element.textbox

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.gangnam.sister.cell.R

class TextBoxStyle(
    private val context: Context,
    private val background: Drawable?,
    private val textColorStateList: ColorStateList?,
    private val hintColorStateList: ColorStateList?
) {
    fun getBackground(): Drawable? = background
    fun getTextColorStateList(): ColorStateList? = textColorStateList
    fun getHintColorStateList(): ColorStateList? = hintColorStateList

    companion object {

        fun create(
            context: Context,
            backgroundRes: Int,
            @ColorRes textColorStateListRes: Int,
            @ColorRes hintColoStateListRes: Int
        ): TextBoxStyle {
            return TextBoxStyle(
                context = context,
                background = ContextCompat.getDrawable(context, backgroundRes),
                textColorStateList = ContextCompat.getColorStateList(
                    context,
                    textColorStateListRes
                ),
                hintColorStateList = ContextCompat.getColorStateList(context, hintColoStateListRes)
            )
        }

        fun createFromAttribute(
            context: Context,
            typedArray: TypedArray?,
            originalStyle: TextBoxStyle,
            isMultiLine: Boolean = false
        ): TextBoxStyle {
            return TextBoxStyle(
                context = context,
                background = getBackgroundAttribute(originalStyle, typedArray, isMultiLine),
                textColorStateList = getTextColorStateListAttribute(
                    originalStyle,
                    typedArray,
                    isMultiLine
                ),
                hintColorStateList = getHintColorStateListAttribute(
                    originalStyle,
                    typedArray,
                    isMultiLine
                )
            )
        }

        private fun getBackgroundAttribute(
            originalStyle: TextBoxStyle,
            typedArray: TypedArray?,
            multiLine: Boolean
        ): Drawable? {
            if (typedArray == null) return originalStyle.background
            return if (multiLine) {
                typedArray.getDrawable(
                    R.styleable.CellTextArea_cellTextAreaBackground
                ) ?: originalStyle.background
            } else {
                typedArray.getDrawable(
                    R.styleable.CellTextField_cellTextFieldBackground
                ) ?: originalStyle.background
            }
        }

        private fun getTextColorStateListAttribute(
            originalStyle: TextBoxStyle,
            typedArray: TypedArray?,
            multiLine: Boolean
        ): ColorStateList? {
            if (typedArray == null) return originalStyle.textColorStateList
            return if (multiLine) {
                typedArray.getColorStateList(R.styleable.CellTextArea_cellTextAreaTextColor)
                    ?: originalStyle.textColorStateList
            } else {
                typedArray.getColorStateList(R.styleable.CellTextField_cellTextFieldTextColor)
                    ?: originalStyle.textColorStateList
            }
        }

        private fun getHintColorStateListAttribute(
            originalStyle: TextBoxStyle,
            typedArray: TypedArray?,
            multiLine: Boolean
        ): ColorStateList? {
            if (typedArray == null) return originalStyle.hintColorStateList
            return if (multiLine) {
                typedArray.getColorStateList(R.styleable.CellTextArea_cellTextAreaHintColor)
                    ?: originalStyle.hintColorStateList
            } else {
                typedArray.getColorStateList(R.styleable.CellTextField_cellTextFieldHintColor)
                    ?: originalStyle.hintColorStateList
            }
        }
    }
}