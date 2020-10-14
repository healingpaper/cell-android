package com.gangnam.sister.cell.element.button

import android.content.Context
import com.gangnam.sister.cell.R

sealed class ButtonStyles: (Context) -> ButtonStyle {
    object Primary : ButtonStyles() {
        override fun invoke(context: Context): ButtonStyle = ButtonStyle.create(
            context = context,
            style = Primary,
            backgroundColorRes = R.color.confident_orange,
            textColorRes = R.color.white,
            disabledBackgroundColorRes = R.color.gray,
            disabledTextColorRes = R.color.light_gray
        )
    }

    object Secondary : ButtonStyles() {
        override fun invoke(context: Context): ButtonStyle = ButtonStyle.create(
            context = context,
            style = Secondary,
            backgroundColorRes = R.color.lemonade,
            textColorRes = R.color.confident_orange,
            disabledBackgroundColorRes = R.color.gray,
            disabledTextColorRes = R.color.light_gray
        )
    }
    object Tertiary : ButtonStyles() {
        override fun invoke(context: Context): ButtonStyle = ButtonStyle.create(
            context = context,
            style = Tertiary,
            backgroundColorRes = R.color.gray,
            textColorRes = R.color.black,
            disabledBackgroundColorRes = R.color.gray,
            disabledTextColorRes = R.color.light_gray
        )
    }
    object Action : ButtonStyles() {
        override fun invoke(context: Context): ButtonStyle = ButtonStyle.create(
            context = context,
            style = Action,
            backgroundColorRes = R.color.gray,
            textColorRes = R.color.black,
            disabledBackgroundColorRes = R.color.gray,
            disabledTextColorRes = R.color.light_gray
        )
    }
    object AboveKeyboard : ButtonStyles() {
        override fun invoke(context: Context): ButtonStyle = ButtonStyle.create(
            context = context,
            style = Action,
            backgroundColorRes = R.color.confident_orange,
            textColorRes = R.color.white,
            disabledBackgroundColorRes = R.color.gray,
            disabledTextColorRes = R.color.light_gray,
            radiusRes = R.dimen.zero
        )
    }
}
