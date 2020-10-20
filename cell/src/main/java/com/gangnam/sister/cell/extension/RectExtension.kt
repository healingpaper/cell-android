package com.gangnam.sister.cell.extension

import android.graphics.Rect

fun Rect.containsWithSensitivity(x: Int, y: Int, horizontalSensitivity: Int, verticalSensitivity: Int): Boolean {
    return left < right && top < bottom // check for empty first
            && x >= left && x < right + horizontalSensitivity && y >= top && y < bottom + verticalSensitivity
}