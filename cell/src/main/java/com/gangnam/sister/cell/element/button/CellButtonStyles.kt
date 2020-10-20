package com.gangnam.sister.cell.element.button

import android.content.Context
import com.gangnam.sister.cell.R

sealed class CellButtonStyles : (Context) -> CellButtonStyle {
    object Primary : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Primary,
            backgroundColorRes = R.color.cell_color_orange,
            textColorRes = R.color.cell_color_white,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500,
            rippleColorRes = R.color.cell_color_white_25_percent
        )
    }

    object Secondary : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Secondary,
            backgroundColorRes = R.color.cell_color_light_orange,
            textColorRes = R.color.cell_color_orange,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500,
            rippleColorRes = R.color.cell_color_orange_15_percent
        )
    }

    object Tertiary : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Tertiary,
            backgroundColorRes = R.color.cell_color_gray_200,
            textColorRes = R.color.cell_color_black,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500,
            rippleColorRes = R.color.cell_color_black_10_percent
        )
    }

    object Action : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Action,
            backgroundColorRes = R.color.cell_color_white,
            textColorRes = R.color.cell_color_black,
            borderColorRes = R.color.cell_color_gray_400,
            borderWidthRes = R.dimen.one,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500
        )
    }

    object AboveKeyboard : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Action,
            backgroundColorRes = R.color.cell_color_orange,
            textColorRes = R.color.cell_color_white,
            disabledBackgroundColorRes = R.color.cell_color_gray_200,
            disabledTextColorRes = R.color.cell_color_gray_500,
            radiusRes = R.dimen.zero,
            rippleColorRes = R.color.cell_color_white_25_percent
        )
    }
}
