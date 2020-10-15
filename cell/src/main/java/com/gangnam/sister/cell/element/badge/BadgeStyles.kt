package com.gangnam.sister.cell.element.badge

import android.content.Context
import com.gangnam.sister.cell.R

sealed class BadgeStyles: (Context) -> BadgeStyle {
    object Gray : BadgeStyles() {
        override fun invoke(context: Context): BadgeStyle = BadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.light_gray,
            textColorRes = R.color.black,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }

    object Orange : BadgeStyles() {
        override fun invoke(context: Context): BadgeStyle = BadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.very_light_pink,
            textColorRes = R.color.confident_orange,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }
    object Yellow : BadgeStyles() {
        override fun invoke(context: Context): BadgeStyle = BadgeStyle.create(
            context = context,
            backgroundColorRes = R.color.lemonade,
            textColorRes = R.color.latte,
            disabledBackgroundColorRes = R.color.light_gray,
            disabledTextColorRes = R.color.silver
        )
    }
}