package com.gangnam.sister.cell.element.badge

import android.content.Context
import com.gangnam.sister.cell.R

sealed class CellBadgeStyles: (Context) -> CellBadgeStyle {
    object Gray : CellBadgeStyles() {
        override fun invoke(context: Context): CellBadgeStyle = CellBadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.cell_color_gray_200,
            textColorRes = R.color.cell_color_black,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500
        )
    }

    object Orange : CellBadgeStyles() {
        override fun invoke(context: Context): CellBadgeStyle = CellBadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.cell_color_light_orange,
            textColorRes = R.color.cell_color_orange,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500
        )
    }
    object Yellow : CellBadgeStyles() {
        override fun invoke(context: Context): CellBadgeStyle = CellBadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.cell_color_light_yellow,
            textColorRes = R.color.cell_color_latte,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500
        )
    }
}