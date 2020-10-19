package com.gangnam.sister.cell.listener

import com.gangnam.sister.cell.element.badge.OnDrawableClickListener

interface OnItemDrawableClickListener {
    fun onItemClick(position: Int, drawablePosition: OnDrawableClickListener.DrawablePosition)
}