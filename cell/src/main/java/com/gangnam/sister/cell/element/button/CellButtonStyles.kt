package com.gangnam.sister.cell.element.button

import android.content.Context
import com.gangnam.sister.cell.R

sealed class CellButtonStyles : (Context) -> CellButtonStyle {
    object Primary : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Primary,
            backgroundColorRes = R.color.confident_orange,
            textColorRes = R.color.white,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver,
            rippleColorRes = R.color.white_25_percent
        )
    }

    object Secondary : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Secondary,
            backgroundColorRes = R.color.very_light_pink,
            textColorRes = R.color.confident_orange,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver,
            rippleColorRes = R.color.orange_15_percent
        )
    }

    object Tertiary : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Tertiary,
            backgroundColorRes = R.color.light_gray,
            textColorRes = R.color.black,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver,
            rippleColorRes = R.color.black_10_percent
        )
    }

    object Action : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Action,
            backgroundColorRes = R.color.white,
            textColorRes = R.color.black,
            borderColorRes = R.color.gray,
            borderWidthRes = R.dimen.one,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }

    object AboveKeyboard : CellButtonStyles() {
        override fun invoke(context: Context): CellButtonStyle = CellButtonStyle.create(
            context = context,
            style = Action,
            backgroundColorRes = R.color.confident_orange,
            textColorRes = R.color.white,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver,
            radiusRes = R.dimen.zero,
            rippleColorRes = R.color.white_25_percent
        )
    }
}
