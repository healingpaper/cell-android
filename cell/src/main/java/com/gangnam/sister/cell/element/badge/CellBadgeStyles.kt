package com.gangnam.sister.cell.element.badge

import android.content.Context
import com.gangnam.sister.cell.R

sealed class CellBadgeStyles: (Context) -> CellBadgeStyle {
    object Gray : CellBadgeStyles() {
        override fun invoke(context: Context): CellBadgeStyle = CellBadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.light_gray,
            textColorRes = R.color.black,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }

    object Orange : CellBadgeStyles() {
        override fun invoke(context: Context): CellBadgeStyle = CellBadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.very_light_pink,
            textColorRes = R.color.confident_orange,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }
    object Yellow : CellBadgeStyles() {
        override fun invoke(context: Context): CellBadgeStyle = CellBadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.lemonade,
            textColorRes = R.color.latte,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }
}