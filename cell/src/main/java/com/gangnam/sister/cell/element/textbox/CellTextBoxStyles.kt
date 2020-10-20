package com.gangnam.sister.cell.element.textbox

import android.content.Context
import com.gangnam.sister.cell.R

sealed class CellTextBoxStyles: (Context) -> CellTextBoxStyle {
    object Base : CellTextBoxStyles() {
        override fun invoke(context: Context): CellTextBoxStyle = CellTextBoxStyle.create(
            context = context,
            backgroundRes = R.drawable.selector_text_box,
            textColorStateListRes = R.color.selector_text_box,
            hintColoStateListRes = R.color.selector_text_box_hint
        )
    }
}