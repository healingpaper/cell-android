package com.gangnam.sister.cell.element.badge

interface OnDrawableClickListener {
    fun onClick(position: DrawablePosition)

    enum class DrawablePosition {
        START, END
    }
}